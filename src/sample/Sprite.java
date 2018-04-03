package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;

import java.util.ArrayList;

public class Sprite {
    protected Image image;
    protected GameObject object;
    protected ArrayList<Sprite> children = new ArrayList<Sprite>();
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

    public double getAngle() { return object.getAngle(); }

    public void addChild(Sprite child) {
        children.add(child);
    }

    public void render(GraphicsContext gc, Affine affine) {
        Position pos = object.getPosition();
        Affine af = affine.clone();
        // Apply the translations and rotations to the affine of the world
        af.appendTranslation(pos.x, pos.y);
        af.appendRotation(getAngle());

        if(image != null) { // Don't render an invisible sprite
            gc.setTransform(af);
            gc.drawImage(image, -1 * getWidth() / 2, -1 * getHeight() / 2, getWidth(), getHeight());
        }
        for(Sprite child : children) {
            child.render(gc, af);
        }
    }

    public enum Shape { SQUARE, CIRCLE }
}

