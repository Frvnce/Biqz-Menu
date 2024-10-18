package me.francesco.menu.events;

import me.francesco.menu.Menu;
import me.francesco.menu.configs.configInventari;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class inventoryClickEvent implements Listener{
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        List<String> listaInventari = configInventari.getAllFiles();
        for (String nomeMenu : listaInventari) {

            // Nome dell'inventario
            Component titleMenu = e.getView().title();
            String legacy = LegacyComponentSerializer.legacyAmpersand().serialize(titleMenu);

            // Nome dal config
            String nameFromConfig = configInventari.get(nomeMenu).getString(nomeMenu+".title");
            Component titleFromConfig = LegacyComponentSerializer.legacyAmpersand().deserialize(nameFromConfig);
            String legacy2 = LegacyComponentSerializer.legacyAmpersand().serialize(titleFromConfig);

            if( legacy.equalsIgnoreCase(legacy2) ){
                int j=0;
                while(configInventari.get(nomeMenu).get(nomeMenu + ".items." + j)!=null){
                    int slot = configInventari.get(nomeMenu).getInt(nomeMenu + ".items." + j + ".slot");
                    if(slot==e.getSlot()){
                        for (String cmd:configInventari.get(nomeMenu).getStringList(nomeMenu+".items."+j+".cmd")) {
                            if(cmd.contains("chat")){
                                cmd = cmd.replace("chat","");

                                player.performCommand(cmd);
                                player.closeInventory();

                                e.setCancelled(true);
                                return;
                            }
                            if(cmd.contains("write")){
                                cmd = cmd.replace("write ","");

                                Menu.listaPlayer.put(player,cmd);

                                Component component = LegacyComponentSerializer.legacyAmpersand().deserialize("&6★ &aScrivi il nome del player in chat, se ritieni di aver sbagliato, scrivi &c\"cancella\"&a per annullare il comando!");
                                player.sendMessage(component);

                                player.closeInventory();

                                e.setCancelled(true);
                                return;
                            }
                            if(cmd.contains("say")){
                                cmd = cmd.replace("say","");
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6★ "+cmd));
                                player.closeInventory();

                                e.setCancelled(true);
                            }else{
                                if(!cmd.isEmpty())
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd.replace("%player%",player.getName()));
                            }
                        }
                        e.setCancelled(true);
                    }
                    j++;

                }
            }

        }

    }
}
