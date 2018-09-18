package cn.omsfuk.curseforge.console;

/**
 * by omsfuk
 * ---- 创建于 9/12/18 10:33 PM
 */
public abstract class BasicMod {

    private String modId;

    private String modName;

    public BasicMod(String modId, String modName) {
        this.modId = modId;
        this.modName = modName;
    }

    public BasicMod() {}

    public String getModId() {
        return this.modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public String getModName() {
        return this.modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }
}
