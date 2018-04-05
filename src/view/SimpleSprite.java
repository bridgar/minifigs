package view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import model.GameObject;

public class SimpleSprite extends Sprite {

    private Shape shape;
    private final Color fillColor;
    private Color strokeColor;

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
        if(shape == Shape.RECTANGLE)
            return rectangleContains(affine, x, y) || childrenContains(affine, x, y);
        else if(shape == Shape.CIRCLE)
            return circleContains(affine, x, y) || childrenContains(affine, x, y);
        else return false;
    }

    @Override
    public void render(GraphicsContext gc, Affine affine) {
        if(shape == Shape.RECTANGLE)
            rectangleRender(gc, affine);
        else if(shape == Shape.CIRCLE)
            circleRender(gc, affine);
        else {
        }
    }

    private void rectangleRender(GraphicsContext gc, Affine affine) {
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
