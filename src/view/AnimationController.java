package view;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

import java.util.ArrayList;

public class AnimationController extends AnimationTimer {

    private long lastNanoTime;
    private GraphicsContext gc;
    private ArrayList<FrameListener> listeners;
    private ArrayList<Sprite> unitSprites;    //TODO should have better data structure
    private ArrayList<Sprite> scenerySprites; //TODO should have better data structure
    private double zoomLevel;
    private Affine cameraAffine;

    public AnimationController(long firstNanoTime, GraphicsContext gc) {
        lastNanoTime = firstNanoTime;
        this.gc = gc;
        listeners = new ArrayList<FrameListener>();
        unitSprites = new ArrayList<Sprite>();
        scenerySprites = new ArrayList<Sprite>();

        zoomLevel = 1;
        cameraAffine = new Affine();


    }

    public void registerListener(FrameListener listener) {
        listeners.add(listener);
    }

    public void addUnitSprite(Sprite sprite) {
        unitSprites.add(sprite);
    }

    public void addScenerySprite(Sprite sprite) {
        scenerySprites.add(sprite);
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

    @Override
    public void handle(long currentNanoTime)
    {
        double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
        lastNanoTime = currentNanoTime;

        notifyListeners(elapsedTime);

        for (Sprite s : scenerySprites) {
            s.render(gc, new Affine()); //TODO change this affine to the camera affine
        }

        for (Sprite s : unitSprites) {
            s.render(gc, new Affine()); //TODO change this affine to the camera affine
        }
    }

    private void notifyListeners(double dt) {
        for(FrameListener listener : listeners) {
            listener.newFrame(dt);
        }
    }
}
