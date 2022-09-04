package controller.command.manual;

import controller.command.AbstractCommand;
import engine.Model;

public abstract class AbstractManualCommand extends AbstractCommand {
    public AbstractManualCommand(Model model) {
        super(model);
    }
}
