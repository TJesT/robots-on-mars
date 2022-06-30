package network.chat;

import network.name.UserName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Message implements Serializable {
    private String messageData;
    private Calendar date;
    private UserName senderName;
    private MessageType messageType;

    public Message(String messageData, MessageType messageType, UserName userName) {
        this.messageData = messageData;
        date = new GregorianCalendar();
        this.messageType = messageType;
        senderName = userName;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessageData() {
        return messageData;
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date.getTime());
    }


    public UserName getSenderName() {
        return senderName;
    }


}
