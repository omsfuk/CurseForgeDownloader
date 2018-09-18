package cn.omsfuk.curseforge.console.command;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 10:02 AM
 */
public class DoNothingCommand implements Command {

    @Override
    public void invoke(Object object, String[] argv) {

    }

    @Override
    public boolean accept(String command) {
        return false;
    }
}
