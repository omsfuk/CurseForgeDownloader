package cn.omsfuk.curseforge.console;

/**
 * by omsfuk
 * ---- 创建于 9/12/18 10:39 PM
 */
public class ModEntry extends BasicMod {

    private ModFile modFile;

    public ModEntry(String modId, String modName, ModFile modFile) {
        super(modId, modName);
        this.modFile = modFile;
    }

    public ModFile getModFile() {
        return modFile;
    }

    public void setModFile(ModFile modFile) {
        this.modFile = modFile;
    }


}
