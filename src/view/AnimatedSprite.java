package view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import model.GameObject;

/**
 *  An AnimatedSprite is a Sprite which can be rendered with an array of Images which represent animation frames.
 */
public class AnimatedSprite extends Sprite{
    private final Image[] images;
    private int frameIndex;

    /**
     *  An AnimatedSprite representing the specified GameObject and rendered with the given array of Images.
     * @param object The GameObject to be represented.
     * @param images The array of Images to be rendered.
     */
    public AnimatedSprite(GameObject object, Image[] images) {
        super(object);
        this.images = images;
        frameIndex = 0;
    }

    /**
     *  Renders this Sprite and all of its children.
     * @param gc The GraphicsContext to be renders to.
     * @param affine The affine to apply to all renders.
     */
    @Override
    public void renderOriginal(GraphicsContext gc, Affine affine) {
        Point2D pos = getCenter();
        Affine af = affine.clone();
        af.appendTranslation(pos.getX(), pos.getY());
        af.appendRotation(getAngle());
        gc.setTransform(af);
        gc.drawImage(images[frameIndex], -1 * getWidth()/2,-1 * getHeight()/2, getWidth(), getHeight());
        frameIndex = (frameIndex + 1) % images.length;
        renderChildren(gc, af);
    }
}
