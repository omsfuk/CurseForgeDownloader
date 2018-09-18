package cn.omsfuk.curseforge.console.command;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 10:01 AM
 */
public interface Command {

    void invoke(Object object, String[] argv);

    boolean accept(String command);
}
