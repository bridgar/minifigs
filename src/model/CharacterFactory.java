package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class CharacterFactory {

    // Characters stored by faction, type, then name.
    private static final HashMap<String, HashMap<String, HashMap<String, Character>>> TEMPLATES =
            new HashMap<String, HashMap<String, HashMap<String, Character>>>();

    public CharacterFactory(String characterDataFile) {
        try {
            File input = new File(characterDataFile);
            FileReader fr = new FileReader(input);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            String faction = "";
            String type = "";
            while((line = reader.readLine()) != null) {
                String[] sp = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                if(sp[0].equals("Faction")) {
                    faction = sp[1];
                    TEMPLATES.put(faction, new HashMap<String, HashMap<String, Character>>());
                }
                else if(sp[0].equals("Type")) {
                    type = sp[1];
                    TEMPLATES.get(faction).put(type, new HashMap<String, Character>());
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
                    String weapons = sp[11];
                    String extras = "";
                    if(sp.length == 13) extras = sp[12];
                    Character c = new Character(name, description, faction, type,
                            ws, bs, s, t, w, i, a, l, save, weapons, extras);
                    TEMPLATES.get(faction).get(type).put(name, c);
                }
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static Character getNewCharacter(String faction, String type, String name) {
        return TEMPLATES.get(faction).get(type).get(name).clone();
    }


}
