package engine.util;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private Map<Direction, Node> adj = new HashMap<>();
    public Block block;

    public Node(Block block) {
        for (Direction dir :
                Direction.Collection()) {
            this.adj.put(dir, null);
        }
        this.block = block;
    }
    public Node getNeighbour(Direction direction) {
        return adj.get(direction);
    }
    public void setNeighbour(Direction direction, Node node) {
        this.adj.put(direction, node);
    }

}
