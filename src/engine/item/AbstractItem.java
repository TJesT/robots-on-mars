package engine.item;

import engine.item.exception.ItemException;
import engine.robot.AbstractRobot;
import engine.util.ItemType;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

    protected Set<AbstractRobot> interactionSet;

    public AbstractItem() {
        interactionSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    //TODO: should be asynchronous
    abstract public void onUse(AbstractRobot robot) throws ItemException;
    abstract public void onStand(AbstractRobot robot);
    abstract public void onLeave(AbstractRobot robot);

    public ItemType getType() {
        return this.type;
    }
}
