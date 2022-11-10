package kbtdx.links.man10linkplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class url implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("url"))
        {
            sender.sendMessage(Component.text("てすと！"));
            return true;
        }
        return true;
    }
}
