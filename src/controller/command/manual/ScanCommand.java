package controller.command.manual;

import controller.exception.CannotExecuteException;
import engine.Model;
import engine.util.Action;
import engine.util.ActionType;
import engine.util.Direction;
import engine.util.GameState;

public class ScanCommand extends AbstractManualCommand {
    public ScanCommand(Model model) {
        super(model);
    }

    @Override
    public GameState execute() throws CannotExecuteException {

        String name = this.args[1];

        Action action = new Action(ActionType.SCAN, null);

        model.makeStep(name, action);

        return GameState.SUCCESS;
    }
}
