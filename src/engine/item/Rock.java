package engine.item;

import engine.robot.AbstractRobot;
import engine.util.ItemType;

public class Rock extends AbstractItem {
    public Rock(){
        this.type = ItemType.ROCK;
    }

    @Override
    public void onUse(AbstractRobot robot) {
        //TODO: nothing
    }

    @Override
    public void onStand(AbstractRobot robot) {
        //TODO: make him leave
        robot.move(robot.getLastDirection().inverse());
    }

    @Override
    public void onLeave(AbstractRobot robot) {
        //TODO: stay still
    }
}
