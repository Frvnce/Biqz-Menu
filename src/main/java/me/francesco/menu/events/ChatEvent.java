package me.francesco.menu.events;

import me.francesco.menu.Menu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {
    Menu plugin;
    public ChatEvent(Menu plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
    
        if(!Menu.playerList.containsKey(e.getPlayer())){return;}

        Player player = e.getPlayer();
        String plain = e.getMessage();

        if(plain.equalsIgnoreCase("cancella")){

            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize("&6★ &cComando annullato!");
            player.sendMessage(component);

            Menu.playerList.remove(player);
            e.setCancelled(true);
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            player.performCommand(Menu.playerList.get(player)+" "+plain);
            Menu.playerList.remove(player);
            Component component2 = LegacyComponentSerializer.legacyAmpersand().deserialize("&6★ &aOperazione eseguita con successo!");
            player.sendMessage(component2);
            e.setCancelled(true);
        });
        e.setCancelled(true);
    }
}
