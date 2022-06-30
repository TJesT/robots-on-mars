package engine.robot.storage;

import engine.util.ItemType;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class CountingStorage implements IStorage<ItemType>{
    private Map<ItemType, Integer> storage;

    public CountingStorage() {
        this.storage = new ConcurrentHashMap<>();
    }

    public int getCount(ItemType item) {
        if (!this.storage.containsKey(item)) return 0;

        return this.storage.get(item);
    }

    @Override
    public void store(ItemType item) {
        if (this.storage.containsKey(item)) {
            this.storage.put(item,this.storage.get(item) + 1);
        } else {
            this.storage.put(item, 1);
        }
    }

    @Override
    public ItemType drop(ItemType item) throws NoSuchElementException {
        if (!this.storage.containsKey(item)) {
            throw new NoSuchElementException();
        }

        this.storage.put(item,this.storage.get(item) - 1);

        if (this.storage.get(item) <= 0) {
            this.storage.remove(item);
        }

        return item;
    }

    @Override
    public ItemType[] getItems(ItemType itemType) {
        return this.storage.keySet().toArray(new ItemType[this.storage.size()]);
    }
}
