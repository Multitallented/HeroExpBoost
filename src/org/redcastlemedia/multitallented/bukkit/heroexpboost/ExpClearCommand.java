package org.redcastlemedia.multitallented.bukkit.heroexpboost;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Multitallented
 */
public class ExpClearCommand {
    public ExpClearCommand(HeroExpBoost plugin, CommandSender cs, String[] args) {
        //validate name
        String name = args[0];
        Player player = Bukkit.getPlayer(args[0]);
        if (player != null) {
            name = player.getName();
        }
        name = name.toLowerCase();
        
        //check if player has a boost
        if (plugin.boostsContains(name) && plugin.getBoost(name).getDuration() != 0) {
            plugin.clearBoost(name);
            cs.sendMessage(ChatColor.GOLD + HeroExpBoost.NAME + " cleared " + name + "'s boost");
        } else {
            cs.sendMessage(ChatColor.RED + HeroExpBoost.NAME + name + " does not have a boost");
        }
    }
}
