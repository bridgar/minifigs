package model;

import java.util.ArrayList;

/**
 *  Scenery is the Model representation of all game units.
 */
public class Character extends GameObject{
    private Faction faction;
    private final String type;
    private final int weaponSkill, ballisticSkill, strength, toughness, wounds, initiative, attacks, leadership, save;
    private int currentWounds;
    private final ArrayList<Gear> gear = new ArrayList<>();;
    private final ArrayList<Weapon> weapons = new ArrayList<>();;
    private Squad parent;

    public Character(String name, String faction, String type, int weaponSkill, int ballisticSkill,
                     int strength, int toughness, int wounds, int initiative, int attacks, int leadership, int save,
                     double height, double width) {
        this.name = name;
        this.faction = FactionFactory.getFaction(faction);
        this.type = type;
        this.weaponSkill = weaponSkill;
        this.ballisticSkill = ballisticSkill;
        this.strength = strength;
        this.toughness = toughness;
        this.wounds = wounds;
        this.initiative = initiative;
        this.attacks = attacks;
        this.leadership = leadership;
        this.save = save;
        currentWounds = wounds;

        this.height = height;
        this.width = width;

        //TODO weapons and extras
    }

    public int getWeaponSkill() {
        return weaponSkill;
    }

    public int getBallisticSkill() {
        return ballisticSkill;
    }

    public int getStrength() {
        return strength;
    }

    public int getToughness() {
        return toughness;
    }

    public int getWounds() {
        return wounds;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getAttacks() {
        return attacks;
    }

    public int getLeadership() {
        return leadership;
    }

    public int getSave() {
        return save;
    }

    public int getCurrentWounds() {
        return currentWounds;
    }

    public void setCurrentWounds(int currentWounds) {
        this.currentWounds = currentWounds;
    }

    public ArrayList<Gear> getGear() { return gear; }

    public void addGear(Gear addedGear) {
        gear.add(addedGear);
    }

    public void removeGear(Gear removedGear) {
        gear.remove(removedGear);
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    private Character(String name, Faction faction, String type, int weaponSkill, int ballisticSkill,
    int strength, int toughness, int wounds, int initiative, int attacks, int leadership, int save,
    int currentWounds, double height, double width, Squad parent) {
        this.name = name;
        this.faction = faction;
        this.type = type;
        this.weaponSkill = weaponSkill;
        this.ballisticSkill = ballisticSkill;
        this.strength = strength;
        this.toughness = toughness;
        this.wounds = wounds;
        this.initiative = initiative;
        this.attacks = attacks;
        this.leadership = leadership;
        this.save = save;
        this.currentWounds = currentWounds;
        this.height = height;
        this.width = width;
        this.parent = parent;

    }

    public Character clone() {
        Character c =  new Character(name, faction, type, weaponSkill, ballisticSkill, strength, toughness,
                wounds, initiative, attacks, leadership, save, currentWounds, height, width, parent);
        c.weapons.addAll(weapons);
        c.gear.addAll(gear);
        //TODO extras

        return c;
    }

    public String toString() {
        return name + " (" + currentWounds + "/" + wounds + ")";
    }

}
