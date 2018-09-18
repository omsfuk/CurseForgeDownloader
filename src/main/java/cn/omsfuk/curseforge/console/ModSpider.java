package cn.omsfuk.curseforge.console;

import cn.omsfuk.common.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

/**
 * by omsfuk
 * ---- 创建于 9/13/18 6:52 PM
 */
public class ModSpider implements Callable<ModRegistry> {

    private String modId;

    private Consumer<ModSpider> postProcessor;

    private Consumer<ModSpider> preProcessor;

    public ModSpider(String modId) {
        this.modId = modId;
    }

    public void setPostProcessor(Consumer<ModSpider> postProcessor) {
        this.postProcessor = postProcessor;
    }

    public void setPreProcessor(Consumer<ModSpider> preProcessor) {
        this.preProcessor = preProcessor;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    private String parseModName(String rawHtml) {
        return Jsoup.parse(rawHtml).getAllElements().select(".project-title .overflow-tip").text();
    }

    private List<ModFile> parseFileInfo(String rawHtml) {
        List<ModFile> modFiles = new ArrayList<>();
        Elements elements = Jsoup.parse(rawHtml).select(".listing-project-file tbody tr");

        for (int i = 0; i < elements.size(); i++) {
            Elements singleElement = elements.eq(i);

            ModFile modFile = new ModFile();
            modFile.setReleaseType(rawHtml.contains("release-phase") ? ModFile.ReleaseType.RELEASE : ModFile.ReleaseType.BETA);
            String name = singleElement.select(".project-file-name-container a").attr("data-name");
            modFile.setName(name.endsWith(".jar") ? name.substring(0, name.lastIndexOf(".")) : name);
            modFile.setSize(singleElement.select(".project-file-size").text());
            modFile.setUpdated(parseToDate(singleElement.select(".project-file-date-uploaded abbr").text()));
            modFile.setGameVersion(singleElement.select(".version-label").text());
            modFile.setDownloadLink(Constants.BASEURL + singleElement.select(".project-file-download-button a").attr("href"));
            modFile.setDownloads(singleElement.select(".project-file-downloads").text());

            modFiles.add(modFile);
        }

        return modFiles;
    }

    private Date parseToDate(String date) {
        String[] strs = date.trim().replace(",", "").split(" ");
        String monthStr = strs[0], dayStr = strs[1], yearStr = strs[2];
        int month, day, year;
        switch (monthStr) {
            case "Jan": month = 1; break;
            case "Feb": month = 2; break;
            case "Mar": month = 3; break;
            case "Apr": month = 4; break;
            case "May": month = 5; break;
            case "Jun": month = 6; break;
            case "Jul": month = 7; break;
            case "Aug": month = 8; break;
            case "Sep": month = 9; break;
            case "Oct": month = 10; break;
            case "Nov": month = 11; break;
            case "Dec": month = 12; break;
            default: month = 0;
        }
        day = Integer.parseInt(dayStr);
        year = Integer.parseInt(yearStr);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    private String getHtml() {
        return HttpUtil.get(Constants.BASEURL + "/projects/" + modId + "/files", null);
    }

    @Override
    public ModRegistry call() {
        ModRegistry registry;
        try {
            String rawHtml = getHtml();
            registry = new ModRegistry(modId, parseModName(rawHtml), parseFileInfo(rawHtml));
            if (postProcessor != null) {
                postProcessor.accept(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("[ %s ] fail to download", modId));
            return null;
        }
        return registry;
    }
}
