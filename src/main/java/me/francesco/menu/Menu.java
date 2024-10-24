/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the license.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 */

package me.francesco.menu;

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
    public static HashMap<Player,String> playerList = new HashMap<>();
    @Override
    public void onEnable() {

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

        //Commands
        Objects.requireNonNull(getCommand("menu")).setExecutor(new MenuCommands(this));

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
        for (int i = 0; i < Objects.requireNonNull(file.listFiles()).length; i++) {
            String name = Objects.requireNonNull(file.listFiles())[i].getName();
            name = name.replace(".yml","");
            ConfigMenus.setup(this,name);
            ConfigMenus.get(name).options().copyDefaults(true);
            ConfigMenus.save(name);
        }
    }

    private void checkForPlugins(){
        if(!getServer().getPluginManager().getPlugin("PlaceHolderAPI").isEnabled()){
            getLogger().warning("PlaceHolderAPI is not enabled!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if(!getServer().getPluginManager().getPlugin("PlaceHolderAPI").isEnabled()){
            getLogger().warning("PlaceHolderAPI is not enabled!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {
        getLogger().warning("Disabling Biqz-Menu...");
    }


}
