package cn.omsfuk.curseforge.console.command;

import java.util.Scanner;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 2:19 PM
 */
public abstract class AbstractCommand implements Command {

    private Scanner scanner = new Scanner(System.in);

    protected void println() {
        System.out.println();
    }

    protected void println(String str) {
        System.out.println(str);
    }

    protected void print(String str) {
        System.out.print(str);
    }

    protected String nextLine() {
        return scanner.nextLine();
    }

    protected int nextInt() {
        // TODO type check
        return scanner.nextInt();
    }


}
