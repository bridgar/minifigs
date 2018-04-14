package view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import model.GameObject;

import static control.GameController.INCH_TO_PIXEL;

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
        af.appendTranslation(pos.getX() * INCH_TO_PIXEL, pos.getY() * INCH_TO_PIXEL);
        af.appendRotation(getAngle());
        gc.setTransform(af);
        double w = getWidth() * INCH_TO_PIXEL;
        double h = getHeight() * INCH_TO_PIXEL;
        gc.drawImage(images[frameIndex], -1 * w/2,-1 * h/2, w, h);
        frameIndex = (frameIndex + 1) % images.length;
        renderChildren(gc, af);
    }
}
