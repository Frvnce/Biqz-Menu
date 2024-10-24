package me.francesco.menu.utils;

import me.francesco.menu.inv.MenusInv;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MyUtils {

    //Use components
    public static Component getComponent(String text){
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text).decoration(TextDecoration.ITALIC,false);
    }

    public static String getString(Component textComponent){
        return LegacyComponentSerializer.legacyAmpersand().serialize(textComponent);
    }

    public static List<Component> getListComponent(Player player, List<String> lista){
        List<Component> listComp = new ArrayList<>();
        for (String i:lista) {
            listComp.add(getComponent(MenusInv.setPlaceHolder(player,i)).decoration(TextDecoration.ITALIC,false));
        }
        return listComp;
    }
}
