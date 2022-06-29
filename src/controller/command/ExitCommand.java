package controller.command;

import controller.exception.CannotExecuteException;
import engine.Model;
import engine.util.GameState;

public class ExitCommand extends AbstractCommand {
    public ExitCommand(Model model) {
        super(model);
    }

    @Override
    public GameState execute() throws CannotExecuteException {
        return GameState.FINISH;
    }
}
