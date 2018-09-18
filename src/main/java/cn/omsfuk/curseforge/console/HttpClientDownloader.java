package cn.omsfuk.curseforge.console;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 9:46 PM
 */
public class HttpClientDownloader implements Downloader {

    private static CloseableHttpClient client = HttpClients.createDefault();

    @Override
    public void downlaod(String url, String savePath) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            response.getEntity().writeTo(new FileOutputStream(savePath));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
