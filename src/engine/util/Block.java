package engine.util;

import engine.item.AbstractItem;

public class Block {
    public BlockType type;
    public AbstractItem item;

    public Block() {
        this.type = BlockType.NONE;
        this.item = null;
    }

    public static String ArrayToString(Block[][] field) {
        StringBuilder sb = new StringBuilder();
        for (Block[] line : field) {
            for (Block block : line) {
                String symbol = " ";
                if(block != null) {
                    if (block.item == null) {
                        symbol = block.type.toString();
                    } else {
                        symbol = block.item.getType().toString();
                    }
                }
                sb.append(symbol);
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
