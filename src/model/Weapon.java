package model;

import control.DiceController;

import java.util.ArrayList;

public class Weapon {
    private final String name, description;
    private final Faction faction;
    private final ArrayList<WeaponTag> tags = new ArrayList<WeaponTag>();
    private final double range;
    private final int strength, armorPierce;

    public Weapon(String name, String description, String faction, double range, int strength,
                  int armorPierce, String tagsString) {
        this.name = name;
        this.description = description;
        this.faction = FactionFactory.getFaction(faction);
        this.range = range;
        this.strength = strength;
        this.armorPierce = armorPierce;

        loadTags(tagsString);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public void fire(Character c, Squad target) {
        // For now, just shoot once
        int numShots = 1;
        // Roll to hit
        DiceController.addDice(c.id, numShots, 7 - c.getBallisticSkill());
    }

    private class WeaponTag {
        WeaponType type;
        int value1, value2;

        public WeaponTag(WeaponType type, int value1, int value2) {
            this.type = type;
            this.value1 = value1;
            this.value2 = value2;
        }

        public String toString() {
            if(value2 != 1) return type + " " + value1 + "/" + value2;
            else if(value1 != 1) return type + " " + value1;
            else return "" + type;
        }

    }

    public enum WeaponType {
        Armourbane, Assault, Barrage, Blast, Blind, Concussive, Fleshbane, Force, GetsHot, Graviton, Haywire, Heavy,
        IgnoresCover, Interceptor, Melee, Melta, OneUseOnly, Ordnance, Pistol, Poisoned, RapidFire, Rending, Salvo,
        SavantLock, Shred, Skyfire, Sniper, SpecialistWeapon, Strikedown, Torrent, TwinLinked, TwoHanded, Unwieldy,
        TEST
    };

    private void loadTags(String tagString) {
        String[] split = tagString.split(",");
        for(String s : split) {
            addTag(s);
        }
    }

    public void addTag(String tagString) {
        if(tagString.charAt(0) == '\"') tagString = tagString.substring(1,tagString.length() - 1);
        WeaponType type;
        int value1 = 1, value2 = 1;

        String[] arr = tagString.trim().split("/");
        if(arr.length > 1) value1 = Integer.parseInt(arr[1]); // Read in first value if it exists
        if(arr.length > 2) value2 = Integer.parseInt(arr[2]); // Read in second value if it exists

        switch(arr[0].toLowerCase()) {
            case "armourbane": type = WeaponType.Armourbane;
            break;
            case "assault": type = WeaponType.Assault;
            break;
            case "barrage": type = WeaponType.Barrage;
            break;
            case "blast": type = WeaponType.Blast;
            break;
            case "blind": type = WeaponType.Blind;
            break;
            case "concussive": type = WeaponType.Concussive;
            break;
            case "fleshbane": type = WeaponType.Fleshbane;
            break;
            case "force": type = WeaponType.Force;
            break;
            case "gets hot": type = WeaponType.GetsHot;
            break;
            case "graviton": type = WeaponType.Graviton;
            break;
            case "haywire": type = WeaponType.Haywire;
            break;
            case "heavy": type = WeaponType.Heavy;
            break;
            case "ignores cover": type = WeaponType.IgnoresCover;
            break;
            case "interceptor": type = WeaponType.Interceptor;
            break;
            case "melee": type = WeaponType.Melee;
            break;
            case "melta": type = WeaponType.Melta;
            break;
            case "one use only": type = WeaponType.OneUseOnly;
            break;
            case "ordnance": type = WeaponType.Ordnance;
            break;
            case "pistol": type = WeaponType.Pistol;
            break;
            case "poisoned": type = WeaponType.Poisoned;
            break;
            case "rapid fire": type = WeaponType.RapidFire;
            break;
            case "rending": type = WeaponType.Rending;
            break;
            case "salvo": type = WeaponType.Salvo;
            break;
            case "savant lock": type = WeaponType.SavantLock;
            break;
            case "shred": type = WeaponType.Shred;
            break;
            case "skyfire": type = WeaponType.Skyfire;
            break;
            case "sniper": type = WeaponType.Sniper;
            break;
            case "specialist weapon": type = WeaponType.SpecialistWeapon;
            break;
            case "strikedown": type = WeaponType.Strikedown;
            break;
            case "torrent": type = WeaponType.Torrent;
            break;
            case "twin-linked": type = WeaponType.TwinLinked;
            break;
            case "two-handed": type = WeaponType.TwoHanded;
            break;
            case "unwieldy": type = WeaponType.Unwieldy;
            break;
            default: type = WeaponType.TEST;
            break;
        }

        tags.add(new WeaponTag(type, value1, value2));
    }

    public String toString() {
        return name;
    }
}
