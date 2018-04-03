package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    protected Image image;
    protected GameObject object;
    //TODO scaling

    // Invisible sprite
    public Sprite(GameObject object) {
        this.object = object;
    }

    public Sprite(GameObject object, Image image) {
        this.object = object;
        this.image = image;
        object.setWidth(image.getWidth());   // Setting object sizes to image sizes
        object.setHeight(image.getHeight()); // Not sure if this is the right thing to do
    }

    public Position getPosition() { return object.getPosition(); }

    public double getHeight() { return object.getHeight(); }

    public double getWidth() { return object.getWidth(); }

    public void render(GraphicsContext gc) {
        if(image == null) return;
        Position pos = object.getPosition();
        gc.drawImage(image, pos.x, pos.y, getWidth(), getHeight());
    }

    public enum Shape { SQUARE, CIRCLE }
}

