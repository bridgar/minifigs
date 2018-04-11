package model;

import java.util.ArrayList;

/**
 *  Scenery is the Model representation of all game units.
 */
public class Character extends GameObject{
    private final String description, faction, type;
    private final int weaponSkill, ballisticSkill, strength, toughness, wounds, initiative, attacks, leadership, save;
    private int currentWounds;
    private final ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    private Unit parent;

    public Character(String name, String description, String faction, String type, int weaponSkill, int ballisticSkill,
                     int strength, int toughness, int wounds, int initiative, int attacks, int leadership, int save,
                     String weaponString, String extras) {
        this.name = name;
        this.description = description;
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

        currentWounds = wounds;

        //TODO weapons and extras
    }

    public String getDescription() {
        return description;
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

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void removeWeapon(Weapon weapon) {

    }



    //private constructor for cloning. Skips weapons and extras
    private Character(String name, String description, String faction, String type, int weaponSkill, int ballisticSkill,
                      int strength, int toughness, int wounds, int initiative, int attacks, int leadership, int save) {
        this.name = name;
        this.description = description;
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

        currentWounds = wounds;
    }

    public Character clone() {
        Character c =  new Character(name, description, faction, type, weaponSkill, ballisticSkill, strength, toughness,
                wounds, initiative, attacks, leadership, save);
        c.weapons.addAll(weapons);
        //TODO extras

        return c;
    }

    public String toString() {
        return name + " (" + currentWounds + "/" + wounds + ")";
    }

}
