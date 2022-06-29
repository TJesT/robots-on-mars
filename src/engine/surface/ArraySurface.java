package engine.surface;

import engine.surface.loader.ArrayLoader;
import engine.surface.loader.ILoader;
import engine.util.Block;
import engine.util.Direction;
import engine.util.ItemType;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class ArraySurface implements ISurface<Block> {
    private int width;
    private int height;

    ILoader<Block[][]> loader;
    private Block[][] field;

    public ArraySurface(String fileName) {
        this.loader = new ArrayLoader();

        this.field = this.loader.load(fileName);

        this.width  = this.field.length;
        this.height = this.field[0].length;
    }

    public ArraySurface(int width, int height) {
        this.loader = null;

        this.width = width;
        this.height = height;

        this.field = new Block[height][width];
    }

    private int[] findCell(Block block) {
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
        Direction[] moves = Direction.Collection();

        Block[] result = new Block[moves.length];

        int[] coords = findCell(block);

        for (int i = 0; i < moves.length; i++) {
            int x = coords[0] + moves[i].dx;
            int y = coords[1] + moves[i].dy;

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
        int x = random.nextInt(width-2)+2;
        int y = random.nextInt(height-2)+2;

        return field[y][x];
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "ArraySurface{\n" + Block.ArrayToString(this.field) + "\n}";
    }




    public Block findApple() {
        /*TODO: create protocol for finding blocks
                current complexity: O(N^2)*/
        int[] res = {-1, -1};

        for(int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if(field[y][x].item != null && field[y][x].item.getType() == ItemType.APPLE) {
                    return field[y][x];
                }
            }
        }

        throw new NoSuchElementException();
    }
}
