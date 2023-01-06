package kbtdx.links.man10linkplugin;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Man10LinkPlugin extends JavaPlugin {
    public static JavaPlugin Man10link;
    public static String prefix;
    public static String defmsg;
    public static List<String> commands = new ArrayList();
    @Override
    public void onEnable() {
        getCommand("urlop").setExecutor(new Urlop());
        getCommand("urlop").setTabCompleter(new Urlop());
        getCommand("url").setExecutor(new Url());
        getCommand("url").setTabCompleter(new Url());
        Man10link = this;
        saveDefaultConfig();
        prefix = Man10link.getConfig().getString("prefix");
        defmsg = Man10link.getConfig().getString("default-message");
        File file = new File(Man10link.getDataFolder().getAbsolutePath() + File.separator + "links");
        file.mkdir();
        File[] list = file.listFiles();
        if (list != null){
            for (File folder : list){
                YamlConfiguration yml = YamlConfiguration.loadConfiguration(folder);
                List<String> values = Collections.singletonList(yml.getString("key"));
                commands.addAll(values);
            }
        }
    }

}
