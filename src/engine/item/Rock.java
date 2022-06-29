package engine.item;

import engine.robot.AbstractRobot;
import engine.robot.exception.RobotException;
import engine.util.ItemType;

public class Rock extends AbstractItem {
    public Rock(){
        super();
        this.type = ItemType.ROCK;
    }

    @Override
    public void onUse(AbstractRobot robot) {
        //TODO: nothing
    }

    @Override
    public void onStand(AbstractRobot robot) {
        //TODO: absolutely nothing
    }

    @Override
    public void onLeave(AbstractRobot robot) {
        //TODO: stay still
    }
}
