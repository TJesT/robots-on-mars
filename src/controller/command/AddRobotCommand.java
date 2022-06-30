package controller.command;

import controller.exception.CannotExecuteException;
import engine.Model;
import engine.util.GameState;
import engine.util.ModeType;
import engine.util.RobotType;

public class AddRobotCommand extends AbstractCommand {
    public AddRobotCommand(Model model) {
        super(model);
    }

    @Override
    public GameState execute() throws CannotExecuteException {
        String name = this.args[1];
        RobotType type = RobotType.NONE;

        if (this.args[2].startsWith("collector")) {
            type = RobotType.COLLECTOR;
        } else if (this.args[2].startsWith("sapper")) {
            type = RobotType.SAPPER;
        }

        model.addRobot(name, type);

        return GameState.SUCCESS;
    }
}
