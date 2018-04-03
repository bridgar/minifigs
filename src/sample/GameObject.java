package sample;

public abstract class GameObject {
    protected Position pos;             // Position of center of object
    protected double width, height;     // Width and height of object before rotation
    protected double angle;             // Rotation in radians

    public GameObject() {
        pos = new Position();
    }

    public void setPosition(Position pos) { this.pos = pos; }
    public Position getPosition() { return pos; }
    public void setWidth(double width) {this.width = width; }
    public double getWidth() { return width; }
    public void setHeight(double height) {this.height = height; }
    public double getHeight() { return height; }
    public void setAngle(double angle) { this.angle = angle; }
    public double getAngle() { return angle; }



}
