package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SimpleSprite extends Sprite {

    private Shape shape;
    private Color fillColor, strokeColor;

    public SimpleSprite(Shape shape, double width, double height) {
        this.shape = shape;
        position = new Position();
        this.width = width;
        this.height = height;
        fillColor = Color.WHITE;
        strokeColor = Color.BLACK;
    }

    public SimpleSprite(Shape shape, double width, double height, Color fillColor, Color strokeColor) {
        this.shape = shape;
        position = new Position();
        this.width = width;
        this.height = height;
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
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillRect(position.x, position.y, width, height);
        gc.strokeRect(position.x, position.y, width, height);
    }

    private void renderCircle(GraphicsContext gc) {
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillOval(position.x, position.y, width, height);
        gc.strokeOval(position.x, position.y, width, height);
    }

}
