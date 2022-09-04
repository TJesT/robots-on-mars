package engine.item;

import engine.item.exception.CannotDefuseException;
import engine.robot.AbstractRobot;
import engine.util.ItemType;
import engine.util.RobotType;

public class Bomb extends AbstractItem {
    public Bomb(){
        super();
        this.type = ItemType.BOMB;
        this.killer = true;
    }

    @Override
    public void onUse(AbstractRobot robot) throws CannotDefuseException {
        if(robot.getType() != RobotType.COLLECTOR) {
            throw new CannotDefuseException();
        }

        this.killer = false;
        this.used = true;
    }

    @Override
    public void onStand(AbstractRobot robot) {
        if(robot.getType() != RobotType.SAPPER) {
            //TODO: explode!!!
            robot.kill(this);
        }
        this.used = true;
    }

    @Override
    public void onLeave(AbstractRobot robot) {
        if(robot.getType() != RobotType.SAPPER) {
            //TODO: explode!!!
            robot.kill(this);
        }
        this.used = true;
    }
}
