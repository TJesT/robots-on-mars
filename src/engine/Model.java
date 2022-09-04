package engine;

import engine.robot.AbstractRobot;
import engine.robot.Collector;
import engine.robot.Sapper;
import engine.robot.exception.RobotException;
import engine.surface.ISurface;
import engine.surface.ArraySurface;
import engine.surface.GraphSurface;
import engine.util.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Model {
    private String name;

    private Block commonBlock;
    private GraphSurface graph_surface;
    private ArraySurface surface;
    private ConcurrentHashMap<String, AbstractRobot> robots;

    public Model(String name) {
        this.name = name;

        this.surface = new ArraySurface("file_name.txt");

//        System.out.println(this.surface.toString());

        this.robots = new ConcurrentHashMap<>();

        this.graph_surface = Model.explore(this.surface);

        this.commonBlock = graph_surface.getStartCell();

//        System.out.println(graph_surface.toString());
    }

    public String getStringView() {
        Block[][] field = this.surface.getField();

        StringBuilder sb = new StringBuilder("\n");

        sb.append('\u2554');
        sb.append("\u2550".repeat(field.length));
        sb.append('\u2557');

        sb.append("\n");
        for (Block[] line : field) {
            sb.append("\u2551");
            for (Block block : line) {
                String symbol = ".";

                boolean flag = false;
                for (AbstractRobot robot :
                        this.robots.values()) {

                    if (block == robot.getPosition().block){
                        if (robot.isAlive()) {
                            symbol = robot.getType().toString();
                        } else {
                            symbol = "X";
                        }
                        sb.append(symbol);
                        flag = true;
                        break;
                    }
                }
                if (flag) continue;


                if (block != null) {
                    for (AbstractRobot robot :
                            this.robots.values()) {

                        if (robot.explored_map.hasBlock(block)){
                            if (block.item == null) {
                                symbol = block.type.toString();
                            } else {
                                symbol = block.item.getType().toString();
                            }
                            break;
                        }
                    }
                }

                sb.append(symbol);
            }
            sb.append("\u2551\n");
        }

        sb.append('\u255a');
        sb.append("\u2550".repeat(field.length));
        sb.append('\u255d');

        return sb.toString();
    }
    
    /*TODO: - robot factory
    * */
    public void addRobot(String name, RobotType type) {
        if (this.robots.containsKey(name)) return;

        AbstractRobot robot;
        Block block = this.surface.getStartCell();
//        Block block = this.commonBlock;

        switch (type) {
            case COLLECTOR:
                robot = new Collector(this.surface, block);
                break;
            case SAPPER:
                robot = new Sapper(this.surface, block);
                break;
            default:
                return;
        }
//        robot.explored_map = this.graph_surface;
        this.robots.put(name, robot);
    }
    public void makeStep(String name, Action action) {
        if(!robots.containsKey(name)) return;

        AbstractRobot robot = this.robots.get(name);

        clearItemFromCellIfUsed(robot.getPosition().block);
        try {
            robot.step(action);
        } catch (RobotException e) {
            e.printStackTrace();
        }

        clearItemFromCellIfUsed(robot.getPosition().block);
        clearRobotIfDead(robot);
    }
    public void changeMode(String name, ModeType type) {
        if(!robots.containsKey(name)) return;

        AbstractRobot robot = this.robots.get(name);

        robot.setMode(type);
    }

    private synchronized void clearRobotIfDead(AbstractRobot robot) {
        if (!robot.isAlive()) {
            if (this.robots.contains(robot)) {
                this.robots.remove(robot);
            }
        }
    }
    private synchronized void clearItemFromCellIfUsed(Block block) {
        if (block.item == null) return;
        if (block.item.isUsed()) {
            block.item = null;
        }
    }

    private static GraphSurface explore(ISurface<Block> surface) {
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
