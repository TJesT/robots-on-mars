package engine.util;

public class Block {
    public BlockType type;
    public int item_id;

    public Block() {
        this.type = BlockType.NONE;
        this.item_id = 0;
    }
}
