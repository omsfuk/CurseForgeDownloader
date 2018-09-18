package cn.omsfuk.curseforge.console;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 10:08 AM
 */
public class GlobalThreadPool {

    public static ExecutorService executors = Executors.newFixedThreadPool(20);
}
