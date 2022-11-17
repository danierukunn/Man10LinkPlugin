package kbtdx.links.man10linkplugin;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class Man10LinkPlugin extends JavaPlugin {
    public static JavaPlugin Man10link;
    public static String prefix;
    public static File configfile;
    @Override
    public void onEnable() {
        getCommand("urlop").setExecutor(new urlop());
        getCommand("url").setExecutor(new url());
        getCommand("url").setTabCompleter(new url());
        Man10link = this;
        saveDefaultConfig();
        prefix = Man10link.getConfig().getString("prefix");
        File file = new File(Man10link.getDataFolder().getAbsolutePath() + File.separator + "links");
        file.mkdir();
    }

}
