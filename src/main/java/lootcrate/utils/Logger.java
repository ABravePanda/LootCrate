package lootcrate.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "LOG: " + ChatColor.WHITE + message);
    }

    public static void warn(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "WARNING: " + ChatColor.YELLOW + message);
    }

    public static void error(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "ERROR: " + ChatColor.RED + message);
    }
}
