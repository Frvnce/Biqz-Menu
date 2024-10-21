package me.francesco.menu;

//import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.francesco.menu.commands.creaMenuCommand;
import me.francesco.menu.commands.menuCommands;
import me.francesco.menu.configs.configMenus;
import me.francesco.menu.events.chatEvent;
import me.francesco.menu.events.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class Menu extends JavaPlugin {
    //public static HeadDatabaseAPI api = null;
    public static HashMap<Player,String> listaPlayer = new HashMap<>();
    @Override
    public void onEnable() {
        /* TODO
         * Aggiungere più cose autocompilanti nel gioco
         * evitare ogni tipo di errore, in caso mandare messaggio in chat
         * migliorare il menu di esempio con varie cose simpatiche
         * trovare un modo per mettere le teste perchè altrimenti mi uccido
         * magari più macchinoso però da fare.
         * Utopia, ma se si riesce a fare in modo tale da fare nel config.yml una roba che ti permette di decidere se usare l'API di HEADDATABASE o no.
         * HeadDatabase non dovrebbe aver cambiato le cose, quindi lo tengo così e via.
         */

        try {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();

            getAllMenu();

            configMenus.setup(this,"menu");
            configMenus.get("menu").options().copyDefaults(true);
            configMenus.inventoryExample("menu");
            configMenus.save("menu");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //api = new HeadDatabaseAPI();

        //Comandi
        getCommand("menu").setExecutor(new menuCommands(this));
        getCommand("creamenu").setExecutor(new creaMenuCommand(this));
        getServer().getPluginManager().registerEvents(new ClickEvent(),this);
        getServer().getPluginManager().registerEvents(new chatEvent(this),this);

    }

    public void getAllMenu() throws IOException {
        File file = new File(getDataFolder()+"/menus");
        if(!file.exists()){
            return;
        }
        for (int i = 0; i < file.listFiles().length; i++) {
            String name = file.listFiles()[i].getName();
            name = name.replace(".yml","");
            configMenus.setup(this,name);
            configMenus.get(name).options().copyDefaults(true);
            configMenus.save(name);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
