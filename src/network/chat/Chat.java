package network.chat;

import java.util.LinkedList;

public class Chat {
    private LinkedList<Message> messages;
    private boolean hasFixedSize;
    private int fixedSize;

    public Chat() {
        messages = new LinkedList<>();
        hasFixedSize = false;
    }

    public Chat(LinkedList<Message> messages) {
        this.messages = new LinkedList<>(messages);
        hasFixedSize = false;
    }

    public Chat(int fixedSize) {
        messages = new LinkedList<>();
        hasFixedSize = true;
        this.fixedSize = fixedSize;
    }

    public void addMessage(Message message) {
        messages.addFirst(message);
        if (hasFixedSize && messages.size() > fixedSize) {
            messages.removeLast();
        }
    }

    public LinkedList<Message> getMessageList() {
        return messages;
    }


}
