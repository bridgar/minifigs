package view;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import model.GameObject;

public class SimpleSprite extends Sprite {

    private Shape shape;
    private Color fillColor, strokeColor;

    public SimpleSprite(GameObject object, Shape shape) {
        super(object);
        this.shape = shape;
        fillColor = Color.WHITE;
        strokeColor = Color.BLACK;
    }

    public SimpleSprite(GameObject object, Shape shape, Color fillColor, Color strokeColor) {
        super(object);
        this.shape = shape;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    @Override
    public boolean contains(Affine affine, double x, double y) {
        switch(shape) {
            case SQUARE: return squareContains(affine, x, y) || childrenContains(affine, x, y);
            case CIRCLE: return circleContains(affine, x, y) || childrenContains(affine, x, y);
            default: return false;
        }
    }

    private boolean squareContains(Affine affine, double x, double y) {
        Point2D pos = getCenter();
        BoundingBox bb = new BoundingBox(pos.getX() - getWidth()/2, pos.getY() - getHeight()/2,
                getWidth(), getHeight());
        Bounds b = affine.transform(bb);
        return b.contains(x, y);
    }

    private boolean circleContains(Affine affine, double x, double y) {
        return squareContains(affine, x, y); //TODO do real boundingcircle calculations
    }

    @Override
    public void render(GraphicsContext gc, Affine affine) {
        switch(shape) {
            case SQUARE: squareRender(gc, affine);
            case CIRCLE: circleRender(gc, affine);
            default: return;
        }
    }

    private void squareRender(GraphicsContext gc, Affine affine) {
        Point2D center = getCenter();
        Affine af = affine.clone();
        af.appendTranslation(center.getX(), center.getY());
        af.appendRotation(getAngle());
        gc.setTransform(af);

        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillRect(-1 * getWidth()/2,-1 * getHeight()/2, getWidth(), getHeight());
        gc.strokeRect(-1 * getWidth()/2,-1 * getHeight()/2, getWidth(), getHeight());

        for(Sprite child : children) {
            child.render(gc, af);
        }
    }

    private void circleRender(GraphicsContext gc, Affine affine) {
        Point2D center = getCenter();
        Affine af = affine.clone();
        af.appendTranslation(center.getX(),center.getY());
        af.appendRotation(getAngle());
        gc.setTransform(af);

        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillOval(-1 * getWidth()/2,-1 * getHeight()/2, getWidth(), getHeight());
        gc.strokeOval(-1 * getWidth()/2,-1 * getHeight()/2, getWidth(), getHeight());

        for(Sprite child : children) {
            child.render(gc, af);
        }
    }

}
