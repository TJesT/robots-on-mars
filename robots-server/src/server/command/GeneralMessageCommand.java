package server.command;

import network.chat.Message;
import server.RequestHandler;
import server.Server;

import java.util.logging.Logger;

public class GeneralMessageCommand implements IServerCommand {

    @Override
    public void execute(Server server, Message message, RequestHandler requestHandler) {
        server.broadcastMessage(message);
    }
}
