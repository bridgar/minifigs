package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class SquadFactory {
    // Squads stored by faction then name.
    private static final HashMap<String, HashMap<String, Squad>> SQUADS =
            new HashMap<>();

    private static SquadFactory sf = new SquadFactory("data/Squads.csv");;

    private SquadFactory(String squadDataFile) {
        try {
            File input = new File(squadDataFile);
            FileReader fr = new FileReader(input);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine(); // skip header line
            String faction = "";
            while((line = reader.readLine()) != null) {
                String[] sp = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                if(sp[0].equals("Faction")) {
                    faction = sp[1];
                    SQUADS.put(faction, new HashMap<>());
                }
                else {
                    String name = sp[0];
                    String role = sp[1];
                    String characters = sp[2];
                    String wargear = sp[3];
                    String rules = sp[4];
                    String options = sp[5];
                    int points = Integer.parseInt(sp[6]);
                    Squad s = new Squad(faction, name, role, characters, wargear, rules, options, points);
                    SQUADS.get(faction).put(name, s);
                }
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static Squad getNewSquad(String faction, String name) {
        return SQUADS.get(faction).get(name).clone();
    }
}
