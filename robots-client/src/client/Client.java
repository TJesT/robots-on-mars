package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

import network.chat.Message;
import network.chat.MessageType;
import network.name.UserName;
import view.UserNameAsker;

public class Client implements Observable {
    private final String ip;
    private final int port;
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private final Set<Observer> observers;
    private Message receivedMessage;
    private UserName userName;
    private final String askInfo = "Please enter user name.\nUser name must contain at least one character or digit.";

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        observers = new HashSet<Observer>();
        userName = null;
    }

    public UserName getUserName() {
        return userName;
    }


    public boolean isConnected() {
        if (socket == null) {
            return false;
        }
        return !socket.isClosed();
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (isConnected()) {
                socket.close();
            }

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessage(Message message) throws IOException {
        if (message.getMessageData().indexOf("/") == 0) {
            message.setMessageType(MessageType.SERVER_REQUEST);
        }
        writer.writeObject(message);
        writer.flush();
        if (message.getMessageData().equals("/exit")) {
            close();
        }
    }

    public void connect() {
        try {
            socket = new Socket(ip, port);
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());
        }
        catch (ConnectException ex) {
            System.err.println("Server is offline");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            connect();
            if (socket == null || !isConnected()) {
                return;
            }

            UserNameAsker userNameAsker = new UserNameAsker();
            userName = userNameAsker.ask(askInfo);
            Message loginMessage = new Message("/hello", MessageType.SERVER_REQUEST, userName);
            sendMessage(loginMessage);

            while (isConnected()) {
                receivedMessage = (Message) reader.readObject();
                if (receivedMessage.getMessageType() == MessageType.SERVER_RESPONSE) {
                    if (receivedMessage.getMessageData().equals("Invalid request")) {
                        loginMessage = new Message("/hello", MessageType.SERVER_REQUEST, userName);
                        sendMessage(loginMessage);
                        continue;
                    }
                    else if (receivedMessage.getMessageData().equals("User name is taken")) {
                        userName = userNameAsker.ask("User name is taken. Please choose another user name.\n" + askInfo);
                        loginMessage = new Message("/hello", MessageType.SERVER_REQUEST, userName);
                        sendMessage(loginMessage);
                        continue;
                    }
                    else if (receivedMessage.getMessageData().equals("/exit")) {
                        break;
                    }
                }
                notifyObservers();
            }
        }
        catch (SocketException ex) {
            receivedMessage = new Message("you were disconnected ", MessageType.SERVER_RESPONSE, null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            close();
            notifyObservers();
        }
    }


    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public Message getReceivedMessage() {
        return receivedMessage;
    }
}
