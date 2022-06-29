package engine.util;

public class Action {
    public ActionType type;
    public Direction direction;

    public Action(ActionType type, Direction direction) {
        this.type = type;
        this.direction = direction;
    }
}
