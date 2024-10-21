package me.francesco.menu.events;

import me.francesco.menu.Menu;
import me.francesco.menu.configs.ConfigMenus;
import me.francesco.menu.inv.MenusInv;
import me.francesco.menu.utils.MyUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class ClickEvent implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        List<String> listInventory = ConfigMenus.getAllFiles();
        Component titleMenu = e.getView().title();
        String legacyTitle = MyUtils.getString(titleMenu);

        for (String nomeMenu : listInventory) {
            String nameFromConfig = ConfigMenus.get(nomeMenu).getString(nomeMenu + ".title");
            Component titleFromConfig = MyUtils.getComponent(nameFromConfig);
            String legacyConfigTitle = MyUtils.getString(titleFromConfig);

            if (legacyTitle.equalsIgnoreCase(legacyConfigTitle)) {
                processMenuItems(e, nomeMenu, player);
            }
        }
    }

    private void processMenuItems(InventoryClickEvent e, String nomeMenu, Player player) {
        int j = 0;
        while (ConfigMenus.get(nomeMenu).get(nomeMenu + ".items." + j) != null) {
            int slot = ConfigMenus.get(nomeMenu).getInt(nomeMenu + ".items." + j + ".slot");
            if (slot == e.getSlot()) {
                List<String> commands = ConfigMenus.get(nomeMenu).getStringList(nomeMenu + ".items." + j + ".cmd");
                executeCommands(e, commands, player);
                e.setCancelled(true);
                return;
            }
            j++;
        }
    }

    private void executeCommands(InventoryClickEvent e, List<String> commands, Player player) {
        for (String cmd : commands) {
            if (cmd.startsWith("%chat%")) {
                executeChatCommand(cmd, player);
            } else if (cmd.startsWith("%write%")) {
                executeWriteCommand(cmd, player);
            } else if (cmd.startsWith("%say%")) {
                executeSayCommand(cmd, player);
            }  else if (cmd.startsWith("%menu%")) {
                executeMenuCommand(cmd, player);
            }else {
                if (!cmd.isEmpty()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getName()));
                }
            }
        }
    }

    private void executeMenuCommand(String cmd, Player player) {
        cmd = cmd.replace("%menu%", "").trim();
        System.out.println(cmd);
        player.openInventory(MenusInv.getInventory(player,cmd));
    }

    //TODO Da capire se sti tre metodi sono utili a qualcosa o se toglierli.

    private void executeChatCommand(String cmd, Player player) {
        cmd = cmd.replace("%chat%", "").trim();
        player.closeInventory();
        player.performCommand(cmd);
    }

    private void executeWriteCommand(String cmd, Player player) {
        cmd = cmd.replace("%write%", "").trim();
        Menu.playerList.put(player, cmd);

        Component message = LegacyComponentSerializer.legacyAmpersand().deserialize(
                "&6★ &aScrivi il nome del player in chat, se ritieni di aver sbagliato, scrivi &c\"cancella\"&a per annullare il comando!");
        player.sendMessage(message);

        player.closeInventory();
    }

    private void executeSayCommand(String cmd, Player player) {
        cmd = cmd.replace("%say%", "").trim();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6★ " + cmd));
        player.closeInventory();
    }
}
