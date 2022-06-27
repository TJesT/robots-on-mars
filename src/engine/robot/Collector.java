package engine.robot;

import engine.robot.storage.CountingStorage;
import engine.util.Direction;
import engine.util.RobotType;

public class Collector extends AbstractRobot {

    public Collector() {
        this.type = RobotType.COLLECTOR;
        this.inventory = new CountingStorage();
    }

    @Override
    public void move(Direction direction) {

    }
}
