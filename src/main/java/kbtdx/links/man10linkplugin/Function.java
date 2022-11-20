package kbtdx.links.man10linkplugin;

import org.bukkit.configuration.file.YamlConfiguration;

public class Function {
    public static boolean checknull(YamlConfiguration file){
        return file.getString("key") != null;
    }
}
