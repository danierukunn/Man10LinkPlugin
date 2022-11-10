package kbtdx.links.man10linkplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Man10LinkPlugin extends JavaPlugin {
    public static File mlinkfile;
    public static JavaPlugin Man10link;
    public static String prefix;
    @Override
    public void onEnable() {
        getCommand("urlop").setExecutor(new urlop());
        Man10link = this;
        saveDefaultConfig();
        prefix = Man10link.getConfig().getString("prefix");
        File file = new File(Man10link.getDataFolder().getAbsolutePath() + File.separator + "links");
        file.mkdir();

    }

}
