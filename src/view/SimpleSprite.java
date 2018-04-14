package view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import model.GameObject;

/**
 *  A SimpleSprite is a sprite that can be rendered with a simple shape.
 *  It contains a fillColor and strokeColor for determining how to renderOriginal the shape.
 */
public class SimpleSprite extends Sprite {
    private Color fillColor;
    private Color strokeColor;

    /**
     *  A SimpleSprite of the specified shape with white fill and black stroke.
     * @param object The GameObject that this Sprite represents.
     * @param shape The Shape of this Sprite.
     */
    public SimpleSprite(GameObject object, Shape shape) {
        super(object);
        this.shape = shape;
        fillColor = Color.WHITE;
        strokeColor = Color.BLACK;
    }

    /**
     *  A SimpleSprite with the specified shape, fill color, and stroke color.
     * @param object The GameObject that this Sprite represents.
     * @param shape The Shape of this Sprite.
     * @param fillColor The fillColor of this Sprite.
     * @param strokeColor The strokeColor of this Sprite.
     */
    public SimpleSprite(GameObject object, Shape shape, Color fillColor, Color strokeColor) {
        super(object);
        this.shape = shape;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    /**
     *  Renders this Sprite and all of its children.
     * @param gc The GraphicsContext to be renders to.
     * @param affine The affine to apply to all renders.
     */
    @Override
    public void renderOriginal(GraphicsContext gc, Affine affine) {
        if(shape == Shape.RECTANGLE)
            rectangleRender(gc, affine);
        else if(shape == Shape.CIRCLE)
            circleRender(gc, affine);
        else {
        }
    }

    /**
     *  Renders a rectangle of the dimensions of the GameObject that this Sprite represents.
     * @param gc The GraphicsContext to renderOriginal to.
     * @param affine The Affine to apply to the renderOriginal.
     */
    private void rectangleRender(GraphicsContext gc, Affine affine) {
        Point2D center = getCenter();
        Affine af = affine.clone();
        af.appendTranslation(center.getX(), center.getY());
        af.appendRotation(getAngle());
        gc.setTransform(af);

        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.setLineWidth(0.01);
        gc.fillRect(-1 * getWidth()/2,-1 * getHeight()/2, getWidth(), getHeight());
        gc.strokeRect(-1 * getWidth()/2,-1 * getHeight()/2, getWidth(), getHeight());

        renderChildren(gc, af);
    }

    /**
     *  Renders a circle of the dimensions of the GameObject that this Sprite represents.
     * @param gc The GraphicsContext to renderOriginal to.
     * @param affine The Affine to apply to the renderOriginal.
     */
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

        renderChildren(gc, af);
    }

}
