package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;

public class AnimatedSprite extends Sprite{
    private Image[] images;
    private int frameIndex;

    public AnimatedSprite(GameObject object, Image[] images) {
        super(object);
        this.images = images;
        frameIndex = 0;
        object.setHeight(images[0].getHeight()); // Setting sizes to be those of first image
        object.setWidth(images[0].getWidth());   // This is probably not sustainable
    }

    @Override
    public void render(GraphicsContext gc, Affine affine) {
        Position pos = getPosition();
        Affine af = affine.clone();
        af.appendTranslation(pos.x, pos.y);
        af.appendRotation(getAngle());
        gc.transform(af);
        gc.drawImage(images[frameIndex], -1 * getWidth()/2,-1 * getHeight()/2, getWidth(), getHeight());
        frameIndex = (frameIndex + 1) % images.length;

        for(Sprite child : children) {
            child.render(gc, af);
        }
    }
}
