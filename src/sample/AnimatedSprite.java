package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AnimatedSprite extends Sprite{
    private Image[] images;
    private int frameIndex;

    public AnimatedSprite(Image[] images) {
        this.images = images;
        position = new Position();
        width = 0;
        height = 0;
        frameIndex = 0;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(images[frameIndex], position.x, position.y);
        frameIndex = (frameIndex + 1) % images.length;
    }
}
