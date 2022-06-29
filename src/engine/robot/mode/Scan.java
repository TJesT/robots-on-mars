package engine.robot.mode;

import engine.robot.AbstractRobot;
import engine.robot.exception.RobotException;
import engine.util.Action;
import engine.util.ModeType;

import java.util.LinkedList;

public class Scan extends AbstractMode {
    LinkedList<Action> taskQueue;

    public Scan(AbstractRobot robot) {
        super(robot);
        this.type = ModeType.SCAN;
        this.taskQueue = new LinkedList<>();
        /* TODO: analyze explored map and create a task queue
                  to SUCCESSFULLY explore map.
        *        queue should contains only MOVEs and SCANs. */
    }

    @Override
    public void step(Action action) throws RobotException {
        if (this.taskQueue.isEmpty()) return;

        this.manualStep(this.taskQueue.poll());
    }

}
