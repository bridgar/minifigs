package model;

import java.util.ArrayList;

public class Weapon {
    private final String name, description, faction;
    private final ArrayList<WeaponTag> tags = new ArrayList<WeaponTag>();
    private final double range;
    private final int strength, armorPierce;

    public Weapon(String name, String description, String faction, double range, int strength,
                  int armorPierce, String tagsString) {
        this.name = name;
        this.description = description;
        this.faction = faction;
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

    private class WeaponTag {
        WeaponType type;
        int value1, value2;

        public WeaponTag(WeaponType type, int value1, int value2) {
            this.type = type;
            this.value1 = value1;
            this.value2 = value2;
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
        WeaponType type;
        int value1 = 1, value2 = 1;

        String[] arr = tagString.trim().split("/");
        if(arr.length > 1) value1 = Integer.parseInt(arr[1]); // Read in first value if it exists
        if(arr.length > 2) value2 = Integer.parseInt(arr[2]); // Read in second value if it exists

        switch(arr[0].toLowerCase()) {
            case "armourbane": type = WeaponType.Armourbane;
            case "assault": type = WeaponType.Assault;
            case "barrage": type = WeaponType.Barrage;
            case "blast": type = WeaponType.Blast;
            case "blind": type = WeaponType.Blind;
            case "concussive": type = WeaponType.Concussive;
            case "fleshbane": type = WeaponType.Fleshbane;
            case "force": type = WeaponType.Force;
            case "gets hot": type = WeaponType.GetsHot;
            case "graviton": type = WeaponType.Graviton;
            case "haywire": type = WeaponType.Haywire;
            case "heavy": type = WeaponType.Heavy;
            case "ignores cover": type = WeaponType.IgnoresCover;
            case "interceptor": type = WeaponType.Interceptor;
            case "melee": type = WeaponType.Melee;
            case "melta": type = WeaponType.Melta;
            case "one use only": type = WeaponType.OneUseOnly;
            case "ordnance": type = WeaponType.Ordnance;
            case "pistol": type = WeaponType.Pistol;
            case "poisoned": type = WeaponType.Poisoned;
            case "rapid fire": type = WeaponType.RapidFire;
            case "rending": type = WeaponType.Rending;
            case "salvo": type = WeaponType.Salvo;
            case "savant lock": type = WeaponType.SavantLock;
            case "shred": type = WeaponType.Shred;
            case "skyfire": type = WeaponType.Skyfire;
            case "sniper": type = WeaponType.Sniper;
            case "specialist weapon": type = WeaponType.SpecialistWeapon;
            case "strikedown": type = WeaponType.Strikedown;
            case "torrent": type = WeaponType.Torrent;
            case "twin-linked": type = WeaponType.TwinLinked;
            case "two-handed": type = WeaponType.TwoHanded;
            case "unwieldy": type = WeaponType.Unwieldy;
            default: type = WeaponType.TEST;
        }

        tags.add(new WeaponTag(type, value1, value2));
    }
}
