package engine.robot;

import engine.item.AbstractItem;
import engine.item.exception.ItemException;
import engine.robot.exception.*;
import engine.robot.mode.AbstractMode;
import engine.robot.mode.Manual;
import engine.robot.storage.IStorage;
import engine.surface.ISurface;
import engine.surface.GraphSurface;
import engine.util.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractRobot {
    protected RobotType type = RobotType.NONE;

    protected Direction lastDirection;

    protected boolean alive = false;

    protected Node position;
    protected ISurface<Block> surface;
    protected GraphSurface explored_map;

    protected AbstractMode mode;

    public IStorage<ItemType> inventory = null;

    public AbstractRobot(ISurface<Block> surface, Block block) {
        this.surface = surface;
        this.position = new Node(block);
        this.explored_map = new GraphSurface(this.position);

        this.mode = new Manual(this);
    }

    public Node getPosition() {
        return position;
    }
    public Direction getLastDirection() {
        return lastDirection;
    }
    public RobotType getType() {
        return type;
    }
    public boolean isAlive() {
        return alive;
    }

    public String getMapStringView() {
        Block[][] field = this.explored_map.toArray();

        StringBuilder sb = new StringBuilder();

        sb.append('\u2554');
        sb.append("\u2550".repeat(field.length));
        sb.append('\u2557');

        sb.append("\n");
        for (Block[] line : field) {
            sb.append("\u2551");
            for (Block block : line) {
                String symbol = " ";
                if (block != this.position.block){
                    if (block != null) {
                        if (block.item == null) {
                            symbol = block.type.toString();
                        } else {
                            symbol = block.item.getType().toString();
                        }
                    }
                } else {
                    if (this.alive) {
                        symbol = this.type.toString();
                    } else {
                        symbol = "\u001B[30m" + "X" + "\u001B[0m";
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

    public void kill(AbstractItem item) {
        if(item.isKiller()) {
            alive = false;
        }
    }

    public void step(Action action) throws RobotException {
        this.mode.step(action);
    }

    public void scan() throws RobotException {
        if (!this.alive) return;

        class Wrapper {
            public Direction direction;
            public Block block;

            public Wrapper(Direction direction, Block block) {
                this.direction = direction;
                this.block = block;
            }
        }

        Node curNode = this.position;
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
    public void grab() throws RobotException {
        if (!this.alive) return;
        if (this.position == null) throw new NotOnAFieldException();
        if (this.position.block == null) throw new VoidExplorerException();
        if (this.position.block.item == null) return;

        try {
            this.position.block.item.onUse(this);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }
    public void move(Direction direction) throws RobotException {
        if (direction == null) return;
        if (!this.alive) return;
        // trigger onLeave()
        Block leavingBlock = this.position.block;
        AbstractItem leavingItem = leavingBlock.item;

        if (leavingItem != null) {
            leavingItem.onLeave(this);
        }

        if (!this.alive) return;
        // actually move
        this.lastDirection = direction;
        if (this.position.getNeighbour(direction) == null) {
            throw new IntoTheUnknownException();
        }
        this.position = this.position.getNeighbour(direction);


        // trigger onStand()
        Block comingBlock = this.position.block;
        AbstractItem comingItem = comingBlock.item;

        if (comingItem != null) {
            comingItem.onStand(this);
        }
    }
}
