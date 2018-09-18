package cn.omsfuk.curseforge.console;

import cn.omsfuk.curseforge.console.command.CommandFactory;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 9:42 AM
 */
public class CurseForgeDownloader {


    private List<ModRegistry> modRegistries;

    private List<ModEntry> modEntries;

    private List<ModEntry> installedModEntries;

    private List<String> modIds;

    public CurseForgeDownloader() {
        // TODO init scanner according command line parameter
        printCopyright();
        loadConfig();
        loop();
    }

    public static void main(String[] args) {
        new CurseForgeDownloader();
    }

    public List<ModEntry> getInstalledModEntries() {
        return installedModEntries;
    }

    public void setInstalledModEntries(List<ModEntry> installedModEntries) {
        this.installedModEntries = installedModEntries;
    }

    public List<ModRegistry> getModRegistries() {
        return modRegistries;
    }

    public List<ModEntry> getModEntries() {
        return modEntries;
    }

    public void setModEntries(List<ModEntry> modEntries) {
        this.modEntries = modEntries;
    }

    public void setModRegistries(List<ModRegistry> modRegistries) {
        this.modRegistries = modRegistries;
    }

    public List<String> getModIds() {
        return modIds;
    }

    public void setModIds(List<String> modIds) {
        this.modIds = modIds;
    }

    private void printCopyright() {
        System.out.println("=============================================");
        System.out.println("                                             ");
        System.out.println("     Welcome To Use CurseForgeDownloader     ");
        System.out.println("                                             ");
        System.out.println("=============================================");
        System.out.println();
    }

    private void loadConfig() {
        File file = new File("config.json");
        if (!file.exists()) {
            System.out.println("Cannot find config.json. Fail to load configuration file");
            return ;
        }
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String t;
            while ((t = reader.readLine()) != null) {
                sb.append(t);
            }
        } catch (IOException e) {
            System.out.println("Fail to load configuration file");
            return ;
        }
        try {
            this.installedModEntries = new Gson().fromJson(sb.toString(),
                    new TypeToken<List<ModEntry>>() {
                    }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            System.out.println("Fail to load configuration file");
            return ;
        }
        System.out.println("Configuration was successfully loaded");
    }

    private void loop() {
        String command = null;
        Scanner scanner = new Scanner(System.in);
        CommandFactory commandFactory = new CommandFactory();
        while (true) {
            System.out.print("_> ");
            String[] argv = parseArgv(scanner.nextLine());
            commandFactory.parseCommand(argv.length > 0 ? argv[0] : null).invoke(this, argv);
        }
    }

    private String[] parseArgv(String command) {
        String[] argv =  command.split(" ");
        List<String> argvList = Arrays.stream(argv).filter(e -> e.length() != 0).collect(Collectors.toList());
        return argvList.toArray(new String[argvList.size()]);
    }
}

