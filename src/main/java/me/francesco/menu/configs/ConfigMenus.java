package me.francesco.menu.configs;

import me.francesco.menu.Menu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigMenus {
    private static File file;
    private static HashMap<String,FileConfiguration> config = new HashMap<>();
    private static String cartella;

    public static void createDir(File pluginDir){
        cartella = pluginDir+"/menu-list";
        File file = new File(cartella);

        if(!file.exists()){
            file.mkdir();
        }

    }

    //Trova o genera il fileConfing.
    public static void setup(Menu plugin,String inventoryName) throws IOException {
        createDir(plugin.getDataFolder());
        file = new File(cartella,inventoryName+".yml");

        if(!file.exists()){
            file.createNewFile();
        }

        config.put(inventoryName, YamlConfiguration.loadConfiguration(file));
    }

    public static FileConfiguration get(String inventoryName){
        return config.get(inventoryName);
    }

    public static void save(String inventoryName) throws IOException {
        File file = new File(cartella,inventoryName+".yml");
        config.get(inventoryName).save(file);
    }

    public static void reload(String inventoryName){
        File file = new File(cartella,inventoryName+".yml");
        config.put(inventoryName, YamlConfiguration.loadConfiguration(file));
    }

    public static void inventoryExample(String inventoryName) throws IOException {
        reload(inventoryName);
        if(get(inventoryName).get(inventoryName)!=null){return;}
        List<String> list = new ArrayList<>();
        list.add(" ");
        list.add("This is an example.");
        list.add(" %player_name% ");

        List<String> listCmd = new ArrayList<>();
        listCmd.add("");

        get(inventoryName).createSection(inventoryName);
        get(inventoryName).set(inventoryName+".title","&f&lTitle &a&lOf &6&lthe &7&lInventory");
        get(inventoryName).set(inventoryName+".rows",6);

        get(inventoryName).set(inventoryName+".items.0.type","item");
        get(inventoryName).set(inventoryName+".items.0.nameItem","&cExample");
        get(inventoryName).set(inventoryName+".items.0.lore",list);
        get(inventoryName).set(inventoryName+".items.0.material","diamond");
        get(inventoryName).set(inventoryName+".items.0.amount",1);
        get(inventoryName).set(inventoryName+".items.0.slot",20);
        get(inventoryName).set(inventoryName+".items.0.glow",true);
        get(inventoryName).set(inventoryName+".items.0.cmd",listCmd);

        get(inventoryName).set(inventoryName+".items.1.type","item");
        get(inventoryName).set(inventoryName+".items.1.nameItem","&6Another example");
        get(inventoryName).set(inventoryName+".items.1.lore",list);
        get(inventoryName).set(inventoryName+".items.1.material","gold_block");
        get(inventoryName).set(inventoryName+".items.1.amount",2);
        get(inventoryName).set(inventoryName+".items.1.slot",24);
        get(inventoryName).set(inventoryName+".items.1.glow",false);
        get(inventoryName).set(inventoryName+".items.1.cmd",listCmd);

        get(inventoryName).set(inventoryName+".items.2.type","playerHead");
        get(inventoryName).set(inventoryName+".items.2.nameItem","&6&lYou");
        get(inventoryName).set(inventoryName+".items.2.lore",list);
        get(inventoryName).set(inventoryName+".items.2.amount",1);
        get(inventoryName).set(inventoryName+".items.2.slot",22);
        get(inventoryName).set(inventoryName+".items.2.glow",false);
        get(inventoryName).set(inventoryName+".items.2.cmd",listCmd);

        save(inventoryName);

    }

    public static String getInventoryName(String inventoryName){
        reload(inventoryName);
        return get(inventoryName).getString(inventoryName+".title");
    }

    public static List<String> getAllFiles(){
        File file = new File(cartella);
        List<String> listNameFile = new ArrayList<>();
        for (File i: file.listFiles()) {
            String nomeMenu = i.getName().replace(".yml","");
            listNameFile.add(nomeMenu);
        }
        return listNameFile;
    }

}
