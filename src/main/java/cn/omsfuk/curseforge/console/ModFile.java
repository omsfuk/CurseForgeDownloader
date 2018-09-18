package cn.omsfuk.curseforge.console;

import java.util.Date;

/**
 * by omsfuk
 * ---- 创建于 7/24/18 8:03 AM
 */
public class ModFile implements Cloneable {

    public enum ReleaseType {RELEASE, BETA}

    private ReleaseType releaseType;

    private String name;

    private String size;

    private Date updated;

    private String downloadLink;

    private String gameVersion;

    private String downloads;

    private String md5;

    public ReleaseType getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(ReleaseType releaseType) {
        this.releaseType = releaseType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }


    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", releaseType, name, size, updated, downloads);
    }

    public ModFile getCopy() {
        try {
            return (ModFile) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
