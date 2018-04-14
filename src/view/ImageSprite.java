package view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import model.GameObject;

import static control.GameController.INCH_TO_PIXEL;

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
        af.appendTranslation(center.getX() * INCH_TO_PIXEL, center.getY() * INCH_TO_PIXEL);
        af.appendRotation(getAngle());

        gc.setTransform(af);
        double w = getWidth() * INCH_TO_PIXEL;
        double h = getHeight() * INCH_TO_PIXEL;
        gc.drawImage(image, -1 * w / 2, -1 * h / 2, w, h);
        renderChildren(gc, af);
    }
}
