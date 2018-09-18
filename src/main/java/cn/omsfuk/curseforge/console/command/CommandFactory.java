package cn.omsfuk.curseforge.console.command;

import java.util.Arrays;
import java.util.List;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 10:00 AM
 */
public class CommandFactory {

    private Command doNothingCommandCache = new DoNothingCommand();

    private List<Command> commands = Arrays.asList(
            new QuitCommand(),
            new ListCommand(),
            new GetCommand(),
            new UpdateCommand());

    public Command parseCommand(String command) {
        for (Command commandObj : commands) {
            if (commandObj.accept(command)) return commandObj;
        }
        return doNothingCommandCache;
    }
}
