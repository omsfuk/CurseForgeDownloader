package cn.omsfuk.curseforge.console;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 9:52 PM
 */
public class DefaultDownloader implements Downloader {

    @Override
    public void downlaod(String url, String savePath) throws IOException {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
        if (connection.getResponseCode() == 200) {
            BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(savePath));
            byte[] bytes = new byte[1024];
            int l;
            while ((l = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, l);
            }
        }
    }
}
