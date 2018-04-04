package view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import model.GameObject;

import java.util.ArrayList;

public abstract class Sprite {
    protected GameObject object;
    protected ArrayList<Sprite> children = new ArrayList<Sprite>();
    //TODO scaling

    // Invisible sprite constructor
    public Sprite(GameObject object) {
        this.object = object;
    }

    public GameObject getGameObject() { return object; }

    public Point2D getCenter() { return object.getCenter(); }

    public double getHeight() { return object.getHeight(); }

    public double getWidth() { return object.getWidth(); }

    public double getAngle() { return object.getAngle(); }

    public void addChild(Sprite child) {
        children.add(child);
    }

    public abstract void render(GraphicsContext gc, Affine affine);

    public boolean contains(Affine affine, double x, double y) {
        return false;
    }

    protected boolean childrenContains(Affine affine, double x, double y) {
        for(Sprite s : children) {
            if(s.contains(affine, x, y)) return true;
        }
        return false;
    }

    public enum Shape { SQUARE, CIRCLE }
}

