package view;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.transform.Affine;
import model.Character;

import java.util.ArrayList;

/**
 *  AnimationController handles rendering frames.
 *  It contains a reference to the GraphicsContext to renderOriginal the frames to,
 *  a list of FrameListeners that need notification of new frames,
 *  lists of all Sprites,
 *  and the Affine for the camera.
 */
public class AnimationController extends AnimationTimer implements ChangeListener {

    private long lastNanoTime;
    private final GraphicsContext gc;
    private final ArrayList<FrameListener> listeners;
    private final ArrayList<Sprite> characterSprites;    //TODO should have better data structure
    private ListView<Character> currentCharacters;
    private ObservableList<Character> selectedCharacters;
    private final ArrayList<Sprite> scenerySprites;      //TODO should have better data structure
    private Affine cameraAffine, lastCameraAffine;
    private double currentScale;

    /**
     *  Constructs an AnimationController with the start time and the GraphicsContext to renderOriginal to.
     * @param firstNanoTime The start time.
     * @param gc The GraphicsContext to renderOriginal to.
     */
    public AnimationController(long firstNanoTime, GraphicsContext gc, ListView<Character> currentCharacters) {
        lastNanoTime = firstNanoTime;
        this.gc = gc;
        listeners = new ArrayList<FrameListener>();
        characterSprites = new ArrayList<Sprite>();
        this.currentCharacters = currentCharacters;
        currentCharacters.getSelectionModel().selectedItemProperty().addListener(this);
        selectedCharacters = currentCharacters.getSelectionModel().getSelectedItems();
        scenerySprites = new ArrayList<Sprite>();
        cameraAffine = new Affine();
        lastCameraAffine = new Affine();
        currentScale = 1.0;

    }

    /**
     *  Adds the provided Listener to the list of Listeners.
     * @param listener The Listener to be added.
     */
    public void registerListener(FrameListener listener) {
        listeners.add(listener);
    }

    /**
     *  Adds a Sprite representing a Character.
     * @param sprite The Sprite to be added.
     */
    public void addCharacterSprite(Sprite sprite) {
        characterSprites.add(sprite);
    }

    /**
     *  Adds a Sprite representing a Scenery.
     * @param sprite The Sprite to be added.
     */
    public void addScenerySprite(Sprite sprite) {
        scenerySprites.add(sprite);
    }

    /**
     *  Removes the specified Sprite representing a Character.
     * @param sprite The Sprite to be removed.
     */
    public void removeUnitSprite(Sprite sprite) { characterSprites.remove(sprite); }


    /**
     *  Removes the specified Sprite representing a Scenery.
     * @param sprite The Sprite to be removed.
     */
    public void removeScenerySprite(Sprite sprite) {scenerySprites.remove(sprite); }

    /**
     *  Finds the Sprite, if any, at the provided point on the canvas.
     * @param canvasPoint The point on the canvas.
     * @return The first Sprite found at the provided location. Will be null if none found.
     */
    public Sprite spriteAt(Point2D canvasPoint) { //TODO better algorithm to go along with better data structure
        for (Sprite s : characterSprites) {
            if(s.contains(cameraAffine, canvasPoint.getX(), canvasPoint.getY())) { //TODO change this affine to the camera affine
                return s;
            }
        }

        for (Sprite s : scenerySprites) {
            if(s.contains(cameraAffine, canvasPoint.getX(), canvasPoint.getY())) { //TODO change this affine to the camera affine
                return s;
            }
        }

        return null;
    }

    public void moveCamera(Point2D moveVector) {
        cameraAffine = lastCameraAffine.clone();
        cameraAffine.prependTranslation(moveVector.getX(),moveVector.getY());
    }

    public void finishMoveCamera() {
        lastCameraAffine = cameraAffine.clone();
    }

    public void scaleCamera(double deltaY) {
        double scale = Math.pow(1.5,deltaY/40);
        cameraAffine.appendScale(scale,scale);
        lastCameraAffine = cameraAffine.clone();
        currentScale *= scale;
    }

    public double getScale() {
        return currentScale;
    }

    /**
     *  Renders a frame. All Listeners are notified of the frame rendering and the elapsed time.
     *  All Sprites are rendered, bottom to top.
     * @param currentNanoTime The current time.
     */
    @Override
    public void handle(long currentNanoTime)
    {
        double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
        lastNanoTime = currentNanoTime;

        notifyListeners(elapsedTime);

        for (Sprite s : scenerySprites) {
            s.render(gc, cameraAffine); //TODO change this affine to the camera affine
        }

        for (Sprite s : characterSprites) {
            s.render(gc, cameraAffine, selectedCharacters.contains(s.object)); //TODO change this affine to the camera affine
        }
    }

    /**
     *  Notifies all Listeners that a frame has been rendered and the elapsed time.
     * @param dt The elapsed time.
     */
    private void notifyListeners(double dt) {
        for(FrameListener listener : listeners) {
            listener.newFrame(dt);
        }
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        selectedCharacters = currentCharacters.getSelectionModel().getSelectedItems();
    }
}
