package engine.surface;

import engine.util.Block;
import engine.util.Direction;
import engine.util.Node;

import java.util.*;

public class GraphSurface extends AbstractSurface<Block, Node> {
    private Node startNode;

    public GraphSurface(Node startNode) {
        super(null);

        this.startNode = startNode;
    }

    private Node findNode(Block block) {
        Set<Node> visited = new HashSet<>();
        LinkedList<Node> targetList = new LinkedList<>();

        visited.add(startNode);
        targetList.add(startNode);

        while(!targetList.isEmpty()) {
            Node curNode = targetList.poll();

            for (Direction direction :
                    Direction.Collection()) {
                Node adjacentNode = curNode.getNeighbour(direction);
                if (adjacentNode != null && !visited.contains(adjacentNode)) {
                    if (adjacentNode.block == block) {
                        return adjacentNode;
                    }
                    targetList.add(adjacentNode);
                    visited.add(adjacentNode);
                }
            }
        }

        throw new NoSuchElementException();
    }

    @Override
    public Block[] getNeighbours(Block block) {
        Node node = this.findNode(block);

        return new Block[]{
                node.getNeighbour(Direction.UP).block,
                node.getNeighbour(Direction.RIGHT).block,
                node.getNeighbour(Direction.DOWN).block,
                node.getNeighbour(Direction.LEFT).block};
    }
    @Override
    public Block getStartCell() {
        return startNode.block;
    }

    public Block[][] toArray() {
        class NodeWrapper{
            public Node node;
            public int x;
            public int y;

            public NodeWrapper(Node node, int x, int y) {
                this.node = node;
                this.x = x;
                this.y = y;
            }
        }

        //TODO: dynamic storage size
        Block[][] view = new Block[7][7];

        Set<Node> visitedNodes = new HashSet<>();
        LinkedList<NodeWrapper> targetList = new LinkedList<>();

        visitedNodes.add(startNode);
        targetList.add(new NodeWrapper(startNode, 3, 3));

        while(!targetList.isEmpty()) {
            NodeWrapper curNW = targetList.poll();

            Node curNode = curNW.node;
            int x = curNW.x;
            int y = curNW.y;
            view[y][x] = curNode.block;

            for (Direction direction :
                    Direction.Collection()) {
                Node adjacentNode = curNode.getNeighbour(direction);
                if (adjacentNode != null && !visitedNodes.contains(adjacentNode)) {

                    targetList.add(new NodeWrapper(adjacentNode,
                            (x + direction.dx + 7)%7,
                            (y + direction.dy + 7)%7));
                    visitedNodes.add(adjacentNode);
                }
            }
        }

        return view;
    }

    @Override
    public String toString() {
        return "GraphSurface{\n" + Block.ArrayToString(this.toArray()) + "\n}";
    }
}
