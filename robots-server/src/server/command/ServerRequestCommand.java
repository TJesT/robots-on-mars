package server.command;

import network.chat.Message;
import network.chat.MessageType;
import server.RequestHandler;
import server.Server;

import java.util.logging.Logger;

public class ServerRequestCommand implements IServerCommand {
    private final static Logger logger = Logger.getLogger(ServerRequestCommand.class.getName());

    @Override
    public void execute(Server server, Message message, RequestHandler requestHandler) {
        if (message.getMessageType() != MessageType.SERVER_REQUEST) {
            return;
        }
        if (message.getMessageData().equals("/exit")) {
            if (server.isLogging()) {
                logger.info("command /exit from " + requestHandler.getUserName().getName());
            }
            server.disconnect(requestHandler.getUserName());
            if (server.isLogging()) {
                logger.info(requestHandler.getUserName().getName() + " disconnected");
            }
            server.broadcastMessage(new Message(requestHandler.getUserName().getName() + " disconnected",
                    MessageType.GENERAL_MESSAGE, server.getServerName()));
        }
        else if (message.getMessageData().equals("/users")) {
            requestHandler.sendMessage(new Message(message.getMessageData(), MessageType.GENERAL_MESSAGE, message.getSenderName()));
            logger.info("command /users from " + requestHandler.getUserName().getName());
            requestHandler.sendMessage(new Message(System.lineSeparator() + "All connected users: " +
                    System.lineSeparator() + server.getUsers(), MessageType.GENERAL_MESSAGE, server.getServerName()));
        }
        else {
            requestHandler.sendMessage(new Message(message.getMessageData(), MessageType.GENERAL_MESSAGE, message.getSenderName()));
            requestHandler.sendMessage(new Message("Unknown command: " + message.getMessageData(),
                    MessageType.GENERAL_MESSAGE, server.getServerName()));
        }
    }
}
