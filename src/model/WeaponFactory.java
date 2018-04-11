package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class WeaponFactory {
    // Weapons stored by faction, type, then name.
    private static final HashMap<String, HashMap<String, HashMap<String, Weapon>>> TEMPLATES =
            new HashMap<String, HashMap<String, HashMap<String, Weapon>>>();

    public WeaponFactory(String weaponDataFile) {
        try {
            File input = new File(weaponDataFile);
            FileReader fr = new FileReader(input);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            String faction = "";
            String type = "";
            while((line = reader.readLine()) != null) {
                String[] sp = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                if(sp[0] == "Faction") {
                    faction = sp[1];
                    TEMPLATES.put(faction, new HashMap<String, HashMap<String, Weapon>>());
                }
                else if(sp[0] == "Type") {
                    type = sp[1];
                    TEMPLATES.get(faction).put(type, new HashMap<String, Weapon>());
                }
                else {
                    String name = sp[0];
                    String description = sp[1];
                    double range = Double.parseDouble(sp[2]);
                    int s = Integer.parseInt(sp[3]);
                    int ap = Integer.parseInt(sp[4]);
                    String extras = sp[5];
                    Weapon w = new Weapon(name, description, faction, type, range, s, ap, extras);
                    TEMPLATES.get(faction).get(type).put(name, w);
                }
            }

        } catch(Exception e) {

        }
    }

    public Weapon getWeapon(String faction, String type, String name) {
        return TEMPLATES.get(faction).get(type).get(name);
    }
}
