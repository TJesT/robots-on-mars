package controller.parser;

import controller.CommandFactory;
import controller.command.AbstractCommand;
import controller.exception.CannotParseException;
import controller.exception.EmptyCommandStringException;
import controller.exception.WrongNumberOfArgsException;
import engine.Model;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.Scanner;

public class CommandParser {
    private static final Logger LOGGER = Logger.getLogger(CommandParser.class);
    private static final String CMD_SPLIT_REGEX = "\\s+";

    private final CommandFactory commandFactory;

    /**
     * Create CommandParser instance
     *
     * @param model Model instance - for modifying turtle &
     *                    field in commands
     * */
    public CommandParser(Model model) {
        commandFactory = CommandFactory.getInstance(model);
    }

    /**
     * Parse arguments string to command with instance from CommandsFactory
     *
     * @throws CannotParseException
     * @return AbstractCommand
     * */
    public AbstractCommand readCommand() throws CannotParseException {
        Scanner scanner = new Scanner(System.in);
        String[] args = scanner.nextLine().strip().toLowerCase(Locale.ROOT).split(CMD_SPLIT_REGEX);

        LOGGER.debug("Parsing command string");

        if (args[0].length() == 0) {
            throw new EmptyCommandStringException();
        }

        Integer argc = commandFactory.getArgc(args[0]);
        if (argc != args.length - 1) {
            throw new WrongNumberOfArgsException();
        }

        return commandFactory.getCommandInstance(args);
    }
}
