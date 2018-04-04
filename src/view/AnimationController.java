package view;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

import javafx.geometry.Point2D;
import java.util.ArrayList;

public class AnimationController extends AnimationTimer {

    private long lastNanoTime;
    private GraphicsContext gc;
    private ArrayList<Sprite> unitSprites;    //TODO should have better data structure
    private ArrayList<Sprite> scenerySprites; //TODO should have better data structure
    private double zoomLevel;
    private Point2D cameraCenter;

    public AnimationController(long firstNanoTime, GraphicsContext gc) {
        lastNanoTime = firstNanoTime;
        this.gc = gc;
        unitSprites = new ArrayList<Sprite>();
        scenerySprites = new ArrayList<Sprite>();

        zoomLevel = 1;
        cameraCenter = new Point2D(0, 0);


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

    public Sprite spriteAt(double canvasX, double canvasY) { //TODO better algorithm to go along with better data structure
        for (Sprite s : unitSprites) {
            if(s.contains(new Affine(), canvasX, canvasY)) { //TODO change this affine to the camera affine
                return s;
            }
        }

        for (Sprite s : scenerySprites) {
            if(s.contains(new Affine(), canvasX, canvasY)) { //TODO change this affine to the camera affine
                return s;
            }
        }

        return null;
    }
}
