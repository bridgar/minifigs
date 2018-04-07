package view;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.transform.Affine;
import model.GameObject;

import java.util.ArrayList;

/**
 *  Sprite is the highest level View class. It represents any sprite to be rendered in game.
 *  It contains a reference to a GameObject and a list of child sprites.
 *  Child sprites will be rendered relative to their parents and their GameObjects should have relative coordinates.
 */
public abstract class Sprite {
    final GameObject object;
    final ArrayList<Sprite> children = new ArrayList<Sprite>();
    Shape shape;

    private static final RadialGradient GRAD = new RadialGradient(0,0,0.5,0.5,
            0.5,true, CycleMethod.NO_CYCLE,
            new Stop(0.1f, Color.rgb(255, 255, 102, 1)),
            new Stop(1.0f, Color.rgb(255,255,102, .2)));

    //TODO scaling

    /**
     *  An invisible Sprite.
     * @param object The GameObject that this Sprite belongs to.
     */
    Sprite(GameObject object) {
        this.object = object;
    }

    /**
     * @return The GameObject that this Sprite belongs to.
     */
    public GameObject getGameObject() { return object; }

    /**
     * @return The center of this Sprite.
     */
    Point2D getCenter() { return object.getCenter(); }

    /**
     * @return The height of this Sprite.
     */
    double getHeight() { return object.getHeight(); }

    /**
     * @return The width of this Sprite.
     */
    double getWidth() { return object.getWidth(); }

    /**
     * @return The rotation of this Sprite in radians.
     */
    double getAngle() { return object.getAngle(); }

    /**
     *  Adds a child Sprite to this Sprite. This Sprite will renderOriginal all child Sprites when it renders.
     * @param child Child sprite to add.
     */
    public void addChild(Sprite child) {
        children.add(child);
    }

    /**
     *
     * @param gc
     * @param affine
     */
    public void render(GraphicsContext gc, Affine affine) {
        if(object.isSelected) renderSelectedGlow(gc, affine);
        renderOriginal(gc, affine);
        renderPhantom(gc, affine);
    }

    protected void renderSelectedGlow(GraphicsContext gc, Affine affine) {
        Point2D center = object.getCenter();
        Affine af = affine.clone();
        af.appendTranslation(center.getX(), center.getY());
        gc.setTransform(af);
        if(shape == Shape.CIRCLE) {
            gc.setFill(GRAD);
            gc.fillOval(getWidth() * -.55, getHeight() * -.55, getWidth()*1.1, getHeight()*1.1);

        } else if(shape == Shape.RECTANGLE) {
            gc.setFill(GRAD);
            gc.fillRect(getWidth()* -.55, getHeight() * -.55, getWidth()*1.1, getHeight()*1.1);
        }
        for(Sprite s : children)
            s.renderSelectedGlow(gc, af);
    }

    /**
     *  Render this Sprite and all child Sprites to the GraphicsContext provided. The provided Affine will be applied
     *  to all coordinates.
     * @param gc The GraphicsContext to be rendered to.
     * @param affine The affine to apply to all renders.
     */
    protected abstract void renderOriginal(GraphicsContext gc, Affine affine);

    /**
     * Renders the phantom sprite if necessary.
     * @param gc The GraphicsContext to be rendered to.
     * @param affine The affine to apply to all renders.
     */
    protected void renderPhantom(GraphicsContext gc, Affine affine) {
        if(object.getPhantomCenter() != null) {
            Affine af = affine.clone();
            Point2D relativeTranslation = object.getPhantomCenter().subtract(object.getCenter());
            af.appendTranslation(relativeTranslation.getX(), relativeTranslation.getY());
            gc.setGlobalAlpha(0.5);
            renderOriginal(gc, af);
            gc.setGlobalAlpha(1.0);
        }
    }

    /**
     *  Render the children of this Sprite.
     * @param gc The GraphicsContext to be rendered to.
     * @param affine The affine to apply to all renders. NOTE: This should be the affine of the parent.
     */
    void renderChildren(GraphicsContext gc, Affine affine) {
        for(Sprite s : children)
            s.renderOriginal(gc, affine);
    }

    /**
     *  Determines whether the provided point lies within this Sprite or any of its children.
     * @param affine The Affine to apply to the point.
     * @param x The x portion of the point to check.
     * @param y The y portion of the point to check.
     * @return Whether or not the point is contained in this Sprite or any of its children.
     */
    public boolean contains(Affine affine, double x, double y) {
        if(shape == Shape.RECTANGLE)
            return rectangleContains(affine, x, y) || childrenContains(affine, x, y);
        else if(shape == Shape.CIRCLE)
            return circleContains(affine, x, y) || childrenContains(affine, x, y);
        else return childrenContains(affine, x, y);
    }

    /**
     *  Determines whether the children of this Sprite contain the given point.
     * @param affine The Affine to apply to the point.
     * @param x The x portion of the point to check.
     * @param y The y portion of the point to check.
     * @return Whether or not the point is contained in the children of this Sprite.
     */
    boolean childrenContains(Affine affine, double x, double y) {
        for(Sprite s : children) {
            if(s.contains(affine, x, y)) return true;
        }
        return false;
    }

    /**
     *  Determines whether the provided point is in the rectangular bounding box of this Sprite.
     * @param affine The Affine to apply to the point.
     * @param x The x portion of the point to check.
     * @param y They y portion of the point to check.
     * @return Whether or not the point is contained in the rectangular bounding box of this Sprite.
     */
    boolean rectangleContains(Affine affine, double x, double y) {
        Point2D pos = getCenter();
        BoundingBox bb = new BoundingBox(pos.getX() - getWidth()/2, pos.getY() - getHeight()/2,
                getWidth(), getHeight());
        Bounds b = affine.transform(bb);
        return b.contains(x, y);
    }

    /**
     *  Determines whether the provided point is in the circular bounding box of this Sprite.
     * @param affine The Affine to apply to the point.
     * @param x The x portion of the point to check.
     * @param y The y portion of the point to check.
     * @return Whether or not the point is contained in the circular bounding box of this Sprite.
     */
    boolean circleContains(Affine affine, double x, double y) {
        return rectangleContains(affine, x, y); //TODO do real bounding circle calculations
    }

    /**
     *  The possible shapes for bounding boxes.
     */
    public enum Shape {RECTANGLE, CIRCLE }
}

