package cn.omsfuk.curseforge.console.command;

import cn.omsfuk.curseforge.console.*;
import cn.omsfuk.curseforge.console.util.FileUtils;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 4:41 PM
 */
public class UpdateCommand extends AbstractCommand {

    @Override
    public void invoke(Object object, String[] argv) {
        CurseForgeDownloader curseForgeDownloader = (CurseForgeDownloader) object;
        List<ModEntry> modNeedToBeDownload = new ArrayList<>();
        if (curseForgeDownloader.getInstalledModEntries() != null) {
            for (ModEntry modEntry : curseForgeDownloader.getModEntries()) {
                boolean installed = curseForgeDownloader.getInstalledModEntries().stream()
                        .anyMatch(e -> e.getModId().equals(modEntry.getModId()) && e.getModFile().getName().equals(modEntry.getModFile().getName()));
                if (installed) {
                    try {
                        String md5 = FileUtils.md5(modEntry.getModFile().getName() + ".jar");
                        if (modEntry.getModFile().getMd5().equals(md5)) {
                            continue;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                modNeedToBeDownload.add(modEntry);
            }
        } else {
            modNeedToBeDownload = curseForgeDownloader.getModEntries();
        }

        List<Future<String>> futures = new ArrayList<>();
        for (ModEntry modEntry : modNeedToBeDownload) {
            ModDownloader downloader = new ModDownloader(modEntry, "mods/" + modEntry.getModFile().getName() + ".jar");
            downloader.setPostProcessor(obj -> println(String.format("[ %s ] was downloaded successfully.", modEntry.getModFile().getName() + ".jar")));
            futures.add(GlobalThreadPool.executors.submit(downloader));
        }
        int count = 0;
        for (Future<String> future : futures) {
            try {
                if (future.get() != null) {
                    count++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        println(String.format("[ %d/%d ] mod was successfully downloaded", count, modNeedToBeDownload.size()));
        updateConfigFile(curseForgeDownloader, curseForgeDownloader.getModEntries());
    }

    private void updateConfigFile(CurseForgeDownloader curseForgeDownloader, List<ModEntry> modEntries) {
        try {
            curseForgeDownloader.setInstalledModEntries(modEntries);
            FileUtils.writeTo(new GsonBuilder().setPrettyPrinting().create().toJson(modEntries), "config.json");
        } catch (IOException e) {
            println("Fail to update configuration file");
        }
    }

    @Override
    public boolean accept(String command) {
        if (command == null) return false;
        if (command.equals("update")) return true;
        return false;
    }
}
