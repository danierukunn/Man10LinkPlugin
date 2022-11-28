package kbtdx.links.man10linkplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static kbtdx.links.man10linkplugin.Man10LinkPlugin.*;

public class url implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("url"))
        {
            if (args.length == 0){
                sender.sendMessage(prefix + ChatColor.YELLOW + "/url 登録名");
                return true;
            }else {
                if (args.length == 1){
                    File folder = new File(Man10link.getDataFolder().getAbsolutePath() + "/links/" + File.separator + args[0] + ".yml");
                    YamlConfiguration yml = new YamlConfiguration();
                    if (!folder.exists()){
                        sender.sendMessage(prefix + ChatColor.RED + "その登録名は存在しません。");
                    }else {
                        try {
                            yml.load(folder);
                            sender.sendMessage(prefix + yml.getString("url"));
                        } catch (IOException | InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return true;
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("url")){
            if (args.length == 1){
                return commands;
            }
        }
        return null;
    }
}
