package kbtdx.links.man10linkplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static kbtdx.links.man10linkplugin.Man10LinkPlugin.*;

public class urlop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("mlink.op")) {
            sender.sendMessage(prefix + ChatColor.RED + "権限がありません。");
        }else {
            if (command.getName().equalsIgnoreCase("urlop")){
                if (args.length == 0){
                    sender.sendMessage(prefix + ChatColor.YELLOW + "/urlop create|delete|list|send");
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
                        try{
                            Player target = Bukkit.getPlayer(args[2]);
                            sender.sendMessage(prefix + "§e送信しました。");
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
}
