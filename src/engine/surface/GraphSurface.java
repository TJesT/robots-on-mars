package engine.surface;

import engine.utils.Block;
import engine.utils.Node;

public class GraphSurface extends AbstractSurface<Block, Node> {
    private Node startNode;

    public GraphSurface(String file_name) {
        super(null);


    }

    @Override
    public Block[] getNeighbours(Block block) {
        return new Block[0];
    }

    @Override
    public Block getStartCell() {
        return null;
    }
}
