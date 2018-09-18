package cn.omsfuk.curseforge.console.command;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 10:04 AM
 */
public class QuitCommand extends AbstractCommand {
    @Override
    public void invoke(Object object, String[] argv) {
        System.exit(0);
    }

    @Override
    public boolean accept(String command) {
        if (command == null) return false;
        if (command.equals("q") || command.equals("quit")) return true;
        return false;
    }
}
