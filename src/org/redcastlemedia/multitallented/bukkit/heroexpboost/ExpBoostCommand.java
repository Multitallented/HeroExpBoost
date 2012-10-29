package org.redcastlemedia.multitallented.bukkit.heroexpboost;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Multitallented
 */
public class ExpBoostCommand {
    public ExpBoostCommand(HeroExpBoost plugin, CommandSender cs, String[] args) {
        //Set Name
        String name = args[0];
        Player player = Bukkit.getPlayer(args[0]);
        if (player != null) {
            name = player.getName();
        }
        name = name.toLowerCase();
        
        //validate multiplier
        double multiplier = 1.0;
        try {
            multiplier = Double.parseDouble(args[1]);
            multiplier = multiplier > 0 ? multiplier : 0;
        } catch (NumberFormatException nfe) {
            cs.sendMessage(ChatColor.RED + HeroExpBoost.NAME + " enter a valid multiplier /expb <name> <multiplier> <number> <days|weeks|hours>");
            return;
        }
        
        //validate number
        long time = 0;
        try {
            time = Integer.parseInt(args[2]);
            time = time > 0 ? time : 0;
        } catch (NumberFormatException nfe) {
            cs.sendMessage(ChatColor.RED + HeroExpBoost.NAME + " enter a valid number /expb <name> <multiplier> <number> <days|weeks|hours>");
            return;
        }
        
        //validate time measurement
        switch (args[3]) {
            case "hours":
            case "hour":
                time *= 3600000;
                break;
            case "days":
            case "day":
                time *= 86400000;
                break;
            case "weeks":
            case "week":
                time *= 604800000;
                break;
            default:
                cs.sendMessage(ChatColor.RED + HeroExpBoost.NAME + " enter a valid time /expb <name> <multiplier> <number> <days|weeks|hours>");
                return;
        }
        cs.sendMessage(ChatColor.GOLD + HeroExpBoost.NAME + " " + multiplier + " boost awarded to " + name + " for " + args[2] + " " + args[3]);
        plugin.putBoost(name, new Boost(name, multiplier, time));
    }
}
