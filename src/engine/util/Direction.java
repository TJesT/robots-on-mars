package engine.util;

public enum Direction {
    UP (0, -1),
    RIGHT (1, 0),
    DOWN (0, 1),
    LEFT (-1, 0);

    public int dx;
    public int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction[] Collection() {
        return new Direction[]{UP, RIGHT, DOWN, LEFT};
    }

    public Direction inverse() {
        switch (this) {
            case UP:
                return DOWN;
            case RIGHT:
                return LEFT;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
        }

        return null;
    }
}
