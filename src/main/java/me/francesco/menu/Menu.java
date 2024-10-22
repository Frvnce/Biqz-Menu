package me.francesco.menu;

//import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.francesco.menu.commands.MenuCommands;
import me.francesco.menu.configs.ConfigMenus;
import me.francesco.menu.events.ChatEvent;
import me.francesco.menu.events.ClickEvent;
import me.francesco.menu.tabs.menuTab;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public final class Menu extends JavaPlugin {
    //public static HeadDatabaseAPI api = null;
    public static HashMap<Player,String> playerList = new HashMap<>();
    @Override
    public void onEnable() {
        /* TODO
         * Aggiungere più cose autocompilanti nel gioco -> 90%
         * evitare ogni tipo di errore, in caso mandare messaggio in chat -> 76%
         * migliorare il menu di esempio con varie cose simpatiche -> 50%
         * trovare un modo per mettere le teste perchè altrimenti mi uccido -> 0%
         * magari più macchinoso però da fare.
         * Utopia, ma se si riesce a fare in modo tale da fare nel config.yml una roba che ti permette di decidere se usare l'API di HEADDATABASE o no. ->0%
         * HeadDatabase non dovrebbe aver cambiato le cose, quindi lo tengo così e via.
         *
         * CLICKEVENT i metodi
         * TRADURRE TUTTO
         * 
         */

        try {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();

            getAllMenu();

            ConfigMenus.setup(this,"menu");
            ConfigMenus.get("menu").options().copyDefaults(true);
            ConfigMenus.inventoryExample("menu");
            ConfigMenus.save("menu");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //api = new HeadDatabaseAPI();

        //Command
        getCommand("menu").setExecutor(new MenuCommands(this));

        //events
        getServer().getPluginManager().registerEvents(new ClickEvent(this),this);
        getServer().getPluginManager().registerEvents(new ChatEvent(this),this);

        //tab completer
        Objects.requireNonNull(getCommand("menu")).setTabCompleter(new menuTab());
    }

    public void getAllMenu() throws IOException {
        File file = new File(getDataFolder()+"/menu-list");
        if(!file.exists()){
            return;
        }
        for (int i = 0; i < file.listFiles().length; i++) {
            String name = file.listFiles()[i].getName();
            name = name.replace(".yml","");
            ConfigMenus.setup(this,name);
            ConfigMenus.get(name).options().copyDefaults(true);
            ConfigMenus.save(name);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
