package engine.util;

public enum BlockType {
    NONE,
    EARTH,
    STONE,
    WATER;

    @Override
    public String toString() {
        switch (this) {
            case NONE:
                return " ";
            case EARTH:
                return ",";
            case STONE:
                return ".";
            case WATER:
                return "~";
        }
        return null;
    }
}
