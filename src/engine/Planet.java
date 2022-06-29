package engine;

import engine.item.AbstractItem;
import engine.item.exception.ItemException;
import engine.robot.AbstractRobot;
import engine.robot.Collector;
import engine.robot.Sapper;
import engine.robot.exception.RobotException;
import engine.surface.AbstractSurface;
import engine.surface.ArraySurface;
import engine.surface.GraphSurface;
import engine.util.Block;
import engine.util.Direction;
import engine.util.Node;
import engine.util.RobotType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Planet {
    private String planet_name;
    private ArraySurface surface;
    private Set<AbstractRobot> robots;

    public Planet(String planet_name) {
        this.planet_name = planet_name;

        this.surface = new ArraySurface("file_name.txt");

        System.out.println(this.surface.toString());

        this.robots = Collections.newSetFromMap(new ConcurrentHashMap<>());

        GraphSurface graph_surface = Planet.explore(this.surface);

        Collector collector = new Collector(this.surface, graph_surface.getStartCell());

        this.robots.add(collector);

        System.out.println(collector.getMapStringView());

        try {
            collector.scan();
            collector.move(Direction.UP);
            System.out.println(collector.getMapStringView());

            collector.scan();
            collector.move(Direction.UP);
            System.out.println(collector.getMapStringView());

            collector.scan();
            collector.move(Direction.UP);
            System.out.println(collector.getMapStringView());

            collector.scan();
            collector.move(Direction.RIGHT);
            System.out.println(collector.getMapStringView());

            collector.scan();
            System.out.println(collector.getMapStringView());
        } catch (RobotException e) {
            e.printStackTrace();
        }


        System.out.println(graph_surface.toString());
    }

    /*TODO: - robot factory
    *       - threading for commands
    * */
    public AbstractRobot addRobot(String name, RobotType type) {
        AbstractRobot robot;
        Block block = this.surface.getStartCell();
        switch (type) {
            case COLLECTOR:
                robot = new Collector(this.surface, block);
                break;
            case SAPPER:
                robot = new Sapper(this.surface, block);
                break;
            default:
                return null;
        }

        this.robots.add(robot);

        return robot;
    }

    private void clearRobotIfDead(AbstractRobot robot) {
        if (!robot.isAlive()) {
            if (this.robots.contains(robot)) {
                this.robots.remove(robot);
            }
        }
    }
    private void clearItemFromCellIfUsed(Block block) {
        if (block.item == null) return;
        if (block.item.isUsed()) {
            block.item = null;
        }
    }

    private static GraphSurface explore(AbstractSurface<Block> surface) {
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
