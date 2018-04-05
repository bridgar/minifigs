package view;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import model.GameObject;

import java.util.ArrayList;

public abstract class Sprite {
    final GameObject object;
    final ArrayList<Sprite> children = new ArrayList<Sprite>();
    //TODO scaling

    // Invisible sprite constructor
    Sprite(GameObject object) {
        this.object = object;
    }

    public GameObject getGameObject() { return object; }

    Point2D getCenter() { return object.getCenter(); }

    double getHeight() { return object.getHeight(); }

    double getWidth() { return object.getWidth(); }

    double getAngle() { return object.getAngle(); }

    public void addChild(Sprite child) {
        children.add(child);
    }

    public abstract void render(GraphicsContext gc, Affine affine);

    public boolean contains(Affine affine, double x, double y) {
        return false;
    }

    boolean childrenContains(Affine affine, double x, double y) {
        for(Sprite s : children) {
            if(s.contains(affine, x, y)) return true;
        }
        return false;
    }

    boolean rectangleContains(Affine affine, double x, double y) {
        Point2D pos = getCenter();
        BoundingBox bb = new BoundingBox(pos.getX() - getWidth()/2, pos.getY() - getHeight()/2,
                getWidth(), getHeight());
        Bounds b = affine.transform(bb);
        return b.contains(x, y);
    }

    boolean circleContains(Affine affine, double x, double y) {
        return rectangleContains(affine, x, y); //TODO do real bounding circle calculations
    }

    public enum Shape {RECTANGLE, CIRCLE }
}

