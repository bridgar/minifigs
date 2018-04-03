package sample;

public abstract class GameObject {
    protected Position pos;
    protected double width, height;

    public GameObject() {
        pos = new Position();
    }

    public void setPosition(Position pos) { this.pos = pos; }
    public Position getPosition() { return pos; }
    public void setWidth(double width) {this.width = width; }
    public double getWidth() { return width; }
    public void setHeight(double height) {this.height = height; }
    public double getHeight() { return height; }



}
