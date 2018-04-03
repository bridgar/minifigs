package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AnimationController extends AnimationTimer {

    private long lastNanoTime;
    private GraphicsContext gc;
    private Sprite space, earth, sun, circle;
    private double zoomLevel;
    private Position cameraCenter;

    public AnimationController(long firstNanoTime, GraphicsContext gc) {
        lastNanoTime = firstNanoTime;
        this.gc = gc;

        zoomLevel = 1;
        cameraCenter = new Position();

        Image[] earthArr = {new Image("sample/ufo_0.png"), new Image("sample/ufo_1.png"),
                new Image("sample/ufo_2.png"), new Image("sample/ufo_3.png"),
                new Image("sample/ufo_4.png"), new Image("sample/ufo_5.png"),};
        earth = new AnimatedSprite(earthArr);
        sun   = new Sprite(new Image("sample/sun.png"));
        sun.setPosition(196, 196);
        space = new Sprite(new Image("sample/space.png"));
        circle = new SimpleSprite(Sprite.Shape.CIRCLE, 100.0, 100.0);
    }

    @Override
    public void handle(long currentNanoTime)
    {
        double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
        lastNanoTime = currentNanoTime;

        double x = 232 + 128 * Math.cos(currentNanoTime / 1000000000.0);
        double y = 232 + 128 * Math.sin(currentNanoTime / 1000000000.0);

        earth.setPosition(x, y);
        circle.setPosition(y, x);
        // background image clears canvas
        space.render(gc);
        earth.render(gc);
        sun.render(gc);
        circle.render(gc);
    }
}
