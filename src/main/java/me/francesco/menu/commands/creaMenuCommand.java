package me.francesco.menu.commands;

import me.francesco.menu.Menu;
import me.francesco.menu.configs.configMenus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class creaMenuCommand implements CommandExecutor {
    Menu plugin;
    public creaMenuCommand(Menu plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)){return true;}
        Player player = (Player) commandSender;


        if(!player.hasPermission("menu.create")){
            player.sendMessage(Component.text(plugin.getConfig().getString("errore.no-perms")).color(TextColor.color(0xC64832)));
            return true;
        }

        if(strings.length != 1){
            player.sendMessage(Component.text(plugin.getConfig().getString("errore.comandoErrato")).color(TextColor.color(0xC64832)));
            return true;
        }
        String nomeInventario = strings[0];
        try {
            configMenus.setup(plugin,nomeInventario);
            configMenus.get(nomeInventario).options().copyDefaults(true);
            configMenus.inventoryExample(nomeInventario);
            configMenus.save(nomeInventario);
            player.sendMessage(Component.text(plugin.getConfig().getString("successo").replace("%menu%",nomeInventario)).color(TextColor.color(0x3AFF77)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
