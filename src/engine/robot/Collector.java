package engine.robot;

import engine.robot.exception.IntoTheUnknownException;
import engine.robot.exception.RobotException;
import engine.robot.storage.CountingStorage;
import engine.util.Block;
import engine.util.Direction;
import engine.util.RobotType;

public class Collector extends AbstractRobot {

    public Collector(Block block) {
        super(block);
        this.alive = true;
        this.type = RobotType.COLLECTOR;
        this.inventory = new CountingStorage();
    }

    @Override
    public void move(Direction direction) throws RobotException {
        super.move(direction);
    }
}
