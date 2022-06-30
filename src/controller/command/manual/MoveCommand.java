package controller.command.manual;

import controller.exception.CannotExecuteException;
import engine.Model;
import engine.util.Action;
import engine.util.ActionType;
import engine.util.Direction;
import engine.util.GameState;

public class MoveCommand extends AbstractManualCommand {
    public MoveCommand(Model model) {
        super(model);
    }

    @Override
    public GameState execute() throws CannotExecuteException {

        String name = this.args[1];

        Action action = new Action(ActionType.MOVE, null);

        if (this.args[2].startsWith("u")) {
            action.direction = Direction.UP;
        } else if (this.args[2].startsWith("r")) {
            action.direction = Direction.RIGHT;
        } else if (this.args[2].startsWith("d")) {
            action.direction = Direction.DOWN;
        } else if (this.args[2].startsWith("l")) {
            action.direction = Direction.LEFT;
        }

        model.makeStep(name, action);

        return GameState.SUCCESS;
    }
}
