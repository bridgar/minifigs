package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Affine;

import java.util.ArrayList;

public class AnimationController extends AnimationTimer {

    private long lastNanoTime;
    private GraphicsContext gc;
    private ArrayList<Sprite> unitSprites;    //TODO should have better data structure
    private ArrayList<Sprite> scenerySprites; //TODO should have better data structure
    private double zoomLevel;
    private Position cameraCenter;

    public AnimationController(long firstNanoTime, GraphicsContext gc) {
        lastNanoTime = firstNanoTime;
        this.gc = gc;
        unitSprites = new ArrayList<Sprite>();
        scenerySprites = new ArrayList<Sprite>();

        zoomLevel = 1;
        cameraCenter = new Position();


    }

    public void addUnitSprite(Sprite sprite) {
        unitSprites.add(sprite);
    }

    public void addScenerySprite(Sprite sprite) {
        scenerySprites.add(sprite);
    }

    @Override
    public void handle(long currentNanoTime)
    {
        double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
        lastNanoTime = currentNanoTime;

        for (Sprite s : scenerySprites) {
            s.render(gc, new Affine()); //TODO change this affine to the camera affine
        }

        for (Sprite s : unitSprites) {
            s.render(gc, new Affine()); //TODO change this affine to the camera affine
        }
    }
}
