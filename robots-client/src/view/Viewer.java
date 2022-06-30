package view;

import network.chat.Message;
import network.chat.MessageType;
import client.Client;
import client.Observer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Viewer implements Observer, DocumentListener {
    private JFrame frame;
    private JTextArea chatArea;
    private Client client;
    private HelpPanel helpPanel;
    private JTextArea messageArea;

    public Viewer(Client client) {
        this.client = client;
        frame = new JFrame();
        frame.setTitle("Chat");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (client.isConnected()) {
                    try {
                        client.sendMessage(new Message("/exit", MessageType.SERVER_REQUEST, client.getUserName()));
                    }
                    catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                System.exit(0);
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        helpPanel = new HelpPanel();
        mainPanel.add(helpPanel);

        chatArea = new JTextArea(40, 40);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        DefaultCaret caret = (DefaultCaret)chatArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        mainPanel.add(scrollPane);

        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setText("Write your message down and press enter to send it");
        mainPanel.add(textField);

        messageArea = new JTextArea(3, 40);
        messageArea.setLineWrap(true);
        messageArea.getDocument().addDocumentListener(this);
        mainPanel.add(messageArea);

        frame.add(mainPanel);
        frame.setBounds(100, 50, 800, 600);
        frame.setVisible(true);

    }

    private String formatMessage(Message message) {
        String formattedMessage = "";
        if (message.getDate() != null) {
            formattedMessage += message.getDate() + " ";
        }
        if (message.getSenderName() != null) {
            formattedMessage += message.getSenderName().getName() + " > ";
        }
        formattedMessage += message.getMessageData() + System.lineSeparator();
        return  formattedMessage;
    }

    @Override
    public void update() {
        if (client.isConnected()) {
            helpPanel.updateUserName(client.getUserName());
            chatArea.setText(chatArea.getText() + formatMessage(client.getReceivedMessage()));
        }
        else {
            frame.dispose();
            System.exit(0);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        String messageData = messageArea.getText();
        if (messageData.length() > 0 && messageData.indexOf("\n") == messageData.length() - 1) {
            Message message = new Message(messageData.substring(0, messageData.length() - 1), MessageType.GENERAL_MESSAGE, client.getUserName());
            try {
                client.sendMessage(message);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> messageArea.setText(""));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {}

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
