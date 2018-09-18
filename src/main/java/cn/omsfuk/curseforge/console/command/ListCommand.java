package cn.omsfuk.curseforge.console.command;

import cn.omsfuk.curseforge.console.ModEntry;
import cn.omsfuk.curseforge.console.ModFile;
import cn.omsfuk.curseforge.console.ModRegistry;
import cn.omsfuk.curseforge.console.CurseForgeDownloader;
import cn.omsfuk.curseforge.console.ModSelector;

import java.util.List;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 2:08 PM
 */
public class ListCommand extends AbstractCommand {
    @Override
    public void invoke(Object object, String[] argv) {
        CurseForgeDownloader curseForgeDownloader = (CurseForgeDownloader) object;
        // print install version
        if (argv.length == 2 && argv[1].equals("-i")) {
            if (curseForgeDownloader.getInstalledModEntries() == null) {
                println("Cannot find configuration file.");
                return ;
            }
            list(curseForgeDownloader.getInstalledModEntries());
        } else { // print new version
            if (curseForgeDownloader.getModEntries() == null) {
                if (curseForgeDownloader.getModRegistries() != null) {
                    curseForgeDownloader.setModEntries(new ModSelector(curseForgeDownloader.getModRegistries()).select("1.12.2", null));
                } else {
                    new GetCommand().invoke(curseForgeDownloader, new String[]{"get"});
                }
            }
            if (argv.length == 1) {
                list(curseForgeDownloader.getModEntries());
                return;
            }
            if (argv.length == 3 && argv[1].equals("-c")) {
                int modNumber = Integer.parseInt(argv[2]);
                ModRegistry modRegistry = curseForgeDownloader.getModRegistries().get(modNumber);
                list(modRegistry);
                print("Please enter the mod file Number : ");
                int number = nextInt();
                curseForgeDownloader.getModEntries().set(modNumber,
                        new ModEntry(modRegistry.getModId(), modRegistry.getModName(), modRegistry.getModFiles().get(number)));
                return;
            }
        }
    }

    @Override
    public boolean accept(String command) {
        if (command == null) return false;
        if (command.equals("ls") || command.equals("list")) return true;
        return false;
    }

    private void list(ModRegistry modRegistry) {
        for (int i = 0; i < modRegistry.getModFiles().size(); i++) {
            printModFile(i, modRegistry.getModFiles().get(i));
        }
    }

    private void list(List<ModEntry> entries) {
        for (int i = 0; i < entries.size(); i ++) {
            printModFile(i, entries.get(i).getModFile());
        }
    }

    private void printModFile(int index, ModFile modFile) {
        StringBuilder sb = new StringBuilder();
        sb.append(index)
                .append("\t| ")
                    .append(modFile.getReleaseType())
                .append("\t| ")
                    .append(String.format("%1$-30s", modFile.getName()))
                .append("\t| ")
                    .append(modFile.getSize())
                .append("\t| ")
                    .append(modFile.getUpdated())
                .append("\t| ")
                    .append(modFile.getDownloads());
        println(sb.toString());
    }
}
