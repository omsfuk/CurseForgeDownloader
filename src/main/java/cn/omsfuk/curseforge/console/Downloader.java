package cn.omsfuk.curseforge.console;

import java.io.IOException;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 9:46 PM
 */
public interface Downloader {

    void downlaod(String url, String savePath) throws IOException;
}
