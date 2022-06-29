package engine.robot.mode;

import engine.robot.AbstractRobot;
import engine.robot.exception.RobotException;
import engine.util.Action;
import engine.util.ActionType;

import java.util.LinkedList;

public class Auto extends AbstractMode {
    private final LinkedList<Action> taskQueue;

    public Auto(AbstractRobot robot) {
        super(robot);
        this.taskQueue = new LinkedList<>();
        /* TODO: analyze explored map and create a task queue
                  to collect APPLEs.
         *       queue should contain only MOVEs and GRABs. */

    }

    @Override
    public void step(Action action) throws RobotException {
        if (this.taskQueue.isEmpty()) return;

        this.manualStep(this.taskQueue.poll());
    }
}
