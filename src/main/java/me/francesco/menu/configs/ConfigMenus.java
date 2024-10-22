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
        list.add("&#711313&l--&#984616&l--&#BF7A18&l--&#E6AD1B&l--&#BF7A18&l--&#984616&l--&#711313&l--");
        list.add(" &#0964C0Th&#0C78C6is&#0F8BCD i&#129FD3s &#15B2D9an&#18C6E0 e&#1BD9E6xa&#18C8E1mp&#16B8DBle&#13A7D6 o&#1196D0f &#0E85CBa &#0C75C5lo&#0964C0re ");
        list.add("&#711313&l--&#984616&l--&#BF7A18&l--&#E6AD1B&l--&#BF7A18&l--&#984616&l--&#711313&l--");

        List<String> listEdges = new ArrayList<>();
        listEdges.add(" ");
        listEdges.add("&aAutoEdges, you can disable this feature in ");
        listEdges.add("&athe config of the &6menu!");
        listEdges.add("&aHere: &6\\plugins\\Biqz-Menu\\menu-list");
        listEdges.add(" ");

        List<String> listFill = new ArrayList<>();
        listFill.add(" ");
        listFill.add("&aAutoFill, you can disable this feature in ");
        listFill.add("&athe config of the &6menu!");
        listFill.add("&aHere: &6\\plugins\\Biqz-Menu\\menu-list");
        listFill.add(" ");

        List<String> listCmd = new ArrayList<>();
        listCmd.add("");

        List<String> listCmdDiscord = new ArrayList<>();
        listCmdDiscord.add("%say%&6✦ &aJoin here:&6 https://discord.gg/HcVYx7b6yE");

        List<String> listCmdWebsite = new ArrayList<>();
        listCmdWebsite.add("%say%&6✦ &aClick here:&6 https://www.francesconappo.it/");

        get(inventoryName).createSection(inventoryName);
        get(inventoryName).set(inventoryName+".title","&#711313&lT&#802614&lh&#8E3A15&li&#9D4D16&ls &#BA7318&li&#C98719&ls &#E6AD1B&la&#D99C1A&ln &#BF7A18&le&#B26917&lx&#A55717&la&#984616&lm&#8B3515&lp&#7E2414&ll&#711313&le");
        get(inventoryName).set(inventoryName+".rows",6);
        get(inventoryName).set(inventoryName+".customFeature.fillMenu.active",true);
        get(inventoryName).set(inventoryName+".customFeature.fillMenu.type","item");
        get(inventoryName).set(inventoryName+".customFeature.fillMenu.nameItem","&aAuto-Fill &#363636&l/ &aCustomizable!");
        get(inventoryName).set(inventoryName+".customFeature.fillMenu.lore",listFill);
        get(inventoryName).set(inventoryName+".customFeature.fillMenu.material","black_stained_glass_pane");
        get(inventoryName).set(inventoryName+".customFeature.fillMenu.glow",true);
        get(inventoryName).set(inventoryName+".customFeature.fillMenu.cmd",listCmd);

        get(inventoryName).set(inventoryName+".customFeature.edges.active",true);
        get(inventoryName).set(inventoryName+".customFeature.edges.type","item");
        get(inventoryName).set(inventoryName+".customFeature.edges.nameItem","&aAuto-Edges &#363636&l/ &aCustomizable!");
        get(inventoryName).set(inventoryName+".customFeature.edges.lore",listEdges);
        get(inventoryName).set(inventoryName+".customFeature.edges.material","yellow_stained_glass_pane");
        get(inventoryName).set(inventoryName+".customFeature.edges.glow",true);
        get(inventoryName).set(inventoryName+".customFeature.edges.cmd",listCmd);

        get(inventoryName).set(inventoryName+".items.0.type","item");
        get(inventoryName).set(inventoryName+".items.0.nameItem","&aExample of item &#363636&l/ &aGlow item");
        get(inventoryName).set(inventoryName+".items.0.lore",list);
        get(inventoryName).set(inventoryName+".items.0.material","diamond");
        get(inventoryName).set(inventoryName+".items.0.amount",1);
        get(inventoryName).set(inventoryName+".items.0.slot",10);
        get(inventoryName).set(inventoryName+".items.0.glow",true);
        get(inventoryName).set(inventoryName+".items.0.cmd",listCmd);

        get(inventoryName).set(inventoryName+".items.1.type","item");
        get(inventoryName).set(inventoryName+".items.1.nameItem","&aAnother example &#363636&l/ &aCustom Amount");
        get(inventoryName).set(inventoryName+".items.1.lore",list);
        get(inventoryName).set(inventoryName+".items.1.material","iron_ingot");
        get(inventoryName).set(inventoryName+".items.1.amount",16);
        get(inventoryName).set(inventoryName+".items.1.slot",16);
        get(inventoryName).set(inventoryName+".items.1.glow",false);
        get(inventoryName).set(inventoryName+".items.1.cmd",listCmd);

        get(inventoryName).set(inventoryName+".items.2.type","playerHead");
        get(inventoryName).set(inventoryName+".items.2.nameItem","&a%player_name% &#363636&l/ &aPlaceHolders!");
        get(inventoryName).set(inventoryName+".items.2.lore",list);
        get(inventoryName).set(inventoryName+".items.2.amount",1);
        get(inventoryName).set(inventoryName+".items.2.slot",22);
        get(inventoryName).set(inventoryName+".items.2.glow",false);
        get(inventoryName).set(inventoryName+".items.2.cmd",listCmd);

        get(inventoryName).set(inventoryName+".items.3.type","head");
        get(inventoryName).set(inventoryName+".items.3.nameItem","&a&lBiqz &#363636&l/ &aIt's Me!");
        get(inventoryName).set(inventoryName+".items.3.lore",list);
        get(inventoryName).set(inventoryName+".items.3.material","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWVlYjVhZGU0MjY4MWQxZWY3NjJiNWJiNDhlZTgyNDkwNzJhMzgzZWUzZmJjNjJhNTAxZTdmOTRhYWE3MmVhMSJ9fX0=");
        get(inventoryName).set(inventoryName+".items.3.amount",1);
        get(inventoryName).set(inventoryName+".items.3.slot",13);
        get(inventoryName).set(inventoryName+".items.3.glow",false);
        get(inventoryName).set(inventoryName+".items.3.cmd",listCmd);

        get(inventoryName).set(inventoryName+".items.4.type","head");
        get(inventoryName).set(inventoryName+".items.4.nameItem","&a&lMy Website &#363636&l/ &aCustomHeads!");
        get(inventoryName).set(inventoryName+".items.4.lore",list);
        get(inventoryName).set(inventoryName+".items.4.material","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM4Y2YzZjhlNTRhZmMzYjNmOTFkMjBhNDlmMzI0ZGNhMTQ4NjAwN2ZlNTQ1Mzk5MDU1NTI0YzE3OTQxZjRkYyJ9fX0=");
        get(inventoryName).set(inventoryName+".items.4.amount",1);
        get(inventoryName).set(inventoryName+".items.4.slot",12);
        get(inventoryName).set(inventoryName+".items.4.glow",false);
        get(inventoryName).set(inventoryName+".items.4.cmd",listCmdWebsite);

        get(inventoryName).set(inventoryName+".items.5.type","head");
        get(inventoryName).set(inventoryName+".items.5.nameItem","&a&lMy Discord &#363636&l/ &aJoin for support!");
        get(inventoryName).set(inventoryName+".items.5.lore",list);
        get(inventoryName).set(inventoryName+".items.5.material","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2I5NDg0M2QzNDBhYmFkYmQ2NDAxZWY0ZWM3NGRjZWM0YjY2OTY2MTA2NWJkMWEwMWY5YTU5MDVhODkxOWM3MiJ9fX0=");
        get(inventoryName).set(inventoryName+".items.5.amount",1);
        get(inventoryName).set(inventoryName+".items.5.slot",14);
        get(inventoryName).set(inventoryName+".items.5.glow",false);
        get(inventoryName).set(inventoryName+".items.5.cmd",listCmdDiscord);

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
