package server.command;

import network.chat.Message;
import server.RequestHandler;
import server.Server;

public interface IServerCommand {
    void execute(Server server, Message message, RequestHandler requestHandler);
}
