package controller.command.manual;

import controller.exception.CannotExecuteException;
import engine.Model;
import engine.util.Action;
import engine.util.ActionType;
import engine.util.GameState;

public class GrabCommand extends AbstractManualCommand {
    public GrabCommand(Model model) {
        super(model);
    }

    @Override
    public GameState execute() throws CannotExecuteException {

        String name = this.args[1];

        Action action = new Action(ActionType.GRAB, null);

        model.makeStep(name, action);

        return GameState.SUCCESS;
    }
}
