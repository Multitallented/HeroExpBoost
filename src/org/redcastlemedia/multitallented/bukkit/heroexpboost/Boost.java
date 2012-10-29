package org.redcastlemedia.multitallented.bukkit.heroexpboost;

/**
 *
 * @author Multitallented
 */
public class Boost {
    private final String name;
    private final double multiplier;
    private final long duration;
    public Boost(String name, double multiplier, long duration) {
        this.name = name;
        this.multiplier = multiplier;
        this.duration = duration;
    }
    
    public String getName() {
        return name;
    }
    
    public double getMultiplier() {
        return multiplier;
    }
    
    public long getDuration() {
        return duration;
    }
}
