package engine.robot;

import engine.robot.mode.AbstractMode;
import engine.surface.GraphSurface;
import engine.util.Direction;

public abstract class AbstractRobot {
    private int x;
    private int y;
    private AbstractMode mode;
    private GraphSurface explored_map;
    //TODO: inventory system

    abstract public void move(Direction direction);
}
