package model;

import control.DiceController;
import javafx.geometry.Point2D;

import java.util.ArrayList;

/**
 *  Scenery is the Model representation of all game units.
 */
public class Character extends GameObject{
    public final int id;
    private Faction faction;
    private final String type; //TODO change this to enum
    private final int weaponSkill, ballisticSkill, strength, toughness, wounds, initiative, attacks, leadership, save;
    private int currentWounds;
    private final ArrayList<Gear> gear = new ArrayList<>();
    private final ArrayList<Weapon> weapons = new ArrayList<>();
    private Squad parent;

    private boolean hasMovedThisTurn = false;
    private boolean hasFiredThisTurn = false;

    private static final double DEFAULT_MOVE_DISTANCE = 6;

    public Character(int id, String name, String faction, String type, int weaponSkill, int ballisticSkill,
                     int strength, int toughness, int wounds, int initiative, int attacks, int leadership, int save,
                     double height, double width) {
        super();
        this.id = id;
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
        this.shape = Shape.CIRCLE;
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

    public void addWeapon(Weapon addedWeapon) { weapons.add(addedWeapon); }

    public void removeWeapon(Weapon removedWeapon) { weapons.remove(removedWeapon); }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public Faction getFaction() {
        return faction;
    }

    public Squad getParent() {
        return parent;
    }

    public void setParent(Squad parent) {
        this.parent = parent;
    }

    public boolean hasMovedThisTurn() {
        return hasMovedThisTurn;
    }

    public boolean hasFiredThisTurn() {
        return hasFiredThisTurn;
    }

    @Override
    public void setPhantomCenter(Point2D phantomCenter) {
        double moveDistance = DEFAULT_MOVE_DISTANCE;
        if(phantomCenter.distance(getCenter()) <= moveDistance) {
            super.setPhantomCenter(phantomCenter);
        } else {
            Point2D newMove = phantomCenter.subtract(getCenter()).normalize().multiply(moveDistance);
            super.setPhantomCenter(getCenter().add(newMove));
        }

    }

    public void newTurn() {
        hasMovedThisTurn = false;
        hasFiredThisTurn = false;
    }

    public void fire(Weapon weapon, Squad target) {
        if(rangeTo(target) > weapon.getRange()) return; //Don't shoot if you're out of range
        //TODO check cover/line of sight
        weapon.fire(this, target);
    }

    private double rangeTo(Squad squad) {
        double range = rangeTo(squad.getCharacters().get(0));
        for(Character c : squad.getCharacters()) {
            double newRange = rangeTo(c);
            if(newRange < range) range = newRange;
        }
        return range;
    }

    private double rangeTo(Character c) {
        //Center to center for now
        return getCenter().distance(c.getCenter());
    }

    private Character(int id, String name, Faction faction, String type, int weaponSkill, int ballisticSkill,
                      int strength, int toughness, int wounds, int initiative, int attacks, int leadership, int save,
                      int currentWounds, double height, double width, Shape shape) {
        this.id = id;
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
        this.shape = shape;
    }

    @Override
    public Character clone() {
        Character c =  new Character(id,
                name, faction, type, weaponSkill, ballisticSkill, strength, toughness,
                wounds, initiative, attacks, leadership, save, currentWounds, height, width, shape);
        c.weapons.addAll(weapons);
        c.gear.addAll(gear);
        //TODO extras

        return c;
    }

    public String toString() {
        return name + " (" + currentWounds + "/" + wounds + ")";
    }

    public enum CharacterType {INFANTRY, BIKE, ARTILLERY, JUMP, JETPACK, BEAST, CAVALRY, MONSTROUS, FLYING_MONSTROUS,
        GARGANTUAN, FLYING_GARGANTUAN, VEHICLE, TRANSPORT, FLYER, CHARIOT, OPEN_TOPPED_VEHICLE, HEAVY_VEHICLE, FAST_VEHICLE,
        SKIMMER, WALKER, TANK, SUPER_HEAVY_VEHICLE, SUPER_HEAVY_WALKER, SUPER_HEAVY_FLYER, }

}
