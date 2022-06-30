package controller.command;

import controller.exception.CannotExecuteException;
import engine.Model;
import engine.util.GameState;
import org.apache.log4j.Logger;

public abstract class AbstractCommand {
    protected static final Logger LOGGER = Logger.getLogger(AbstractCommand.class);

    protected String[] args;
    protected Model model;

    public AbstractCommand(Model model) {
        this.model = model;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    abstract public GameState execute() throws CannotExecuteException;

}
