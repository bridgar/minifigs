package control;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Character;
import model.GameObject;
import model.Scenery;
import view.*;

import java.util.ArrayList;

/**
 *  GameController is the bulk of the Controller portion of the game.
 */
public class GameController implements FrameListener{
    private final AnimationController ac;
    private final ArrayList<Character> characters;      //TODO should have better data structure
    private final ArrayList<Scenery> scenery; //TODO should have better data structure
    private final ListView<Character> currentCharacters;
    private final ArrayList<GameAction> activeActions;

    private static final double X_MOVE = 5.0;
    private static final double Y_MOVE = 5.0;

    /**
     *
     * @param gc
     */
    public GameController(GraphicsContext gc, ListView<Character> currentCharacters) {
        ac = new AnimationController(System.nanoTime(), gc, currentCharacters);
        ac.registerListener(this);
        characters = new ArrayList<Character>();
        scenery = new ArrayList<Scenery>();
        this.currentCharacters = currentCharacters;

        activeActions = new ArrayList<GameAction>();

        gc.getCanvas().addEventHandler(MouseEvent.ANY, new CanvasMouseHandler(this));
        gc.getCanvas().addEventHandler(KeyEvent.ANY, new CanvasKeyHandler(this));

        initializeUnits();
        initializeScenery();

        for(Character c : characters) {
            currentCharacters.getItems().add(c);
        }

        ac.start();
    }

    /**
     *
     */
    private void clearSelectedCharacters() {
        currentCharacters.getSelectionModel().clearSelection();
    }

    /**
     *
     * @param canvasPoint
     */
    public void selectCharacter(Point2D canvasPoint) {
        Sprite s = ac.spriteAt(canvasPoint);
        if(s == null) {
            clearSelectedCharacters();
            return;
        }
        GameObject go = s.getGameObject();
        if(go instanceof Character) {
            if (!currentCharacters.getSelectionModel().getSelectedItems().contains(go)) {
                currentCharacters.getSelectionModel().select((Character) go);
            }
        } else clearSelectedCharacters(); //TODO maybe handle selecting Scenery?
    }

    /**
     *
     * @param canvasPoint
     */
    public void toggleSelectCharacter(Point2D canvasPoint) {
        Sprite s = ac.spriteAt(canvasPoint);
        if(s == null) {
            clearSelectedCharacters();
            return;
        }
        GameObject go = s.getGameObject();

        if(go instanceof Character) {
            if (currentCharacters.getSelectionModel().getSelectedItems().contains(go)) {
                int removed = currentCharacters.getItems().indexOf(go);
                ObservableList<Integer> selected = currentCharacters.getSelectionModel().getSelectedIndices();
                currentCharacters.getSelectionModel().clearSelection();
                for(Integer i : selected)
                    if(i != removed)
                        currentCharacters.getSelectionModel().select(i);
            } else
                currentCharacters.getSelectionModel().select((Character) go);
        }
        else clearSelectedCharacters(); //TODO maybe handle selecting Scenery?

    }

    /**
     *
     * @param dragVec
     */
    public void dragSelected(Point2D dragVec) {
        for(Character u : currentCharacters.getSelectionModel().getSelectedItems()) {
            u.setPhantomCenter(u.getCenter().add(dragVec));
        }
    }

    /**
     *
     */
    public void finishMove() {
        for(Character c : currentCharacters.getSelectionModel().getSelectedItems()) {
            if(!c.finishMove())
                System.out.println("Invalid move");
        }
        clearSelectedCharacters();
    }

    /**
     *
     * @param dt
     */
    public void newFrame(double dt) { performActions(dt); } //TODO this should really not be frame dependent

    /**
     *
     * @param dt
     */
    private void performActions(double dt) {
        for(GameAction action : activeActions) {
            if(action == GameAction.MOVELEFT)
                moveSelectedLeft(dt);
            else if(action == GameAction.MOVERIGHT)
                moveSelectedRight(dt);
            else if(action == GameAction.MOVEUP)
                moveSelectedUp(dt);
            else if(action == GameAction.MOVEDOWN)
                moveSelectedDown(dt);
        }
    }

    /**
     *
     * @param action
     */
    public void addAction(GameAction action) {
        if(!activeActions.contains(action))
            activeActions.add(action);
    }

    /**
     *
     * @param action
     */
    public void removeAction(GameAction action) {
        activeActions.remove(action);
    }

    /**
     *
     */
    private void initializeUnits() {
        Character earth = new Character();
        earth.name = "Earth";
        Character sun = new Character();
        sun.name = "Sun";
        Character circle = new Character();
        circle.name = "Circle";
        earth.setHeight(100);
        earth.setWidth(100);
        earth.setCenter(new Point2D(100,100));
        sun.setHeight(200);
        sun.setWidth(200);
        sun.setCenter(new Point2D(256, 256));
        circle.setHeight(100);
        circle.setWidth(100);

        characters.add(earth);
        characters.add(sun);
        characters.add(circle);

        Image[] earthArr = {new Image("media/ufo_0.png"), new Image("media/ufo_1.png"),
                new Image("media/ufo_2.png"), new Image("media/ufo_3.png"),
                new Image("media/ufo_4.png"), new Image("media/ufo_5.png"),};
        Sprite earthSprite = new AnimatedSprite(earth, earthArr);
        Sprite sunSprite   = new ImageSprite(sun, new Image("media/sun.png"));
        Sprite circleSprite = new SimpleSprite(circle, Sprite.Shape.CIRCLE);
        ac.addCharacterSprite(sunSprite);
        ac.addCharacterSprite(earthSprite);
        ac.addCharacterSprite(circleSprite);
    }

    /**
     *
     */
    private void initializeScenery() {
        Scenery space = new Scenery();
        space.setCenter(new Point2D(256, 256));
        space.setWidth(512);
        space.setHeight(512);

        scenery.add(space);
        Sprite spaceSprite = new ImageSprite(space, new Image("media/space.png"));
        ac.addScenerySprite(spaceSprite);
    }

    /**
     *
     * @param dt
     */
    private void moveSelectedLeft(double dt) {
        for(Character selected : currentCharacters.getSelectionModel().getSelectedItems()) {
            selected.setCenter(selected.getCenter().add(-1 * dt * X_MOVE, 0));
        }
    }

    /**
     *
     * @param dt
     */
    private void moveSelectedRight(double dt) {
        for(Character selected : currentCharacters.getSelectionModel().getSelectedItems()) {
            selected.setCenter(selected.getCenter().add(dt * X_MOVE, 0));
        }
    }

    /**
     *
     * @param dt
     */
    private void moveSelectedUp(double dt) {
        for(Character selected : currentCharacters.getSelectionModel().getSelectedItems()) {
            selected.setCenter(selected.getCenter().add(0, -1 * dt * Y_MOVE));
        }
    }

    /**
     *
     * @param dt
     */
    private void moveSelectedDown(double dt) {
        for(Character selected : currentCharacters.getSelectionModel().getSelectedItems()) {
            selected.setCenter(selected.getCenter().add(0, dt * Y_MOVE));
        }
    }

    /**
     *
     */
    public enum GameAction{ MOVELEFT, MOVERIGHT, MOVEUP, MOVEDOWN}
}
