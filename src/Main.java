import controller.command.AbstractCommand;
import controller.exception.CannotExecuteException;
import controller.exception.CannotParseException;
import controller.parser.CommandParser;
import engine.Model;
import engine.util.*;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Model model = new Model("planet");
        CommandParser parser = new CommandParser(model);

        GameState state = GameState.START;

        while (state != GameState.FINISH) {
            AbstractCommand cmd = null;
            try {
                cmd = parser.readCommand();
            } catch (CannotParseException e) {
                e.printStackTrace();
            }

            if(cmd != null) {
                try {
                    state = cmd.execute();
                } catch (CannotExecuteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
