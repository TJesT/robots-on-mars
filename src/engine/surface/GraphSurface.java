package engine.surface.map;

public class GraphSurface implements ISurface<Block> {
    @Override
    public Block[] getNeighbours(Block block) {
        return new Block[0];
    }

    @Override
    public Block getStartCell() {
        return null;
    }
}
