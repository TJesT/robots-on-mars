package engine.util;

public enum RobotType {
    NONE,
    COLLECTOR,
    SAPPER;

    @Override
    public String toString() {
        switch (this) {
            case COLLECTOR:
                return "C";
            case SAPPER:
                return "S";
        }

        return null;
    }
}
