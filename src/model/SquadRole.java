package model;

public class SquadRole {
    private final String name, description;
    private String imageLocation;

    public SquadRole(String name, String description, String imageLocation) {
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

    public String toString() {
        return name;
    }
}
