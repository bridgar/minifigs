package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
    public void render(GraphicsContext gc) {
        switch(shape) {
            case SQUARE: renderSquare(gc);
            case CIRCLE: renderCircle(gc);
            default:
        }
    }

    private void renderSquare(GraphicsContext gc) {
        Position pos = getPosition();
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillRect(pos.x, pos.y, getWidth(), getHeight());
        gc.strokeRect(pos.x, pos.y, getWidth(), getHeight());
    }

    private void renderCircle(GraphicsContext gc) {
        Position pos = getPosition();
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillOval(pos.x, pos.y, getWidth(), getHeight());
        gc.strokeOval(pos.x, pos.y, getWidth(), getHeight());
    }

}
