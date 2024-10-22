package me.francesco.menu.events;

import me.francesco.menu.Menu;
import me.francesco.menu.utils.MyUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class ChatEvent implements Listener {
    Menu plugin;
    public ChatEvent(Menu plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
    
        if(!Menu.playerList.containsKey(e.getPlayer())){return;}

        Player player = e.getPlayer();
        String message = e.getMessage();

        if(message.equalsIgnoreCase(Objects.requireNonNull(plugin.getConfig().getString("write.keyword")))){

            Component messageComp = MyUtils.getComponent(Objects.requireNonNull(plugin.getConfig().getString("write.cancelled")));
            player.sendMessage(messageComp);

            Menu.playerList.remove(player);
            e.setCancelled(true);
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            player.performCommand(Menu.playerList.get(player)+" "+message);
            Menu.playerList.remove(player);
            Component messageComp2 = MyUtils.getComponent(Objects.requireNonNull(plugin.getConfig().getString("write.success")));
            player.sendMessage(messageComp2);
            e.setCancelled(true);
        });
        e.setCancelled(true);
    }
}
