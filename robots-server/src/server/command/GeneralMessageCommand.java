package server.command;

import controller.command.AbstractCommand;
import controller.exception.CannotExecuteException;
import network.chat.Message;
import server.RequestHandler;
import server.Server;

import java.util.logging.Logger;

public class GeneralMessageCommand implements IServerCommand {

    @Override
    public void execute(Server server, Message message, RequestHandler requestHandler) {
        AbstractCommand command = server.parseMessage(message);
        try {
            if (command != null)
                command.execute();
        } catch (CannotExecuteException e) {
            e.printStackTrace();
        }

        requestHandler.sendMessage(server.getField());
    }
}
