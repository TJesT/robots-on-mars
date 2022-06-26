package engine.surface.map;

import engine.surface.exception.NoSuchElementException;

import java.util.Random;

public class ArraySurface implements ISurface<Block> {
    private int width;
    private int height;

    private Block[][] field;

    public ArraySurface(String file_name) {
        this.width = 10;
        this.height = 10;

        this.field = new Block[height][width];

        this.load(file_name);
    }
    public ArraySurface(int width, int height) {
        this.width = width;
        this.height = height;

        this.field = new Block[height][width];
    }

    public void load(String file_name) {
        for (int y = 1; y < height-1; y++) {
            for (int x = 1; x < width-1; x++) {
                if(x*x/(float)width/width + y*y/(float)height/height < 4.0) {
                    field[y][x].type = BlockType.WATER;
                } else {
                    field[y][x].type = BlockType.EARTH;
                }
            }
        }
    }

    private int[] findBlockCoords(Block block) {
        /*TODO: create protocol for finding blocks
                current complexity: O(N^2)*/
        int[] res = {-1, -1};

        for(int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if(field[y][x] == block) {
                    res[0] = x;
                    res[1] = y;
                    return res;
                }
            }
        }

        throw new NoSuchElementException();
    }

    @Override
    public Block[] getNeighbours(Block block) {
        final int moves_count = 4;
        final int[][] moves = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};

        Block[] result = new Block[moves_count];

        int[] coords = findBlockCoords(block);

        for (int i = 0; i < moves_count; i++) {
            int x = coords[0] + moves[i][0];
            int y = coords[1] + moves[i][1];

            if(x >= 0 && x < width && y >= 0 && y < height)
                result[i] = field[y][x];
            else
                result[i] = null;
        }

        return result;
    }

    @Override
    public Block getStartCell() {
        Random random = new Random();
        int x = random.nextInt(width);
        int y = random.nextInt(height);

        return field[y][x];
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
}
