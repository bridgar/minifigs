package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class SquadRoleFactory {
    private static final HashMap<String, SquadRole> SQUAD_ROLES = new HashMap<>();

    private SquadRoleFactory srf = new SquadRoleFactory("data/SquadRoles.csv");

    private SquadRoleFactory(String fileLocation) {
        try {
            File input = new File(fileLocation);
            FileReader fr = new FileReader(input);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine(); // skip header line
            while((line = reader.readLine()) != null) {
                String[] sp = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                String name = sp[0];
                String description = sp[1];
                String imageLocation = sp[2];

                SquadRole sr = new SquadRole(name, description, imageLocation);
                SQUAD_ROLES.put(name, sr);
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static SquadRole getSquadRole(String name) {
        return SQUAD_ROLES.get(name);
    }
}
