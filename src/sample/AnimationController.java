package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;

public class AnimationController extends AnimationTimer {

    private long lastNanoTime;
    private GraphicsContext gc;
    private Scenery space;
    private Unit earth, sun, circle;
    private Sprite spaceSprite, earthSprite, sunSprite, circleSprite;
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
        earth = new Unit();
        sun = new Unit();
        circle = new Unit();
        circle.setHeight(100);
        circle.setWidth(100);
        space = new Scenery();
        space.setPosition(new Position(256, 256));
        earthSprite = new Sprite(earth, new Image("sample/earth.png"));
        sunSprite   = new Sprite(sun, new Image("sample/sun.png"));
        spaceSprite = new Sprite(space, new Image("sample/space.png"));
        spaceSprite.addChild(sunSprite);
        sunSprite.addChild(earthSprite);
        circleSprite = new SimpleSprite(circle, Sprite.Shape.CIRCLE);
        sunSprite.addChild(circleSprite);
    }

    @Override
    public void handle(long currentNanoTime)
    {
        double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
        lastNanoTime = currentNanoTime;

        double x = 128 * Math.cos(currentNanoTime / 1000000000.0);
        double y = 128 * Math.sin(currentNanoTime / 1000000000.0);

        earth.setPosition(new Position(x, y));
        circle.setPosition(new Position(y, x));
        // background image clears canvas
        spaceSprite.render(gc, new Affine());
    }
}
