package engine.surface.loader;

import engine.item.Apple;
import engine.item.Bomb;
import engine.item.Rock;
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

        field[3][3].item = new Apple();
        field[4][2].item = new Bomb();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double cxsqr = (x - width / 2.0 + 0.5) * (x - width / 2.0 + 0.5);
                double cysqr = (y - height / 2.0 + 0.5) * (y - height / 2.0 + 0.5);
                if(cxsqr/(float)width/width + cysqr/(float)height/height > 1/16.0) {
                    if (cxsqr/(float)width/width + cysqr/(float)height/height > 1/5.0) {
                        field[y][x].item = new Rock();
                    }
                    field[y][x].type = BlockType.WATER;
                } else {
                    field[y][x].type = BlockType.EARTH;
                    field[y][x].item = new Bomb();
                }
            }
        }

        return field;
    }
}
