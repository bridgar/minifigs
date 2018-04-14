package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class CharacterFactory {

    // Characters stored by faction then name.
    private static final HashMap<String, HashMap<String, Character>> TEMPLATES =
            new HashMap<>();

    private static final double INCH_TO_PIXEL = 50;

    private static CharacterFactory cf = new CharacterFactory("data/Characters.csv");;

    private CharacterFactory(String characterDataFile) {
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
                else {
                    String name = sp[0];
                    int ws = Integer.parseInt(sp[1]);
                    int bs = Integer.parseInt(sp[2]);
                    int s = Integer.parseInt(sp[3]);
                    int t = Integer.parseInt(sp[4]);
                    int w = Integer.parseInt(sp[5]);
                    int i = Integer.parseInt(sp[6]);
                    int a = Integer.parseInt(sp[7]);
                    int l = Integer.parseInt(sp[8]);
                    int save = Integer.parseInt(sp[9]);
                    String type = sp[10];
                    double height = Double.parseDouble(sp[11]) * INCH_TO_PIXEL;
                    double width = Double.parseDouble(sp[12]) * INCH_TO_PIXEL;
                    Character c = new Character(name, faction, type,
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
