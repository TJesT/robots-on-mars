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

    @Override
    public String toString() {
        Node up = adj.get(Direction.UP);
        Node right = adj.get(Direction.RIGHT);
        Node down = adj.get(Direction.DOWN);
        Node left = adj.get(Direction.LEFT);

        return "Node{" +
                "\n\tUP=" + (up == null ? null : up.block) +
                ",\n\tRIGHT=" + (right == null ? null : right.block) +
                ",\n\tDOWN=" + (down == null ? null : down.block) +
                ",\n\tLEFT=" + (left == null ? null : left.block) +
                ",\n\tblock=" + block +
                '}';
    }
}
