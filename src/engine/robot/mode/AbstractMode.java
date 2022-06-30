package engine.robot.mode;

import engine.robot.AbstractRobot;
import engine.robot.exception.RobotException;
import engine.util.Action;
import engine.util.ActionType;
import engine.util.ModeType;

public abstract class AbstractMode {
    AbstractRobot worker;
    ModeType type = ModeType.NONE;
    public AbstractMode(AbstractRobot robot) {
        this.worker = robot;
    }

    protected void manualStep(Action action) throws RobotException {
        if (action == null) return;
        if (action.type == null) return;

        switch (action.type) {
            case MOVE:
                this.worker.move(action.direction);
                break;
            case GRAB:
                this.worker.grab();
                break;
            case SCAN:
                this.worker.scan();
                break;
        }
    }
    abstract public void step(Action action) throws RobotException;
}
