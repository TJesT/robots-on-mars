import engine.Model;
import engine.util.*;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Model model = new Model("planet");

        Scanner scanner = new Scanner(System.in);

        String name = "Adam";

        model.addRobot(name, RobotType.COLLECTOR);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            List<String> arg = Stream.of(line.split(" "))
                    .map(String::new)
                    .collect(Collectors.toList());

            String cmd = arg.get(0);
            arg.remove(0);

            if (cmd.startsWith("EXIT")) {
                break;
            }

            if (cmd.startsWith("CHANGE_MODE")) {
                String arg1 = arg.get(0);
                ModeType mode = ModeType.MANUAL;
                if (arg1.startsWith("SCAN")) {
                    mode = ModeType.SCAN;
                } else if (arg1.startsWith("AUTO")) {
                    mode = ModeType.AUTO;
                }
                model.changeMode(name, mode);
                continue;
            }

            ActionType actionType;
            if (cmd.startsWith("GRAB")) {
                actionType = ActionType.GRAB;
            } else if (cmd.startsWith("MOVE")) {
                actionType = ActionType.MOVE;
            } else {
                actionType = ActionType.SCAN;
            }

            Action action = new Action(actionType, null);

            if (actionType == ActionType.MOVE) {
                String arg1 = arg.get(0);
                if (arg1.startsWith("U")) {
                    action.direction = Direction.UP;
                } else if (arg1.startsWith("R")) {
                    action.direction = Direction.RIGHT;
                } else if (arg1.startsWith("D")) {
                    action.direction = Direction.DOWN;
                } else if (arg1.startsWith("L")) {
                    action.direction = Direction.LEFT;
                }
            }

            model.makeStep(name, action);
        }

        scanner.close();
    }
}
