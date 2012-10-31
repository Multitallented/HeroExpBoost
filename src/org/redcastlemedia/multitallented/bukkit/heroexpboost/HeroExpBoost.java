package org.redcastlemedia.multitallented.bukkit.heroexpboost;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Multitallented
 */

public class HeroExpBoost extends JavaPlugin {
    
    private static Logger logger = Logger.getLogger("Minecraft");
    public static final String NAME = "[HeroExpBoost]";
    private final HashMap<String, Boost> expBoosts = new HashMap<>();
    
    @Override
    public void onEnable() {
        setupConfig();
        ExpBoostListener listener = new ExpBoostListener(this);
        Bukkit.getPluginManager().registerEvents(listener, this);
        log(NAME + " is enabled", Level.INFO);
    }
    
    @Override
    public void onDisable() {
        log(NAME + " is disabled", Level.INFO);
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {
        if (args.length > 3) {
            new ExpBoostCommand(this, cs, args);
            return true;
        } else if (args.length > 0) {
            new ExpClearCommand(this, cs, args);
            return true;
        }
        
        cs.sendMessage(ChatColor.GOLD + NAME + " help pages 1/1");
        cs.sendMessage(ChatColor.GRAY + "/expb <name> <multiplier> <number> <days|weeks|hours>");
        return true;
    }
    
    private void setupConfig() {
        File folder = getDataFolder();
        if (!folder.exists()) {
            folder.mkdir();
        }
        File configFile = new File(folder, "data.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                return;
            } catch (IOException ioe) {
                log(NAME + " failed to create new data.yml", Level.SEVERE);
                return;
            }
        }
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            log(NAME + " failed to load data.yml", Level.SEVERE);
            return;
        }
        for (String s : config.getKeys(false)) {
            System.out.println(s + ":" + config.getLong(s + ".duration"));
            expBoosts.put(s, new Boost(s, config.getDouble(s + ".multiplier", 1), config.getLong(s + ".duration")));
        }
    }
    
    public void putBoost(String name, Boost boost) {
        expBoosts.put(name, boost);
        File configFile = new File(getDataFolder(), "data.yml");
        if (!configFile.exists()) {
            log(NAME + "failed to save to data.yml", Level.SEVERE);
            return;
        }
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            log(NAME + " failed to load data.yml", Level.SEVERE);
            return;
        }
        config.set(name + ".multiplier", boost.getMultiplier());
        config.set(name + ".duration", boost.getDuration());
        try {
            config.save(configFile);
        } catch (IOException ioe) {
            log(NAME + "failed to save to data.yml", Level.SEVERE);
            return;
        }
    }
    
    public void clearBoost(String name) {
        expBoosts.remove(name);
        File configFile = new File(getDataFolder(), "data.yml");
        if (!configFile.exists()) {
            log(NAME + "failed to save to data.yml", Level.SEVERE);
            return;
        }
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            log(NAME + " failed to load data.yml", Level.SEVERE);
            return;
        }
        config.set(name + ".duration", 0);
        try {
            config.save(configFile);
        } catch (IOException ioe) {
            log(NAME + "failed to save to data.yml", Level.SEVERE);
            return;
        }
    }
    
    public boolean boostsContains(String name) {
        return expBoosts.containsKey(name);
    }
    
    public Boost getBoost(String name) {
        return expBoosts.get(name);
    }
    
    public static void log(String message, Level level) {
        logger.log(level,message);
    }
}
