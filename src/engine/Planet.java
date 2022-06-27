package engine;

import engine.surface.AbstractSurface;
import engine.surface.ArraySurface;
import engine.surface.GraphSurface;
import engine.util.Block;
import engine.util.Direction;
import engine.util.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Planet {
    String planet_name;
    ArraySurface surface;

    public Planet(String planet_name) {
        this.planet_name = planet_name;

        this.surface = new ArraySurface("file_name.txt");

        GraphSurface graph_surface = Planet.explore(this.surface);

        System.out.println(this.surface.toString());
        System.out.println(graph_surface.toString());
    }

    private static GraphSurface explore(ArraySurface as) {
        class Wrapper {
            public Direction direction;
            public Block block;

            public Wrapper(Direction direction, Block block) {
                this.direction = direction;
                this.block = block;
            }
        }
        
        Block startBlock = as.getStartCell();

        Node startNode = new Node(startBlock);

        Set<Block> visited = new HashSet<>();
        LinkedList<Node> targetList = new LinkedList<>();

        visited.add(startBlock);
        targetList.add(startNode);

        while(!targetList.isEmpty()) {
            Node curNode = targetList.poll();
            Block curBlock = curNode.block;

            Direction[] directions = Direction.Collection();
            Block[] neighbours = as.getNeighbours(curBlock);

            Stream<Wrapper> wrapperStream = IntStream.range(0, directions.length)
                    .mapToObj(i -> new Wrapper(directions[i], neighbours[i]));

            wrapperStream.forEach(w -> {
                if(w.block != null && !visited.contains(w.block)) {
                    Node adjacentNode = new Node(w.block);
                    curNode.setNeighbour(w.direction, adjacentNode);
                    adjacentNode.setNeighbour(w.direction.inverse(), curNode);

                    visited.add(w.block);
                    targetList.add(adjacentNode);
                }
            });
        }

        return new GraphSurface(startNode);
    }
}
