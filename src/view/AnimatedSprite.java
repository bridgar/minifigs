package view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import model.GameObject;

public class AnimatedSprite extends Sprite{
    private final Image[] images;
    private int frameIndex;

    public AnimatedSprite(GameObject object, Image[] images) {
        super(object);
        this.images = images;
        frameIndex = 0;
    }

    @Override
    public void render(GraphicsContext gc, Affine affine) {
        Point2D pos = getCenter();
        Affine af = affine.clone();
        af.appendTranslation(pos.getX(), pos.getY());
        af.appendRotation(getAngle());
        gc.transform(af);
        gc.drawImage(images[frameIndex], -1 * getWidth()/2,-1 * getHeight()/2, getWidth(), getHeight());
        frameIndex = (frameIndex + 1) % images.length;

        for(Sprite child : children) {
            child.render(gc, af);
        }
    }

    @Override
    public boolean contains(Affine affine, double canvasX, double canvasY) {
        return rectangleContains(affine, canvasX, canvasY);
    }
}
