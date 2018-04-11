package model;

public class Weapon {
    private final String name, description, faction, type;
    private final double range;
    private final int strength, armorPierce;

    public Weapon(String name, String description, String faction, String type, double range, int strength,
                  int armorPierce, String extras) {
        this.name = name;
        this.description = description;
        this.faction = faction;
        this.type = type;
        this.range = range;
        this.strength = strength;
        this.armorPierce = armorPierce;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public double getRange() {
        return range;
    }

    public int getStrength() {
        return strength;
    }

    public int getArmorPierce() {
        return armorPierce;
    }
}
