package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    protected Image image;
    protected Position position;
    protected double width, height;
    protected boolean isCollision;

    // Invisible Sprite
    public Sprite() {
        position = new Position();
        width = 0;
        height = 0;
    }

    public Sprite(Image image) {
        this.image = image;
        position = new Position();
        width = 0;
        height = 0;
    }

    public Sprite(Image image, double width, double height) {
        this.image = image;
        this.position = new Position();
        this.width = width;
        this.height = height;
    }

    public void setPosition(double x, double y) {
        position = new Position(x, y);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, position.x, position.y);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(position.x, position.y, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return "Position: " + position;
    }

    public enum Shape {
        SQUARE, CIRCLE
    }
}

