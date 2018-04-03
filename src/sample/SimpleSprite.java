package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

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
    public void render(GraphicsContext gc, Affine affine) {
        switch(shape) {
            case SQUARE: renderSquare(gc, affine);
            case CIRCLE: renderCircle(gc, affine);
            default:
        }
    }

    private void renderSquare(GraphicsContext gc, Affine affine) {
        Position pos = getPosition();
        Affine af = affine.clone();
        af.appendTranslation(pos.x, pos.y);
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

    private void renderCircle(GraphicsContext gc, Affine affine) {
        Position pos = getPosition();
        Affine af = affine.clone();
        af.appendTranslation(pos.x,pos.y);
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
