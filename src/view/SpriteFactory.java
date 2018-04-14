package view;


import javafx.scene.image.Image;
import model.Character;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class SpriteFactory {
    // Characters stored by faction then name.
    private static final HashMap<String, HashMap<String, String>> SPRITES =
            new HashMap<>();

    private static SpriteFactory cf = new SpriteFactory("data/Sprites.csv");;

    private SpriteFactory(String characterDataFile) {
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
                    SPRITES.put(faction, new HashMap<>());
                }
                else {
                    String name = sp[0];
                    String type = sp[1];
                    SPRITES.get(faction).put(name, type);
                }
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static Sprite getNewCharacter(Character object) {
        String type = SPRITES.get(object.getFaction().toString()).get(object.name);
        if(type.equals("circle")) return new SimpleSprite(object);
        else if(type.equals("rectangle")) return new SimpleSprite(object);
        else return new ImageSprite(object, new Image("media/" + object.getFaction() + "/" + type));
    }
}
