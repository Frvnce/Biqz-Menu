package me.francesco.menu.commands;

import me.francesco.menu.Inventari.Inventario;
import me.francesco.menu.Menu;
import me.francesco.menu.configs.configInventari;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class menuCommands implements CommandExecutor {

    Menu plugin;

    public menuCommands(Menu plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)){
            if(strings.length==2){
                if(!Bukkit.getPlayer(strings[1]).hasPermission("menu.apri")){
                    Bukkit.getPlayer(strings[1]).sendMessage(Component.text(plugin.getConfig().getString("errore.no-perms")).color(TextColor.color(0xC64832)));
                    return true;
                }
                Bukkit.getPlayer(strings[1]).openInventory(Inventario.getInventario(Bukkit.getPlayer(strings[1]),strings[0]));
            }
            return true;
        }
        Player player = (Player) commandSender;

        switch(strings.length){
            case 0:
                player.openInventory(Inventario.getInventario(player,"menu"));

                return true;
            case 1:
                String nomeInventario = strings[0];
                player.openInventory(Inventario.getInventario(player,nomeInventario));
                return true;
            case 2:
                if(strings[1].equalsIgnoreCase("reload")){
                    if(!player.hasPermission("menu.reload")){
                        player.sendMessage(Component.text(plugin.getConfig().getString("errore.no-perms")).color(TextColor.color(0xC64832)));
                        return true;
                    }
                    configInventari.reload(strings[0]);
                    player.sendMessage(Component.text(plugin.getConfig().getString("reload").replace("%nomeConfig%",strings[0])).color(TextColor.color(0x3AFF77)));
                    return true;
                }
                if(!player.hasPermission("menu.apri")){
                    Bukkit.getPlayer(strings[1]).sendMessage(Component.text(plugin.getConfig().getString("errore.no-perms")).color(TextColor.color(0xC64832)));
                    return true;
                }
                Bukkit.getPlayer(strings[1]).openInventory(Inventario.getInventario(Bukkit.getPlayer(strings[1]),strings[0]));

            default:
                //TODO Messaggio che dice che l'inventario non esiste.
                return true;
        }
    }
}
