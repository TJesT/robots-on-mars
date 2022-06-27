package engine.surface.loader;

import engine.util.Block;
import engine.util.BlockType;

import java.util.Arrays;

public class ArrayLoader implements ILoader<Block[][]>{
    @Override
    public Block[][] load(String file_name) {
        Block[][] field = new Block[7][7];

        int height = field.length;
        int width = field[0].length;

        for (Block[] blocks : field) {
            Arrays.setAll(blocks, empty_block -> new Block());
        }

        for (int y = 1; y < height-1; y++) {
            for (int x = 1; x < width-1; x++) {
                if((x-width/2.0+0.5)*(x-width/2.0+0.5)/(float)width/width
                        + (y-height/2.0+0.5)*(y-height/2.0+0.5)/(float)height/height > 1/16.0) {
                    field[y][x].type = BlockType.WATER;
                } else {
                    field[y][x].type = BlockType.EARTH;
                }
            }
        }

        return field;
    }
}
