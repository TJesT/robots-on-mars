package engine.robot.mode;

import engine.robot.AbstractRobot;
import engine.robot.exception.RobotException;
import engine.util.Action;

public class Manual extends AbstractMode {
    public Manual(AbstractRobot robot) {
        super(robot);
    }

    @Override
    public void step(Action action) throws RobotException {
        this.manualStep(action);
    }
}
