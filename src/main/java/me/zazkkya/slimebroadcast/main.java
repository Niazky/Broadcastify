package me.zazkkya.slimebroadcast;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    public final Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("broadcast")) {
            if (args.length == 0) {
                String Error = getConfig().getString("ErrorMessage");
                assert Error != null;
                Error = ChatColor.translateAlternateColorCodes('&', Error);
                sender.sendMessage(Error);
            }
            else {
                String Prefix = "";
                String Suffix = "";

                boolean ShowPrefix = getConfig().getBoolean("ShowPrefix");
                boolean ShowSuffix = getConfig().getBoolean("ShowSuffix");

                for (String arg : args) {
                    if (arg.contains("p:")) {
                        Prefix = arg;
                    }
                    else if (!(args[0]).contains("p:")) {
                        if (ShowPrefix) {
                            Prefix = getConfig().getString("Prefix");
                        }
                    }

                    if (arg.contains("s:")) {
                        Suffix = arg;
                    }
                    // Checks if our last arg is a not a suffix
                    else if (!(args[args.length-1]).contains("s:")) {
                        if (ShowSuffix) {
                            Suffix = getConfig().getString("Suffix");
                        }
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (String arg : args) {
                    sb.append(arg).append(" ");
                }

                assert Prefix != null;
                assert Suffix != null;
                String Content = sb.toString()
                        .replace(Prefix, "")
                        .replace(Suffix, "");
                String Broadcast = (Prefix+Content+Suffix)
                        .replace("p:", "")
                        .replace("s:", "");
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Broadcast));

            }

            if (cmd.getName().equalsIgnoreCase("sbreload")) {
                if (sender.hasPermission(Objects.requireNonNull(getConfig().getString("ReloadPerm"))))
                    try {
                        reloadConfig();
                        saveDefaultConfig();
                        String Reload = getConfig().getString("ReloadMessage");
                        assert Reload != null;
                        Reload = ChatColor.translateAlternateColorCodes('&', Reload);
                        sender.sendMessage(Reload);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "Failed to reload. Check syntax");
                    }
            }
            return true;
        }
        return true;
    }
}
