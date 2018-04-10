package model;

import javafx.geometry.Point2D;

/**
 *  GameObject is the highest level Model class. It represents any object in the game.
 *  It contains a center point, width, height, and angle of rotation to describe its location in space.
 */
public abstract class GameObject { //TODO switch center and angle to a single Affine.
    private Point2D center;             // Point2D of center of object
    private Point2D phantomCenter;      // Point2D of center of phantom moved object
    private double angle;               // Rotation in radians
    private double width;               // Width of object before rotation
    private double height;              // Height of object before rotation
    public String name;


    GameObject() { center = new Point2D(0, 0); }

    /**
     *  Moves the center of the object.
     * @param center The new center point of the object.
     */
    public void setCenter(Point2D center) { this.center = center; }

    /**
     * @return The center point of the object.
     */
    public Point2D getCenter() { return center; }

    /**
     *
     * @param phantomCenter
     */
    public void setPhantomCenter(Point2D phantomCenter) { this.phantomCenter = phantomCenter; }

    /**
     * @return
     */
    public Point2D getPhantomCenter() { return phantomCenter; }

    /**
     *  Changes the width of the object.
     *  Will scale rendering of the corresponding sprite.
     * @param width The new width of the object.
     */
    public void setWidth(double width) {this.width = width; }

    /**
     * @return The width of the object.
     */
    public double getWidth() { return width; }

    /**
     *  Changes the height of the object.
     *  Will scale rendering of the corresponding sprite.
     * @param height The new height of the object.
     */
    public void setHeight(double height) {this.height = height; }

    /**
     * @return The height of the object.
     */
    public double getHeight() { return height; }

    /**
     *  Changes the rotation of the object.
     *  Will rotate the rendering of the corresponding sprite.
     * @param angle The new rotation of the object in radians.
     */
    public void setAngle(double angle) { this.angle = angle; }

    /**
     * @return The rotation of the object in radians.
     */
    public double getAngle() { return angle; }


    public boolean finishMove() {
        if(true) { //TODO check for valid moves.
            center = phantomCenter;
            phantomCenter = null;
            return true;
        }
        phantomCenter = null;
        return false;
    }

    public String toString() {
        return name;
    }


}
