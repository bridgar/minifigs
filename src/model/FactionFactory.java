package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class FactionFactory {
    private static final HashMap<String, Faction> FACTIONS = new HashMap<>();

    private FactionFactory ff = new FactionFactory("data/Factions.csv");

    private FactionFactory(String fileLocation) {
        try {
            File input = new File(fileLocation);
            FileReader fr = new FileReader(input);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine(); // skip header line
            String faction = "";
            while((line = reader.readLine()) != null) {
                String[] sp = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                String name = sp[0];
                String description = sp[1];
                String imageLocation = sp[2];

                Faction f = new Faction(name, description, imageLocation);
                FACTIONS.put(name, f);
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static Faction getFaction(String name) {
        return FACTIONS.get(name);
    }
}
