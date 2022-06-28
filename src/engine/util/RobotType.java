package engine.util;

public enum RobotType {
    NONE,
    COLLECTOR,
    SAPPER;

    @Override
    public String toString() {
        switch (this) {
            case COLLECTOR:
                return "\u001B[30m" + "C" + "\u001B[0m";
            case SAPPER:
                return "\u001B[30m" + "S" + "\u001B[0m";
        }

        return null;
    }
}
