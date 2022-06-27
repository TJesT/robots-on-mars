package engine;

import engine.item.exceptions.ItemException;
import engine.robot.AbstractRobot;
import engine.robot.Collector;
import engine.robot.Sapper;
import engine.surface.AbstractSurface;
import engine.surface.ArraySurface;
import engine.surface.GraphSurface;
import engine.util.Block;
import engine.util.Direction;
import engine.util.Node;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Planet {
    private String planet_name;
//    private AbstractSurface<Block, Block[][]> surface;
    private ArraySurface surface;
    private Set<AbstractRobot> robots;


    public Planet(String planet_name) {
        this.planet_name = planet_name;

        this.surface = new ArraySurface("file_name.txt");

        this.robots = Collections.newSetFromMap(new ConcurrentHashMap<>());

        Sapper sapper = new Sapper();
        Collector collector = new Collector();

        this.robots.add(sapper);
        this.robots.add(collector);

        GraphSurface graph_surface = Planet.explore(this.surface);

        System.out.println(this.surface.toString());

        Block appleBlock = this.surface.findApple();

        try {
            appleBlock.item.onStand(collector);
            appleBlock.item.onUse(collector);
        } catch (ItemException e) {
            e.printStackTrace();
        }

        //TODO: clear all dead items/cells/robots
        if(appleBlock.item.isUsed()) {
            appleBlock.item = null;
        }

        System.out.println(graph_surface.toString());
    }

    private static GraphSurface explore(AbstractSurface<Block, Block[][]> surface) {
        class Wrapper {
            public Direction direction;
            public Block block;

            public Wrapper(Direction direction, Block block) {
                this.direction = direction;
                this.block = block;
            }
        }
        
        Block startBlock = surface.getStartCell();

        Node startNode = new Node(startBlock);

        Set<Block> visited = new HashSet<>();
        LinkedList<Node> targetList = new LinkedList<>();

        visited.add(startBlock);
        targetList.add(startNode);

        while(!targetList.isEmpty()) {
            Node curNode = targetList.poll();
            Block curBlock = curNode.block;

            Direction[] directions = Direction.Collection();
            Block[] neighbours = surface.getNeighbours(curBlock);

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
