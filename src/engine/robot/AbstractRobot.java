package engine.robot;

import engine.item.AbstractItem;
import engine.robot.exception.ImDeadException;
import engine.robot.exception.IntoTheUnknownException;
import engine.robot.exception.RobotException;
import engine.robot.mode.AbstractMode;
import engine.robot.storage.IStorage;
import engine.surface.GraphSurface;
import engine.util.*;

public abstract class AbstractRobot {
    protected RobotType type = RobotType.NONE;

    protected Direction lastDirection;

    protected boolean alive = false;
    protected Node position;
    protected AbstractMode mode;
    protected GraphSurface explored_map;
    public IStorage<ItemType> inventory = null;

    public AbstractRobot(Block block) {
        this.position = new Node(block);
        this.explored_map = new GraphSurface(this.position);
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
        for (Block[] line : field) {
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
            sb.append('\n');
        }

        return sb.toString();
    }

    public void kill(AbstractItem item) {
        if(item.isKiller()) {
            alive = false;
        }
    }

    public void move(Direction direction) throws RobotException {
        if (!this.alive) {
            throw new ImDeadException();
        }
        this.lastDirection = direction;
        if (this.position.getNeighbour(direction) == null) {
            throw new IntoTheUnknownException();
        }
        this.position = this.position.getNeighbour(direction);
    }
}
