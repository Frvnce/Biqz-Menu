package me.francesco.menu.commands;

import me.francesco.menu.inv.MenusInv;
import me.francesco.menu.Menu;
import me.francesco.menu.configs.ConfigMenus;
import me.francesco.menu.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class MenuCommands implements CommandExecutor {

    Menu plugin;

    public MenuCommands(Menu plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player player))
            return true;

        switch(strings.length){
            case 0:
                if(!player.hasPermission("menu.open")){
                    player.sendMessage(MyUtils.getComponent(plugin.getConfig().getString("error.no-perms")));
                    break;
                }
                openMenu(player,MenusInv.getInventory(player,"menu"));
                break;
            case 1:
                lengthOne(player,strings[0]);
                break;
            case 2:
                lengthTwo(player,strings[0],strings[1]);
                break;
            case 3:
                lengthThree(player,strings[0],strings[1],Bukkit.getPlayer(strings[2]));
                break;
            default:
                player.sendMessage(MyUtils.getComponent(plugin.getConfig().getString("error.Wrong-command")));
                break;
        }
        return true;
    }

    private void openMenu(Player player, Inventory inv){
        player.openInventory(inv);
    }

    private void lengthOne(Player player, String inventoryName){
        if(!player.hasPermission("menu.open")){
            player.sendMessage(MyUtils.getComponent(plugin.getConfig().getString("error.no-perms")));
            return;
        }
        if(!MenusInv.inventoryExist(inventoryName)){
            player.sendMessage(MyUtils.getComponent(plugin.getConfig().getString("error.Wrong-menu")));
            return;
        }
        Inventory menu = MenusInv.getInventory(player,inventoryName);
        openMenu(player,menu);
    }

    private void lengthTwo(Player player, String command, String menuName){
        switch(command){
            case "reload":
                // RELOAD menu
                if(!player.hasPermission("menu.reload")){
                    player.sendMessage(MyUtils.getComponent(plugin.getConfig().getString("error.no-perms")));
                    break;
                }
                ConfigMenus.reload(menuName);
                player.sendMessage(MyUtils.getComponent(Objects.requireNonNull(plugin.getConfig().getString("messages.reload")).replace("%menu%",menuName)));
                break;
            case "create":
                // CREATE menu
                if(!player.hasPermission("menu.create")){
                    player.sendMessage(MyUtils.getComponent(plugin.getConfig().getString("error.no-perms")));
                    break;
                }
                try {
                    ConfigMenus.setup(plugin,menuName);
                    ConfigMenus.get(menuName).options().copyDefaults(true);
                    ConfigMenus.inventoryExample(menuName);
                    ConfigMenus.save(menuName);
                    player.sendMessage(MyUtils.getComponent(plugin.getConfig().getString("messages.success").replace("%menu%",menuName)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                    player.sendMessage(MyUtils.getComponent(plugin.getConfig().getString("error.Wrong-command")));
                break;
        }
    }

    private void lengthThree(Player player, String command, String menuName, Player user){
        if(!command.equalsIgnoreCase("open")){
            player.sendMessage(MyUtils.getComponent(plugin.getConfig().getString("error.Wrong-command")));
            return;
        }
        if(!player.hasPermission("menu.admin.open")){
            player.sendMessage(MyUtils.getComponent(plugin.getConfig().getString("error.no-perms")));
            return;
        }
        if(!MenusInv.inventoryExist(menuName)){
            player.sendMessage(MyUtils.getComponent(plugin.getConfig().getString("error.Wrong-menu")));
            return;
        }
        openMenu(user,MenusInv.getInventory(user,menuName));
    }

}
