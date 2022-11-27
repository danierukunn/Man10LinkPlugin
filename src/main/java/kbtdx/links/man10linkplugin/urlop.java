package kbtdx.links.man10linkplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static kbtdx.links.man10linkplugin.Man10LinkPlugin.*;

public class urlop implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("mlink.op")) {
            sender.sendMessage(prefix + ChatColor.RED + "権限がありません。");
        }else {
            if (command.getName().equalsIgnoreCase("urlop")){
                if (args.length == 0){
                    sender.sendMessage(prefix + ChatColor.YELLOW + "/urlop create|delete|send");
                    return true;
                }
                if (args[0].equalsIgnoreCase("create")){
                    if (args.length == 1){
                        sender.sendMessage(prefix + "§e登録名・URLが必要です。");
                        return true;
                    }
                    if (args.length == 2){
                        sender.sendMessage(prefix + "§eURLが必要です。");
                        return true;
                    }
                    if (args.length == 3){
                        File folder = new File(Man10link.getDataFolder().getAbsolutePath() + "/links/" + File.separator + args[1] + ".yml");
                        if (folder.exists()){
                            sender.sendMessage(prefix + ChatColor.RED + "すでにその登録名は存在します。");
                        }else {
                            YamlConfiguration yml = new YamlConfiguration();
                            yml.set("key", args[1]);
                            yml.set("url", args[2]);
                            try {
                                yml.save(folder);
                                sender.sendMessage(prefix + "§a作成に成功しました。");
                            } catch (IOException e){
                                e.printStackTrace();
                                sender.sendMessage(prefix + ChatColor.RED + args[1] + ".ymlの作成に失敗しました。");
                            }
                        }
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("delete")){
                    if (args.length == 1){
                        sender.sendMessage(prefix + "§e削除する登録名が必要です。");
                        return true;
                    }
                    if (args.length == 2){
                        File folder = new File(Man10link.getDataFolder().getAbsolutePath() + "/links/" + File.separator + args[1] + ".yml");
                        if (!folder.exists()){
                            sender.sendMessage(prefix + "§eその登録名は存在しません。");
                        }else {
                            folder.delete();
                            sender.sendMessage(prefix + "§e削除しました。");
                        }
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("send")){
                    YamlConfiguration yml = new YamlConfiguration();
                    if (args.length == 1){
                        sender.sendMessage(prefix + "§e登録名・プレイヤーが必要です。");
                    }
                    if (args.length == 2){
                        File folder = new File(Man10link.getDataFolder().getAbsolutePath() + "/links/" + File.separator + args[1] + ".yml");
                        try {
                            yml.load(folder);
                        } catch (IOException | InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        if (!folder.exists()){
                            sender.sendMessage(prefix + ChatColor.RED + "その登録名は存在しません。");
                        }else {
                            sender.sendMessage(prefix + yml.getString("url"));
                        }
                        return true;
                    }
                    if (args.length == 3){
                        File folder = new File(Man10link.getDataFolder().getAbsolutePath() + "/links/" + File.separator + args[1] + ".yml");
                        try {
                            yml.load(folder);
                        } catch (IOException | InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        try{
                            Player target = Bukkit.getPlayer(args[2]);
                            target.sendMessage(prefix + yml.getString("url"));
                        }catch (Exception e){
                            sender.sendMessage(prefix + ChatColor.RED + "そのプレイヤーはオフラインであるか、存在しません。");
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("urlop")){
            if (sender.hasPermission("mlink.op")){
                if (args[0].length() == 0){
                    return Arrays.asList("create","delete","send");
                }
                if (args[0].equalsIgnoreCase("s")){
                    if (args[0].length() >= 1 && args[0].length() <= 4){
                        return Collections.singletonList("send");
                    }
                    return Collections.singletonList("send");
                }
                if (args[0].equalsIgnoreCase("d")){
                    if (args[0].length() >= 1 && args[0].length() <= 6){
                        return Collections.singletonList("delete");
                    }
                    return Collections.singletonList("delete");
                }
                if (args[0].equalsIgnoreCase("c")){
                    if (args[0].length() >= 1 && args[0].length() <= 6){
                        return Collections.singletonList("create");
                    }
                    return Collections.singletonList("create");
                }
            }
        }
        return null;
    }
}
