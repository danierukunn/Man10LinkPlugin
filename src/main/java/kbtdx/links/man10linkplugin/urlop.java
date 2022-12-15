package kbtdx.links.man10linkplugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static kbtdx.links.man10linkplugin.Man10LinkPlugin.*;
import static net.kyori.adventure.text.Component.text;

public class urlop implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("mlink.op")) {
            sender.sendMessage(prefix + ChatColor.RED + "権限がありません。");
        }else {
            if (command.getName().equalsIgnoreCase("urlop")){
                if (args.length == 0){
                    sendhelp(sender);
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
                            commands.remove(args[1]);
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
                            sender.sendMessage(Component.text(prefix + "§n§eここをクリック§r§fでMan10Wikiを開きます").clickEvent(ClickEvent.openUrl(Objects.requireNonNull(yml.getString("url")))));
                        }
                        return true;
                    }
                    if (args.length == 3){
                        File folder = new File(Man10link.getDataFolder().getAbsolutePath() + "/links/" + File.separator + args[1] + ".yml");
                        if (!folder.exists()){
                            sender.sendMessage(prefix + ChatColor.RED + "その登録名は存在しません。");
                        }else {
                            try {
                                yml.load(folder);
                            } catch (IOException | InvalidConfigurationException e) {
                                throw new RuntimeException(e);
                            }
                            try{
                                Player target = Bukkit.getPlayer(args[2]);
                                target.sendMessage(Component.text(prefix + "§n§eここをクリック§r§fでMan10Wikiを開きます").clickEvent(ClickEvent.openUrl(Objects.requireNonNull(yml.getString("url")))));
                            }catch (Exception e){
                                sender.sendMessage(prefix + ChatColor.RED + "そのプレイヤーはオフラインであるか、存在しません。");
                                return false;
                            }
                        }
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("help")){
                    sendhelp(sender);
                }
                if (args[0].equalsIgnoreCase("reload")){
                    File file = new File(Man10link.getDataFolder().getAbsolutePath() + File.separator + "links");
                    File[] list = file.listFiles();
                    if (list != null){
                        for (File folder : list){
                            YamlConfiguration yml = YamlConfiguration.loadConfiguration(folder);
                            List<String> values = Collections.singletonList(yml.getString("key"));
                            commands.addAll(values);
                        }
                    }
                    Man10link.reloadConfig();
                    sender.sendMessage(prefix + "§aリロードしました。");
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("urlop")){
            if (sender.hasPermission("mlink.op")){
                if (args.length == 1){
                    return Arrays.asList("create","delete","send","help","reload");
                }
                if (args.length == 2){
                    return commands;
                }
            }
        }
        return null;
    }

    public void sendhelp(CommandSender sender){
        sender.sendMessage("=======§dMa§fn§a10§eLink§f=======");
        sender.sendMessage("§a/urlop create 登録名 URL");
        sender.sendMessage("-> §e表示するURLを作成します。");
        sender.sendMessage("§a/urlop delete 登録名");
        sender.sendMessage("-> §e表示するURLを削除します。");
        sender.sendMessage("§a/urlop send 登録名 プレイヤー");
        sender.sendMessage("-> §e引数で指定したプレイヤーにURLを送信します。");
        sender.sendMessage("§a/urlop reload");
        sender.sendMessage("-> §elinkファイル・コンフィグをリロードします。");
        sender.sendMessage("=======================");
    }
}
