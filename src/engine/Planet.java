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

        Collector collector = new Collector(graph_surface.getStartCell());

        this.robots.add(collector);

        System.out.println(collector.getMapStringView());

        this.updateRobotNeighbours(collector);
        System.out.println(collector.getMapStringView());

        this.moveRobot(collector, Direction.UP);
        this.updateRobotNeighbours(collector);
        System.out.println(collector.getMapStringView());


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
                robot = new Collector(block);
                break;
            case SAPPER:
                robot = new Sapper(block);
                break;
            default:
                return null;
        }

        this.robots.add(robot);

        return robot;
    }

    public void updateRobotNeighbours(AbstractRobot robot) {
        class Wrapper {
            public Direction direction;
            public Block block;

            public Wrapper(Direction direction, Block block) {
                this.direction = direction;
                this.block = block;
            }
        }

        Node curNode = robot.getPosition();
        Block curBlock = curNode.block;

        Direction[] directions = Direction.Collection();
        Block[] neighbours = this.surface.getNeighbours(curBlock);

        Stream<Wrapper> wrapperStream = IntStream.range(0, directions.length)
                .mapToObj(i -> new Wrapper(directions[i], neighbours[i]));

        wrapperStream.forEach(w -> {
            if(w.block != null) {
                Node adjacentNode = new Node(w.block);
                curNode.setNeighbour(w.direction, adjacentNode);
                adjacentNode.setNeighbour(w.direction.inverse(), curNode);
            }
        });
    }
    public void moveRobot(AbstractRobot robot, Direction direction) {
        Block leavingBlock = robot.getPosition().block;
        AbstractItem leavingItem = leavingBlock.item;

        if (leavingItem != null) {
            leavingItem.onLeave(robot);
        }

        clearRobotIfDead(robot);
        clearItemFromCellIfUsed(leavingBlock);

        // robots can only travel on explored part of a map
        try {
            robot.move(direction);
        } catch (RobotException e) {
            e.printStackTrace();
        }

        Block comingBlock = robot.getPosition().block;
        AbstractItem comingItem = comingBlock.item;

        if (comingItem != null) {
            comingItem.onStand(robot);
        }

        clearRobotIfDead(robot);
        clearItemFromCellIfUsed(comingBlock);
    }
    public void useItem(AbstractRobot robot) {
        Node position = robot.getPosition();
        if (position.block.item == null) return;

        try {
            position.block.item.onUse(robot);
        } catch (ItemException e) {
            e.printStackTrace();
        }

        clearRobotIfDead(robot);
        clearItemFromCellIfUsed(position.block);
    }

    private void clearRobotIfDead(AbstractRobot robot) {
        if (!robot.isAlive()) {
            this.robots.remove(robot);
        }
    }
    private void clearItemFromCellIfUsed(Block block) {
        if (block.item == null) return;
        if (block.item.isUsed()) {
            block.item = null;
        }
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
