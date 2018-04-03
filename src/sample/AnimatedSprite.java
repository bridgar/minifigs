package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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
    public void render(GraphicsContext gc) {
        Position pos = getPosition();
        gc.drawImage(images[frameIndex], pos.x, pos.y, getWidth(), getHeight());
        frameIndex = (frameIndex + 1) % images.length;
    }
}
