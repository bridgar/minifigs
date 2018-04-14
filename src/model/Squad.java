package model;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Squad {

    private final Faction faction;
    private final String name;
    private final SquadRole role;
    private final ArrayList<Character> characters = new ArrayList<>();
    private Detachment parent;
    private int pointCost;

    private static final int ROW_WIDTH = 5;

    public Squad(String faction, String name, String role, String characters, String wargear, String rules, String options, int pointCost) {
        this.faction = FactionFactory.getFaction(faction);
        this.name = name;
        this.role = SquadRoleFactory.getSquadRole(role);
        prepareCharacters(characters);
        prepareGear(wargear);
        prepareRules(rules);
        prepareOptions(options);
        this.pointCost = pointCost;
    }

    private void prepareCharacters(String chars) {
        if(chars.charAt(0) == '\"') chars = chars.substring(1,chars.length() - 1);
        String[] split = chars.split(","); // Split each character type
        for(String character : split) {
            String[] spaceSplit = character.split(" ");
            int quantity = Integer.parseInt(spaceSplit[0]);
            String s = "";
            for(int i = 1; i < spaceSplit.length; i++) // Reconstruct the rest of the name
                s += spaceSplit[i] + " ";

            s = s.trim();
            for(int i = 0; i < quantity; i++)
                characters.add(CharacterFactory.getNewCharacter(faction.getName(), s));
            for(Character c : characters) {
                c.setParent(this);
            }
        }
    }

    private void prepareGear(String wargear) {
        if(wargear.charAt(0) == '\"') wargear = wargear.substring(1,wargear.length() - 1);
        // Split by characters that it applies to
        String[] groups = wargear.split("\\|");
        for(String group : groups) {
            // Split into name of group and gear
            String[] split = group.split(":");
            if(split[0].equals("All")) { // If the group is all, give the gear to the whole squad
                for (Character c : characters) {
                    addGearToCharacter(split[1], c);
                }
            } else {
                for(Character c : characters) {
                    if(c.name.equals(split[0])) // If the name matches the group, give the gear to that character
                        addGearToCharacter(split[1], c);
                }
            }
        }
    }

    private void addGearToCharacter(String gearString, Character character) {
        String[] gearArr = gearString.split(",");
        for(String gear : gearArr) {
            Weapon weapon = WeaponFactory.getWeapon(faction.toString(), gear);
            if(weapon != null) {
                    character.addWeapon(weapon);
            } else {
                //TODO implement gear
            }
        }
    }

    private void prepareRules(String rules) {
        //TODO this is going to be very complicated
    }

    private void prepareOptions(String ops) {
        //TODO this is going to be very complicated
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public Faction getFaction() {
        return faction;
    }

    public String getName() {
        return name;
    }

    public SquadRole getRole() {
        return role;
    }

    public Detachment getParent() {
        return parent;
    }

    public int getPointCost() {
        return pointCost;
    }

    public void initializePositions(Point2D firstLocation) {
        int x = 0;
        int y = 0;
        for(Character c : characters) {
            c.setCenter(firstLocation.add(x,y));

            y = y + x/ROW_WIDTH;
            x = (x+1) % ROW_WIDTH;
        }
    }

    private Squad(Faction faction, String name, SquadRole role, int pointCost) {
        this.faction = faction;
        this.name = name;
        this.role = role;
        this.pointCost = pointCost;
    }

    public Squad clone() {
        Squad s = new Squad(faction, name, role, pointCost);
        for(Character c : characters) {
            s.characters.add(c.clone());
        }
        for(Character c : s.characters)
            c.setParent(s);
        //TODO handle weapons and gear
        return s;
    }

    public String toString() {
        return name;
    }
}
