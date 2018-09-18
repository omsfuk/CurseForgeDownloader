package cn.omsfuk.curseforge.console;

import cn.omsfuk.curseforge.console.util.FileUtils;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 7:11 PM
 */
public class ModDownloader implements Callable<String> {


    private ModEntry modEntry;

    private String savePath;

    private Consumer<ModDownloader> postProcessor;

    private Consumer<ModDownloader> preProcessor;

    public ModDownloader(ModEntry modEntry, String savePath) {
        this.modEntry = modEntry;
        this.savePath = savePath;
    }

    public ModEntry getModEntry() {
        return modEntry;
    }

    public Consumer<ModDownloader> getPostProcessor() {
        return postProcessor;
    }

    public void setPostProcessor(Consumer<ModDownloader> postProcessor) {
        this.postProcessor = postProcessor;
    }

    public Consumer<ModDownloader> getPreProcessor() {
        return preProcessor;
    }

    public void setPreProcessor(Consumer<ModDownloader> preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public String call() throws Exception {
        try {
            // HttpClientDownloader
            new DefaultDownloader().downlaod(modEntry.getModFile().getDownloadLink(), savePath);
            String md5 = FileUtils.md5(savePath);
            modEntry.getModFile().setMd5(md5);
            if (postProcessor != null) {
                postProcessor.accept(this);
            }
            return md5;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
