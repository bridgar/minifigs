package model;

public class Faction {
    private final String name, description;
    private String imageLocation;

    public Faction(String name, String description, String imageLocation) {
        this.name = name;
        this.description = description;
        this.imageLocation = imageLocation;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageLocation() {
        return imageLocation;
    }

}
