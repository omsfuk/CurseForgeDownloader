package cn.omsfuk.curseforge.console;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 10:57 AM
 */
public class ModSelector {

    private List<ModRegistry> modRegistries;

    public ModSelector(List<ModRegistry> modRegistries) {
        this.modRegistries = modRegistries;
    }

    public List<ModEntry> select(String gameVersion, ModFile.ReleaseType releaseType) {
        List<ModEntry> modEntries = new ArrayList<>();
        if (releaseType == null) {
            for (ModRegistry registry : modRegistries) {
                ModFile file = registry.getModFiles().stream()
//                        .sorted(Comparator.comparing(ModFile::getUpdated))
                        .filter(e -> e.getGameVersion().equals(gameVersion))
                        .findFirst().orElse(null);
                modEntries.add(new ModEntry(registry.getModId(), registry.getModName(), file));
            }
        } else {
            for (ModRegistry registry : modRegistries) {
                ModFile file = registry.getModFiles().stream()
                        .sorted(Comparator.comparing(ModFile::getUpdated))
                        .filter(e -> e.getGameVersion().equals(gameVersion))
                        .filter(e -> e.getReleaseType().equals(releaseType))
                        .findFirst().orElse(null);
                modEntries.add(new ModEntry(registry.getModId(), registry.getModName(), file));
            }
        }
        return modEntries;
    }

}
