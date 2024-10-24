package me.francesco.menu.tabs;

import me.francesco.menu.configs.ConfigMenus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class menuTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();
        Player player = (Player) commandSender;

        switch (strings.length){
            case 1:
                if(player.hasPermission("menu.admin.open")){
                    list.add("open");
                }
                if(player.hasPermission("menu.reload")){
                    list.add("reload");
                }
                if(player.hasPermission("menu.create")){
                    list.add("create");
                }
                for(String i : ConfigMenus.getAllFiles()){
                    if(player.hasPermission("menu."+i)){
                        list.add(i);
                    }
                }
                break;
            case 2:
                if((strings[0].equalsIgnoreCase("open") || strings[0].equalsIgnoreCase("reload")) &&
                        (player.hasPermission("menu.admin.open") || player.hasPermission("menu.admin.reload"))){
                    list.addAll(ConfigMenus.getAllFiles());
                }
                break;
        }

        return list;
    }


}
