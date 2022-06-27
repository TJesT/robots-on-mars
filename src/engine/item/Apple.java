package engine.item;

import engine.item.exceptions.CannotCollectException;
import engine.robot.AbstractRobot;
import engine.util.ItemType;
import engine.util.RobotType;

public class Apple extends AbstractItem {
    public Apple(){
        this.type = ItemType.APPLE;
    }

    @Override
    public void onUse(AbstractRobot robot) throws CannotCollectException {
        if(robot.type != RobotType.COLLECTOR) {
            throw new CannotCollectException();
        }

        if (this.collectable) {
            robot.inventory.store(this.type);
            this.used = true;
            this.collectable = false;
        }
    }

    @Override
    public void onStand(AbstractRobot robot) {
        this.collectable = true;
    }

    @Override
    public void onLeave(AbstractRobot robot) {
        this.collectable = false;
    }
}
