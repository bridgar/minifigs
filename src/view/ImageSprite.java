package view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import model.GameObject;

/**
 * An ImageSprite is a Sprite that can be rendered with an Image.
 * It contains an Image.
 */
public class ImageSprite extends Sprite {

    private Image image;

    /**
     *  An ImageSprite representing the provided GameObject with the provided Image.
     * @param object The GameObject to be represented.
     * @param image The Image to renderOriginal.
     */
    public ImageSprite(GameObject object, Image image) {
        super(object);
        this.image = image;
    }

    /**
     *  Renders the image with the provided Affine applied.
     * @param gc The GraphicsContext to be renders to.
     * @param affine The affine to apply to all renders.
     */
    @Override
    public void renderOriginal(GraphicsContext gc, Affine affine){
        Point2D center = object.getCenter();
        if(center == null)
            System.out.println("Oops");
        Affine af = affine.clone();
        // Apply the translations and rotations to the affine of the world
        af.appendTranslation(center.getX(), center.getY());
        af.appendRotation(getAngle());

        gc.setTransform(af);
        gc.drawImage(image, -1 * getWidth() / 2, -1 * getHeight() / 2, getWidth(), getHeight());
        renderChildren(gc, af);
    }
}
