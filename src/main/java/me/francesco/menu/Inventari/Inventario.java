package me.francesco.menu.Inventari;

import me.clip.placeholderapi.PlaceholderAPI;
import me.francesco.menu.Menu;
import me.francesco.menu.configs.configInventari;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Inventario {
    Menu plugin;

    public Inventario(Menu plugin) {
        this.plugin = plugin;
    }

    public static Inventory getInventario(Player player, String nomeInventario){
        int numeroRighe = configInventari.get(nomeInventario).getInt(nomeInventario+".righe");
        int slotTotali = numeroRighe*9;

        String nomeMenu = configInventari.getInventarioName(nomeInventario);
        Component title = LegacyComponentSerializer.legacyAmpersand().deserialize(nomeMenu);

        Inventory inventory = Bukkit.createInventory(player,slotTotali, title);
        inventory = creaInventario(player,inventory,nomeInventario);

        return inventory;
    }

    public static Inventory creaInventario(Player player, Inventory inventory,String nomeInventario){
        int i=0;
        /*while(configInventari.get(nomeInventario).get(nomeInventario + ".decorazioni." + i)!=null){
            int slot = configInventari.get(nomeInventario).getInt(nomeInventario + ".decorazioni." + i + ".slot");
            inventory.setItem(slot,getItem(player,".decorazioni.",nomeInventario,i));
            i++;
        }
        i=0;
        */

        while(configInventari.get(nomeInventario).get(nomeInventario + ".items." + i)!=null){
            int slot = configInventari.get(nomeInventario).getInt(nomeInventario + ".items." + i + ".slot");
            inventory.setItem(slot,getItem(player,".items.",nomeInventario,i));
            i++;
        }
        return inventory;
    }

    public static ItemStack getItem(Player player, String tipo, String nomeInventario,int i){
        ItemStack item = null;
        if(configInventari.get(nomeInventario).get(nomeInventario + tipo + i + ".tipo")==null){return null;}
        switch (Objects.requireNonNull(configInventari.get(nomeInventario).getString(nomeInventario + tipo + i + ".tipo"))){
            case "item":
                Material materiale = Material.getMaterial(Objects.requireNonNull(configInventari.get(nomeInventario).getString(nomeInventario + tipo + i + ".Material")));
                if(materiale==null){break;}
                item = new ItemStack(materiale, 1);
                break;
            case "head":
                //item = Menu.api.getItemHead(configInventari.get(nomeInventario).getString(nomeInventario + tipo + i + ".Material"));
                break;
            case "playerHead":
                item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta myAwesomeSkullMeta = (SkullMeta) item.getItemMeta();
                myAwesomeSkullMeta.setOwner(player.getName());
                item.setItemMeta(myAwesomeSkullMeta);
                break;
            default:
                //TODO Errore
                break;
        }
        ItemMeta itemMeta = item.getItemMeta();
        //itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(configInventari.get(nomeInventario).getString(nomeInventario + tipo + i + ".nameItem"))));

        String nomeItem = Objects.requireNonNull(configInventari.get(nomeInventario).getString(nomeInventario + tipo + i + ".nameItem"));
        Component name = LegacyComponentSerializer.legacyAmpersand().deserialize(nomeItem).decoration(TextDecoration.ITALIC,false);
        itemMeta.displayName(name);

        List<String> lore;
        lore = configInventari.get(nomeInventario).getStringList(nomeInventario + tipo + i + ".lore");
        for (int j = 0; j < lore.size(); j++) {
            lore.set(j,ChatColor.translateAlternateColorCodes('&',impostaPlaceHolder(player,lore.get(j).replace("%player_name%",player.getName()))));
        }
        if(configInventari.get(nomeInventario).get(nomeInventario + tipo + i + ".glow")!=null){
            if(configInventari.get(nomeInventario).getBoolean(nomeInventario + tipo + i + ".glow")){
                itemMeta.addEnchant(Enchantment.CHANNELING,1,true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        itemMeta.setLore(lore);
        itemMeta.lore();
        item.setItemMeta(itemMeta);

        return item;
    }

    public static String impostaPlaceHolder(Player player, String messaggio){
        return PlaceholderAPI.setPlaceholders(player, messaggio);
    }
    public static Inventory creaBordoInventario(Inventory inventory){
        //Item bordo
        ItemStack item_Bordo = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta_Bordo = item_Bordo.getItemMeta();
        itemMeta_Bordo.setDisplayName(" ");
        item_Bordo.setItemMeta(itemMeta_Bordo);

        for(int i=0;i<9;i++){
            inventory.setItem(i, item_Bordo);
            if(i==0){
                for (int j=0;j<=45;j=j+9){
                    inventory.setItem(j, item_Bordo);
                    if(j==45){
                        for (int y=45;y<=53;y++){
                            inventory.setItem(y, item_Bordo);
                        }
                    }
                }
            }
            if(i==8){
                for (int j=8;j<=53;j=j+9){
                    inventory.setItem(j, item_Bordo);
                }
            }
        }

        return inventory;
    }

    public static Inventory riempiInteroInventario(Inventory inventory){
        //Item bordo
        ItemStack item_Bordo = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta_Bordo = item_Bordo.getItemMeta();
        itemMeta_Bordo.setDisplayName(" ");
        item_Bordo.setItemMeta(itemMeta_Bordo);

        for(int i=0;i<inventory.getSize();i++){
            inventory.setItem(i, item_Bordo);
        }

        return inventory;
    }

}
