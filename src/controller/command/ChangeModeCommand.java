package controller.command;

import controller.exception.CannotExecuteException;
import engine.Model;
import engine.util.GameState;
import engine.util.ModeType;

public class ChangeModeCommand extends AbstractCommand {
    public ChangeModeCommand(Model model) {
        super(model);
    }

    @Override
    public GameState execute() throws CannotExecuteException {
        String name = this.args[1];
        ModeType mode = ModeType.MANUAL;
        if (this.args[2].startsWith("scan")) {
            mode = ModeType.SCAN;
        } else if (this.args[2].startsWith("auto")) {
            mode = ModeType.AUTO;
        }

        model.changeMode(name, mode);

        return GameState.SUCCESS;
    }
}
