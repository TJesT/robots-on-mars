package engine.robot;

import engine.robot.exception.IntoTheUnknownException;
import engine.robot.exception.RobotException;
import engine.surface.AbstractSurface;
import engine.util.Block;
import engine.util.Direction;
import engine.util.RobotType;

public class Sapper extends AbstractRobot {

    public Sapper(AbstractSurface<Block> surface, Block block) {
        super(surface, block);
        this.alive = true;
        this.type = RobotType.SAPPER;
    }

    @Override
    public void move(Direction direction) throws RobotException {
        super.move(direction);
    }
}
