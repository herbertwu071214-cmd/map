package com.herb.macondo.map.model;

public class Upgrade {
    public enum UpgradeType {
        DAMAGE,
        PROJECTILE_COUNT,
        FIRE_RATE,
        MAX_HEALTH,
        ROCKET,
        DAGGER
    }

    private UpgradeType type;
    private String name;
    private String description;

    public Upgrade(UpgradeType type) {
        this.type = type;
        switch(type) {
            case DAMAGE:
                this.name = "Sharpened Blade";
                this.description = "+20% damage";
                break;
            case PROJECTILE_COUNT:
                this.name = "Shotgun Spread";
                this.description = "Fire +1 projectile";
                break;
            case FIRE_RATE:
                this.name = "Swift Trigger";
                this.description = "-10% cooldown";
                break;
            case MAX_HEALTH:
                this.name = "Vitality";
                this.description = "+20 max HP";
                break;
            case ROCKET:
                this.name = "Rocket Launcher";
                this.description = "Unlock rockets (press R)";
                break;
            case DAGGER:
                this.name = "Orbital Dagger";
                this.description = "A dagger orbits around you";
                break;
        }
    }

    public UpgradeType getType() { return type; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}

