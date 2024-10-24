package me.francesco.menu.inv;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.clip.placeholderapi.PlaceholderAPI;
import me.francesco.menu.configs.ConfigMenus;
import me.francesco.menu.utils.MyUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class MenusInv {
    public static boolean inventoryExist(String inventoryName){
        //Check if the inventory exist. If it does, then the function will return true.
        File file = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Biqz-Menu")).getDataFolder()+"/menu-list");
        if(!file.exists()){
            return false;
        }
        for (int i = 0; i < Objects.requireNonNull(file.listFiles()).length; i++) {
            String name = Objects.requireNonNull(file.listFiles())[i].getName();
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
        Component title = MyUtils.getComponent(MenusInv.setPlaceHolder(player,nomeMenu));

        Inventory inventory = Bukkit.createInventory(player,totalSlot, title);
        inventory = createMenu(player,inventory,inventoryName);

        return inventory;
    }

    public static Inventory createMenu(Player player, Inventory inventory, String inventoryName){
        int i=0;
        int totalSlot = ConfigMenus.get(inventoryName).getInt(inventoryName+".rows")*9;
        if(ConfigMenus.get(inventoryName).getBoolean(inventoryName+".customFeature.fillMenu.active")){
            inventory = fillInventory(player, ".customFeature.fillMenu", inventoryName, inventory);
        }
        if(ConfigMenus.get(inventoryName).getBoolean(inventoryName+".customFeature.edges.active")){
            inventory = createEdge(player, ".customFeature.edges", inventoryName, inventory);
        }
        while(ConfigMenus.get(inventoryName).get(inventoryName + ".items." + i)!=null){
            int slot = ConfigMenus.get(inventoryName).getInt(inventoryName + ".items." + i + ".slot");
            if(slot<totalSlot) inventory.setItem(slot,getItem(player,".items.",inventoryName,i));
            i++;
        }
        return inventory;
    }

    public static ItemStack getItem(Player player, String items, String inventoryName,int i) {
        ItemStack item = null;
        if(ConfigMenus.get(inventoryName).get(inventoryName + items + i + ".type")==null){return null;}
        switch (Objects.requireNonNull(ConfigMenus.get(inventoryName).getString(inventoryName + items + i + ".type"))){
            case "item":
                Material material = Material.getMaterial(getMaterialFromConfig(inventoryName,items,i).toUpperCase());
                if(material==null){break;}

                item = new ItemStack(material, getAmountFromConfig(inventoryName,items,i));
                break;
            case "head":
                item = new ItemStack(Material.PLAYER_HEAD, getAmountFromConfig(inventoryName,items,i), (short) 3);
                SkullMeta customHeadPlayer = (SkullMeta) item.getItemMeta();

                PlayerProfile playerProfile = Bukkit.getOfflinePlayer(UUID.randomUUID()).getPlayerProfile();
                ProfileProperty profileProperty = new ProfileProperty("textures", getMaterialFromConfig(inventoryName,items,i));
                playerProfile.setProperty(profileProperty);

                customHeadPlayer.setPlayerProfile(playerProfile);
                item.setItemMeta(customHeadPlayer);
                break;
            case "playerHead":
                item = new ItemStack(Material.PLAYER_HEAD, getAmountFromConfig(inventoryName,items,i), (short) 3);
                SkullMeta myAwesomeSkullMeta = (SkullMeta) item.getItemMeta();
                myAwesomeSkullMeta.setOwningPlayer(player);

                item.setItemMeta(myAwesomeSkullMeta);
                break;
        }

        if(item==null){return null;}
        ItemMeta itemMeta = item.getItemMeta();

        // Set  the name of the item
        Component name = MyUtils.getComponent(setPlaceHolder(player,ConfigMenus.get(inventoryName).getString(inventoryName + items + i + ".nameItem")));
        itemMeta.displayName(name);

        // check if the item is glowing or not.
        if(ConfigMenus.get(inventoryName).get(inventoryName + items + i + ".glow")!=null){
            if(ConfigMenus.get(inventoryName).getBoolean(inventoryName + items + i + ".glow")){
                itemMeta.addEnchant(Enchantment.CHANNELING,1,true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }

        // set the lore and set the itemMeta.
        itemMeta.lore(MyUtils.getListComponent(player, ConfigMenus.get(inventoryName).getStringList(inventoryName + items + i + ".lore")));
        item.setItemMeta(itemMeta);

        return item;
    }

    public static String getMaterialFromConfig(String inventoryName, String items, int i){
        return Objects.requireNonNull(ConfigMenus.get(inventoryName).getString(inventoryName + items + i + ".material"));
    }

    public static int getAmountFromConfig(String inventoryName, String items, int i){
        int amount = ConfigMenus.get(inventoryName).getInt(inventoryName + items + i + ".amount");
        if(amount==0){amount=1;}
        return amount;
    }

    public static String setPlaceHolder(Player player, String message){
        return PlaceholderAPI.setPlaceholders(player, message);
    }

    //keyword == .customFeature.fillmenu or .customFeature.edges
    public static ItemStack getItemEdgeFill(Player player, String keyword, String inventoryName) {
        ItemStack item = null;
        if(ConfigMenus.get(inventoryName).get(inventoryName + keyword + ".type")==null){return null;}
        switch (Objects.requireNonNull(ConfigMenus.get(inventoryName).getString(inventoryName + keyword + ".type"))){
            case "item":
                Material material = Material.getMaterial(Objects.requireNonNull(ConfigMenus.get(inventoryName).getString(inventoryName + keyword + ".material")).toUpperCase());
                if(material==null){break;}

                item = new ItemStack(material, 1);
                break;
            case "head":
                item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta customHeadPlayer = (SkullMeta) item.getItemMeta();

                PlayerProfile playerProfile = Bukkit.getOfflinePlayer(UUID.randomUUID()).getPlayerProfile();
                ProfileProperty profileProperty = new ProfileProperty("textures", Objects.requireNonNull(ConfigMenus.get(inventoryName).getString(inventoryName + keyword + ".material")).toUpperCase());
                playerProfile.setProperty(profileProperty);

                customHeadPlayer.setPlayerProfile(playerProfile);
                item.setItemMeta(customHeadPlayer);
                break;
            case "playerHead":
                item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta myAwesomeSkullMeta = (SkullMeta) item.getItemMeta();
                myAwesomeSkullMeta.setOwningPlayer(player);

                item.setItemMeta(myAwesomeSkullMeta);
                break;
        }

        if(item==null){return null;}
        ItemMeta itemMeta = item.getItemMeta();

        // Set  the name of the item
        Component name = MyUtils.getComponent(setPlaceHolder(player,ConfigMenus.get(inventoryName).getString(inventoryName + keyword + ".nameItem")));
        itemMeta.displayName(name);

        // check if the item is glowing or not.
        if(ConfigMenus.get(inventoryName).get(inventoryName + keyword + ".glow")!=null){
            if(ConfigMenus.get(inventoryName).getBoolean(inventoryName + keyword + ".glow")){
                itemMeta.addEnchant(Enchantment.CHANNELING,1,true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }

        // set the lore and set the itemMeta.
        itemMeta.lore(MyUtils.getListComponent(player, ConfigMenus.get(inventoryName).getStringList(inventoryName + keyword + ".lore")));
        item.setItemMeta(itemMeta);

        return item;
    }


    //TODO metterlo nel config, vediamo.
    public static Inventory createEdge(Player player,String keyword,String inventoryName, Inventory inventory){
        ItemStack edgeItem = getItemEdgeFill(player,keyword,inventoryName);
        int nRow = ConfigMenus.get(inventoryName).getInt(inventoryName+".rows");
        int totalSlot = nRow*9;

        for(int i=0;i<9;i++){
            inventory.setItem(i, edgeItem);
            if(i==0){
                for (int j=0;j<=(totalSlot-1);j=j+9){
                    inventory.setItem(j, edgeItem);
                    if(j==(totalSlot-9)){
                        for (int y=(totalSlot-9);y<=(totalSlot-1);y++){
                            inventory.setItem(y, edgeItem);
                        }
                    }
                }
            }
            if(i==8){
                for (int j=8;j<=(totalSlot-1);j=j+9){
                    inventory.setItem(j, edgeItem);
                }
            }
        }
        return inventory;
    }

    public static Inventory fillInventory(Player player, String keyword, String inventoryName, Inventory inventory){
        ItemStack fillItem = getItemEdgeFill(player,keyword,inventoryName);

        for(int i=0;i<inventory.getSize();i++){
            inventory.setItem(i, fillItem);
        }
        return inventory;
    }

}
