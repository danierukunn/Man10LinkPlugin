package kbtdx.links.man10linkplugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import static kbtdx.links.man10linkplugin.Man10LinkPlugin.*;

import java.io.File;

public class Config {
    public static Data.MLink getConfig(YamlConfiguration config, File file){
        if (!Function.checknull(config)){
            Bukkit.broadcast(prefix + file.getName() + "の読み込みに失敗しました","mlink.op");
            return null;
        }
        return new Data.MLink(config.getString("key"));
    }
}
