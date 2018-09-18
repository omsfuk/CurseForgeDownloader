package cn.omsfuk.curseforge.console.command;

import cn.omsfuk.curseforge.console.ModRegistry;
import cn.omsfuk.curseforge.console.CurseForgeDownloader;
import cn.omsfuk.curseforge.console.GlobalThreadPool;
import cn.omsfuk.curseforge.console.ModSelector;
import cn.omsfuk.curseforge.console.ModSpider;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 10:06 AM
 */
public class GetCommand extends AbstractCommand {

    @Override
    public void invoke(Object object, String[] argv) {
        CurseForgeDownloader curseForgeDownloader = (CurseForgeDownloader) object;
        ensureModIdsNotNull(curseForgeDownloader);

        List<String> modIds = curseForgeDownloader.getModIds();
        List<Future<ModRegistry>> futures = new ArrayList<>();
        for (String id : modIds) {
            ModSpider modSpider = new ModSpider(id);
            modSpider.setPostProcessor(spider -> println(String.format("[ %s ] was successfully downloaded", spider.getModId())));
            futures.add(GlobalThreadPool.executors.submit(modSpider));
        }
        List<ModRegistry> registries = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < futures.size(); i++) {
            try {
                ModRegistry registry = futures.get(i).get();
                if (registry != null) {
                    registries.add(registry);
                } else {
                    count ++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (count != 0) {
            println(String.format("[ %d/%d ] mod information was successfully downloaded. You can try again with the command <get -f>", (modIds.size() - count), modIds.size()));
        } else {
            println(String.format("All %d mod information was successfully downloaded", modIds.size()));
        }
        curseForgeDownloader.setModRegistries(registries);
        curseForgeDownloader.setModEntries(new ModSelector(registries).select("1.12.2", null));
    }

    @Override
    public boolean accept(String command) {
        if (command == null) return false;
        if (command.equals("get")) return true;
        return false;
    }

    private void ensureModIdsNotNull(CurseForgeDownloader curseForgeDownloader) {
        if (curseForgeDownloader.getModIds() == null) {
            List<String> ids = new ArrayList<>();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader("mods.txt"));
            } catch (FileNotFoundException e) {
                println("[ mod.txt ] was not exist");
                throw new RuntimeException(e);
            }
            String t;
            try {
                while ((t = reader.readLine()) != null) {
                    if (t.length() != 0) {
                        ids.add(t);
                    }

                }
                curseForgeDownloader.setModIds(ids);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

}
