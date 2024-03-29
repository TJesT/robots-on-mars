package server;

import controller.command.AbstractCommand;
import controller.exception.CannotParseException;
import controller.parser.CommandParser;
import engine.Model;
import network.chat.Message;
import network.chat.MessageType;
import network.exception.InvalidUserNameException;
import network.name.UserName;
import server.command.GeneralMessageCommand;
import server.command.IServerCommand;
import server.command.ServerRequestCommand;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class Server implements Runnable {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private final boolean isLogging;
    private int port;
    private int numOfShowingMessages;

    private ServerSocket serverSocket;

    private ConcurrentHashMap<UserName, RequestHandler> clients;
    private ConcurrentHashMap<MessageType, IServerCommand> serverCommands;

    private final Model model;
    private final CommandParser parser;

    private final UserName serverName;

    public Server() {
        Properties properties = new Properties();

        try {
            properties.load(ClassLoader.getSystemResourceAsStream("resources/server.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            serverName = new UserName(properties.getProperty("ServerName"));
        } catch (InvalidUserNameException ex) {
            throw new RuntimeException("Invalid server name");
        }

        port = Integer.parseInt(properties.getProperty("Port"));
        numOfShowingMessages = Integer.parseInt(properties.getProperty("NumShowingMessages"));

        clients = new ConcurrentHashMap<UserName, RequestHandler>();
        model = new Model("model");
        parser = new CommandParser(model);

        isLogging = Boolean.parseBoolean(properties.getProperty("Logging"));

        serverCommands = new ConcurrentHashMap<>();
        serverCommands.put(MessageType.GENERAL_MESSAGE, new GeneralMessageCommand());
        serverCommands.put(MessageType.SERVER_REQUEST, new ServerRequestCommand());
    }

    public boolean isLogging() {
        return isLogging;
    }

    public UserName getServerName() {
        return serverName;
    }

    public void disconnect(UserName userName) {
        if (!clients.containsKey(userName)) {
            if (isLogging) {
                logger.warning("try to disconnect not connected user: " + userName.getName());
            }
            return;
        }
        clients.get(userName).close();
        clients.remove(userName);
    }

    public String getUsers() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<UserName, RequestHandler> entry : clients.entrySet()) {
            stringBuilder.append(entry.getKey().getName());
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public boolean registerNewUser(UserName userName, RequestHandler requestHandler) {
        if (clients.containsKey(userName) || userName.equals(serverName)) {
            return false;
        }
        clients.put(userName, requestHandler);
        return true;
    }

    public IServerCommand getCommand(MessageType messageType) {
        if (!serverCommands.containsKey(messageType)) {
            if (isLogging) {
                logger.warning("try to get command for message type: " + messageType);
            }
            return null;
        }
        return serverCommands.get(messageType);
    }

    public AbstractCommand parseMessage(Message message) {
        AbstractCommand command = null;
        try {
             command= this.parser.parseCommand(message.getMessageData());
        } catch (CannotParseException e) {
            e.printStackTrace();
        }

        return command;
    }

    public void broadcastMessage(Message message) {
        for (Map.Entry<UserName, RequestHandler> entry : clients.entrySet()) {
            entry.getValue().sendMessage(message);
        }

        logger.info("Broadcast message from " + message.getSenderName().getName());
    }

    public void close() {
        try {
            serverSocket.close();
            logger.info("Closing server socket...");
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
            ex.printStackTrace();
        }
        logger.info("Server socket closed");
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server started on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                if (isLogging) {
                    logger.info("Client connected " + clientSocket.getInetAddress() + " : " + clientSocket.getPort());
                }
                Thread thread = new Thread(new RequestHandler(this, clientSocket));
                thread.start();
            }
        }
        catch (SocketException ex) {
            System.out.println("Socket closed");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            close();
        }
    }

    public void stop() {
        for (Map.Entry<UserName, RequestHandler> entry : clients.entrySet()) {
            clients.get(entry.getKey()).sendMessage(new Message("/exit", MessageType.SERVER_RESPONSE, serverName));
            disconnect(entry.getKey());
        }
        close();
    }

    public Message getField() {
        return new Message(this.model.getStringView(), MessageType.SERVER_RESPONSE, serverName);
    }
}
