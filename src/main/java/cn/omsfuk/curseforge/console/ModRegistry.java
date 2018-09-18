package cn.omsfuk.curseforge.console;

import java.util.ArrayList;
import java.util.List;

/**
 * by omsfuk
 * ---- 创建于 9/12/18 10:37 PM
 */
public class ModRegistry extends BasicMod {

    private List<ModFile> modFiles = new ArrayList<>();

    public ModRegistry() {}

    public ModRegistry(String modId, String modName, List<ModFile> modFiles) {
        super(modId, modName);
        this.modFiles = modFiles;
    }

    public List<ModFile> getModFiles() {
        return modFiles;
    }

    public void setModFiles(List<ModFile> modFiles) {
        this.modFiles = modFiles;
    }
}
