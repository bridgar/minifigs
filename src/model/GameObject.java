package model;

import javafx.geometry.Point2D;

public abstract class GameObject {
    private Point2D center;             // Point2D of center of object
    private double width;
    private double height;     // Width and height of object before rotation
    private double angle;             // Rotation in radians

    GameObject() { center = new Point2D(0, 0); }

    public void setCenter(Point2D center) { this.center = center; }
    public Point2D getCenter() { return center; }
    public void setWidth(double width) {this.width = width; }
    public double getWidth() { return width; }
    public void setHeight(double height) {this.height = height; }
    public double getHeight() { return height; }
    public void setAngle(double angle) { this.angle = angle; }
    public double getAngle() { return angle; }



}
