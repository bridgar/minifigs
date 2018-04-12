package model;

import java.util.ArrayList;

public class Squad {

    private final Faction faction;
    private final String name;
    private final SquadRole role;
    private final ArrayList<Character> characters = new ArrayList<>();
    private final ArrayList<Option> options = new ArrayList<>();
    private Detachment parent;
    private int pointCost;

    public Squad(String faction, String name, String role, String characters, String options, Detachment parent, int pointCost) {
        this.faction = FactionFactory.getFaction(faction);
        this.name = name;
        this.role = SquadRoleFactory.getSquadRole(role);
        prepareCharacters(characters);
        prepareOptions(options);
        this.parent = parent;
        this.pointCost = pointCost;
    }

    private void prepareCharacters(String chars) {

    }

    private void prepareOptions(String ops) {
        //TODO this is going to be very complicated
    }

    // Setup option
    private class Option {

    }



}
