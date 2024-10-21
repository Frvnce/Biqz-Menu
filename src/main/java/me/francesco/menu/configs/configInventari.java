package me.francesco.menu.configs;

import me.francesco.menu.Menu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class configInventari {
    private static File file;
    private static HashMap<String,FileConfiguration> config = new HashMap<>();
    private static String cartella;

    public static void creaCartelle(File cartellaPlugin){
        cartella = cartellaPlugin+"/Inventari";
        File file = new File(cartella);

        if(!file.exists()){
            file.mkdir();
        }

    }

    //Trova o genera il fileConfing.
    public static void setup(Menu plugin,String nomeInventario) throws IOException {
        creaCartelle(plugin.getDataFolder());
        file = new File(cartella,nomeInventario+".yml");

        if(!file.exists()){
            file.createNewFile();
        }

        config.put(nomeInventario, YamlConfiguration.loadConfiguration(file));
        System.out.println(config.get(nomeInventario).toString());
    }

    /*public static FileConfiguration get(String nomeInventario){
        return config.get(nomeInventario);
    }*/

    public static FileConfiguration get(String nomeInventario){
        return config.get(nomeInventario);
    }

    public static void save(String nomeInventario) throws IOException {
        File file = new File(cartella,nomeInventario+".yml");
        config.get(nomeInventario).save(file);
    }

    public static void reload(String nomeInventario){
        File file = new File(cartella,nomeInventario+".yml");
        config.put(nomeInventario, YamlConfiguration.loadConfiguration(file));
    }

    public static void creaInventarioEsempio(String nomeInventario) throws IOException {
        reload(nomeInventario);
        if(get(nomeInventario).get(nomeInventario)!=null){return;}
        List<String> list = new ArrayList<>();
        list.add("");

        get(nomeInventario).createSection(nomeInventario);
        get(nomeInventario).set(nomeInventario+".title","&fTitoloInventario");
        get(nomeInventario).set(nomeInventario+".righe",6);

        get(nomeInventario).set(nomeInventario+".items.0.tipo","item");
        get(nomeInventario).set(nomeInventario+".items.0.nameItem","&cWarp &lSpawn");
        get(nomeInventario).set(nomeInventario+".items.0.lore",list);
        get(nomeInventario).set(nomeInventario+".items.0.Material","OAK_LOG");
        get(nomeInventario).set(nomeInventario+".items.0.slot",20);
        get(nomeInventario).set(nomeInventario+".items.0.glow",true);
        get(nomeInventario).set(nomeInventario+".items.0.cmd",list);

        get(nomeInventario).set(nomeInventario+".items.1.tipo","item");
        get(nomeInventario).set(nomeInventario+".items.1.nameItem",".");
        get(nomeInventario).set(nomeInventario+".items.1.lore",list);
        get(nomeInventario).set(nomeInventario+".items.1.Material","OAK_LOG");
        get(nomeInventario).set(nomeInventario+".items.1.slot",20);
        get(nomeInventario).set(nomeInventario+".items.1.glow",true);
        get(nomeInventario).set(nomeInventario+".items.1.cmd",list);

        save(nomeInventario);

    }

    public static String getInventarioName(String nomeInventario){
        reload(nomeInventario);
        return get(nomeInventario).getString(nomeInventario+".title");
    }

    public static List<String> getAllFiles(){
        File file = new File(cartella);
        List<String> listaNomiFile = new ArrayList<>();
        for (File i: file.listFiles()) {
            String nomeMenu = i.getName().replace(".yml","");
            listaNomiFile.add(nomeMenu);
        }
        return listaNomiFile;
    }

}
