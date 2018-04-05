package view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import model.GameObject;

public class ImageSprite extends Sprite {

    protected Image image;

    public ImageSprite(GameObject object, Image image) {
        super(object);
        this.image = image;
    }

    @Override
    public void render(GraphicsContext gc, Affine affine){
        Point2D center = object.getCenter();
        Affine af = affine.clone();
        // Apply the translations and rotations to the affine of the world
        af.appendTranslation(center.getX(), center.getY());
        af.appendRotation(getAngle());

        gc.setTransform(af);
        gc.drawImage(image, -1 * getWidth() / 2, -1 * getHeight() / 2, getWidth(), getHeight());
        for(Sprite child : children) {
            child.render(gc, af);
        }
    }

    @Override
    public boolean contains(Affine affine, double canvasX, double canvasY) {
        return rectangleContains(affine, canvasX, canvasY);
    }
}
