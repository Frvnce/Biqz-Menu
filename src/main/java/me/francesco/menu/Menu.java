package me.francesco.menu;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.francesco.menu.commands.creaMenuCommand;
import me.francesco.menu.commands.menuCommands;
import me.francesco.menu.configs.configInventari;
import me.francesco.menu.events.chatEvent;
import me.francesco.menu.events.inventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class Menu extends JavaPlugin {
    public static HeadDatabaseAPI api = null;
    public static HashMap<Player,String> listaPlayer = new HashMap<>();
    @Override
    public void onEnable() {

        try {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();

            getAllInventari();

            configInventari.setup(this,"menu");
            configInventari.get("menu").options().copyDefaults(true);
            configInventari.creaInventarioEsempio("menu");
            configInventari.save("menu");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        api = new HeadDatabaseAPI();

        //Comandi
        getCommand("menu").setExecutor(new menuCommands(this));
        getCommand("creamenu").setExecutor(new creaMenuCommand(this));
        getServer().getPluginManager().registerEvents(new inventoryClickEvent(),this);
        getServer().getPluginManager().registerEvents(new chatEvent(this),this);

    }

    public void getAllInventari() throws IOException {
        File file = new File(getDataFolder()+"/Inventari");
        if(!file.exists()){
            return;
        }
        for (int i = 0; i < file.listFiles().length; i++) {
            String name = file.listFiles()[i].getName();
            name = name.replace(".yml","");
            configInventari.setup(this,name);
            configInventari.get(name).options().copyDefaults(true);
            configInventari.save(name);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}