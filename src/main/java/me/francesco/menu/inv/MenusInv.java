package me.francesco.menu.inv;

import me.clip.placeholderapi.PlaceholderAPI;
import me.francesco.menu.Menu;
import me.francesco.menu.configs.ConfigMenus;
import me.francesco.menu.utils.MyUtils;
import net.kyori.adventure.text.Component;
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
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class MenusInv {
    static Menu plugin;

    public static boolean inventoryExist(String inventoryName){
        File file = new File(Bukkit.getPluginManager().getPlugin("Biqz-Menu").getDataFolder()+"/menu-list");
        if(!file.exists()){
            return false;
        }
        for (int i = 0; i < file.listFiles().length; i++) {
            String name = file.listFiles()[i].getName();
            name = name.replace(".yml","");
            if(name.equals(inventoryName))
                return true;
        }
        return false;
    }

    public static Inventory getInventory(Player player, String inventoryName){
        int nRow = ConfigMenus.get(inventoryName).getInt(inventoryName+".rows");
        int totalSlot = nRow*9;

        String nomeMenu = ConfigMenus.getInventoryName(inventoryName);
        Component title = MyUtils.getComponent(nomeMenu);

        Inventory inventory = Bukkit.createInventory(player,totalSlot, title);
        inventory = createMenu(player,inventory,inventoryName);

        return inventory;
    }

    public static Inventory createMenu(Player player, Inventory inventory, String inventoryName){
        int i=0;
        while(ConfigMenus.get(inventoryName).get(inventoryName + ".items." + i)!=null){
            int slot = ConfigMenus.get(inventoryName).getInt(inventoryName + ".items." + i + ".slot");
            inventory.setItem(slot,getItem(player,".items.",inventoryName,i));
            i++;
        }
        return inventory;
    }

    public static ItemStack getItem(Player player, String items, String inventoryName,int i){
        ItemStack item = null;
        if(ConfigMenus.get(inventoryName).get(inventoryName + items + i + ".type")==null){return null;}

        switch (Objects.requireNonNull(ConfigMenus.get(inventoryName).getString(inventoryName + items + i + ".type"))){
            case "item":
                Material material = Material.getMaterial(Objects.requireNonNull(ConfigMenus.get(inventoryName).getString(inventoryName + items + i + ".material").toUpperCase()));
                if(material==null){break;}
                int amount = ConfigMenus.get(inventoryName).getInt(inventoryName + items + i + ".amount");
                if(amount==0){amount=1;}
                item = new ItemStack(material, amount);

                break;
            case "head":
                //item = Menu.api.getItemHead(configInventari.get(inventoryName).getString(inventoryName + type + i + ".Material"));
                //TODO trovare un modo
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

        if(item==null){return null;}
        ItemMeta itemMeta = item.getItemMeta();

        // Set  the name of the item
        Component name = MyUtils.getComponent(ConfigMenus.get(inventoryName).getString(inventoryName + items + i + ".nameItem"));
        itemMeta.displayName(name);

        // Get the Lore of the item and check if there is a placeholder like a name.
        List<String> lore;
        lore = ConfigMenus.get(inventoryName).getStringList(inventoryName + items + i + ".lore");
        lore.replaceAll(s -> ChatColor.translateAlternateColorCodes('&', setPlaceHolder(player, s.replace("%player_name%", player.getName()))));

        // check if the item is glowing or not.
        if(ConfigMenus.get(inventoryName).get(inventoryName + items + i + ".glow")!=null){
            if(ConfigMenus.get(inventoryName).getBoolean(inventoryName + items + i + ".glow")){
                itemMeta.addEnchant(Enchantment.CHANNELING,1,true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }

        // set the lore and set the itemMeta.
        itemMeta.setLore(lore);
        itemMeta.lore();
        item.setItemMeta(itemMeta);

        return item;
    }

    public static String setPlaceHolder(Player player, String message){
        return PlaceholderAPI.setPlaceholders(player, message);
    }

    //TODO metterlo nel config, vediamo.
    public static org.bukkit.inventory.Inventory createEdge(org.bukkit.inventory.Inventory inventory){
        //Item ede
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

    public static org.bukkit.inventory.Inventory fullInventory(org.bukkit.inventory.Inventory inventory){
        //Item bordo
        ItemStack itemEdge = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta itemMetaEdge = itemEdge.getItemMeta();
        itemMetaEdge.setDisplayName(" ");
        itemEdge.setItemMeta(itemMetaEdge);

        for(int i=0;i<inventory.getSize();i++){
            inventory.setItem(i, itemEdge);
        }

        return inventory;
    }

}
