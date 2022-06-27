package engine.item;

import engine.item.exceptions.CannotCollectException;
import engine.item.exceptions.ItemException;
import engine.robot.AbstractRobot;
import engine.util.ItemType;

public abstract class AbstractItem {
    protected ItemType type = ItemType.NONE;
    protected boolean collectable = false;
    protected boolean killer = false;
    protected boolean used = false;

    public boolean isUsed() {
        return used;
    }
    public boolean isCollectable() {
        return collectable;
    }
    public boolean isKiller() {
        return killer;
    }

    abstract public void onUse(AbstractRobot robot) throws ItemException;
    abstract public void onStand(AbstractRobot robot);
    abstract public void onLeave(AbstractRobot robot);

    public ItemType getType() {
        return this.type;
    }
}
