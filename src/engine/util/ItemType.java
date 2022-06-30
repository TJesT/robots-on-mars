package engine.util;

public enum ItemType {
    NONE,
    APPLE,
    BOMB,
    ROCK;

    @Override
    public String toString() {
        switch (this) {
            case APPLE:
//                return "\u001B[32m" + "@" + "\u001B[0m";
                return "@";
            case BOMB:
//                return "\u001B[31m" + "*" + "\u001B[0m";
                return "*";
            case ROCK:
//                return "\u001B[33m" + "^" + "\u001B[0m";
                return "^";
        }


        return null;
    }
}
