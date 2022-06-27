package engine.robot;

import engine.item.AbstractItem;
import engine.item.Bomb;
import engine.robot.mode.AbstractMode;
import engine.robot.storage.IStorage;
import engine.surface.GraphSurface;
import engine.util.Direction;
import engine.util.ItemType;
import engine.util.RobotType;

public abstract class AbstractRobot {
    public RobotType type = RobotType.NONE;

    protected Direction lastDirection;
    protected boolean alive = false;

    protected AbstractMode mode;
    protected GraphSurface explored_map;
    public IStorage<ItemType> inventory;

    public Direction getLastDirection() {
        return lastDirection;
    }
    public boolean isAlive() {
        return alive;
    }
    public void kill(AbstractItem item) {
        if(item.isKiller()) {
            alive = false;
        }
    }

    abstract public void move(Direction direction);
}
