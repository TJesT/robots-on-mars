package engine.robot.storage;

import java.util.NoSuchElementException;

public interface IStorage<TItem> {
    abstract public void store(TItem item);
    abstract public TItem drop(TItem item) throws NoSuchElementException;
    abstract public TItem[] getItems(TItem item);
}
