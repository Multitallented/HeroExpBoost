package org.redcastlemedia.multitallented.bukkit.heroexpboost;

import com.herocraftonline.heroes.api.events.ExperienceChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author Multitallented
 */
public class ExpBoostListener implements Listener {
    private final HeroExpBoost plugin;
    public ExpBoostListener(HeroExpBoost plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onExpBoost(ExperienceChangeEvent event) {
        String name = event.getHero().getPlayer().getName();
        if (!plugin.boostsContains(name) && plugin.getBoost(name).getDuration() > System.currentTimeMillis()) {
            return;
        }
        
        event.setExpGain(event.getExpChange() * plugin.getBoost(name).getMultiplier());
    }
}
