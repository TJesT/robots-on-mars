package engine.item;

import engine.item.exception.CannotCollectException;
import engine.robot.AbstractRobot;
import engine.util.ItemType;
import engine.util.RobotType;

public class Apple extends AbstractItem {
    public Apple(){
        super();
        this.collectable = true;
        this.type = ItemType.APPLE;
    }

    @Override
    public void onUse(AbstractRobot robot) throws CannotCollectException {
        if(robot.getType() != RobotType.COLLECTOR) {
            throw new CannotCollectException();
        }

        if (!this.used && this.collectable) {
            robot.inventory.store(this.type);
            this.used = true;
        }
    }

    @Override
    public void onStand(AbstractRobot robot) {
//        this.interactionSet.add(robot);
    }

    @Override
    public void onLeave(AbstractRobot robot) {
//        this.interactionSet.remove(robot);
    }
}
