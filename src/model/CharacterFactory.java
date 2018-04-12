package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class CharacterFactory {

    // Characters stored by faction then name.
    private static final HashMap<String, HashMap<String, Character>> TEMPLATES =
            new HashMap<>();

    public CharacterFactory(String characterDataFile) {
        try {
            File input = new File(characterDataFile);
            FileReader fr = new FileReader(input);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine(); // skip header line
            String faction = "";
            while((line = reader.readLine()) != null) {
                String[] sp = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                if(sp[0].equals("Faction")) {
                    faction = sp[1];
                    TEMPLATES.put(faction, new HashMap<>());
                }
                else { //TODO add model dimensions (height and width of Character) and shape
                    String name = sp[0];
                    String description = sp[1];
                    int ws = Integer.parseInt(sp[2]);
                    int bs = Integer.parseInt(sp[3]);
                    int s = Integer.parseInt(sp[4]);
                    int t = Integer.parseInt(sp[5]);
                    int w = Integer.parseInt(sp[6]);
                    int i = Integer.parseInt(sp[7]);
                    int a = Integer.parseInt(sp[8]);
                    int l = Integer.parseInt(sp[9]);
                    int save = Integer.parseInt(sp[10]);
                    double height = Double.parseDouble(sp[11]);
                    double width = Double.parseDouble(sp[12]);
                    Character c = new Character(name, description, faction,
                            ws, bs, s, t, w, i, a, l, save, height, width);
                    TEMPLATES.get(faction).put(name, c);
                }
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static Character getNewCharacter(String faction, String name) {
        return TEMPLATES.get(faction).get(name).clone();
    }


}
