package control;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.*;
import model.Character;
import view.*;

import java.util.ArrayList;

/**
 *  GameController is the bulk of the Controller portion of the game.
 */
public class GameController implements FrameListener{
    private static final ArrayList<Army> armies = new ArrayList<>();               //TODO switch over to keeping track of characters this way
    private static final ArrayList<Squad> squads = new ArrayList<>();
    private static final ArrayList<Scenery> scenery = new ArrayList<>();           //TODO should have better data structure
    private static ListView<Character> currentCharacters;
    private static final ArrayList<GameAction> activeActions = new ArrayList<>();;

    private static Point2D clickCoords;

    private static final double X_MOVE = 5.0;
    private static final double Y_MOVE = 5.0;

    private static GameController gameController = new GameController();

    private GameController() {
        AnimationController.registerListener(this);
        initializeUnits();
        initializeScenery();

    }

    public static void setGraphicsContext(GraphicsContext gc) {
        gc.getCanvas().addEventHandler(MouseEvent.ANY, new CanvasMouseHandler(gameController));
        gc.getCanvas().addEventHandler(ScrollEvent.ANY, new CanvasScrollHandler(gameController));
        gc.getCanvas().addEventHandler(KeyEvent.ANY, new CanvasKeyHandler(gameController));
    }

    public static void setCurrentCharacters(ListView<Character> currentCharacters) {
        GameController.currentCharacters = currentCharacters;
        for(Squad s : squads) {
            for (Character c : s.getCharacters()) {
                currentCharacters.getItems().add(c);
            }
        }
    }

    /**
     *
     */
    private static void clearSelectedCharacters() {
        currentCharacters.getSelectionModel().clearSelection();
    }

    /**
     *
     * @param s
     */
    public static void selectCharacter(Sprite s) {
        GameObject go = s.getGameObject();
        if(go instanceof Character) {
            if (!currentCharacters.getSelectionModel().getSelectedItems().contains(go)) {
                currentCharacters.getSelectionModel().select((Character) go);
            }
        } else clearSelectedCharacters(); //TODO maybe handle selecting Scenery?
    }

    public static void click(Point2D clickVec) {
        clickCoords = clickVec;
        Sprite s = AnimationController.spriteAt(clickVec);
        if(s != null) selectCharacter(s);
        else clearSelectedCharacters();
    }

    /**
     *
     * @param dragVec
     */
    public static void drag(Point2D dragVec) {
        ObservableList<Character> selected = currentCharacters.getSelectionModel().getSelectedItems();
        if(!selected.isEmpty()) {
            for (Character u : selected) {
                Point2D movement = dragVec.subtract(clickCoords).multiply(1.0 / AnimationController.getScale());
                u.setPhantomCenter(u.getCenter().add(movement));
            }
        } else {
            AnimationController.moveCamera(dragVec.subtract(clickCoords));
        }
    }

    /**
     *
     */
    public static void finishMove() {
        ObservableList<Character> selected = currentCharacters.getSelectionModel().getSelectedItems();
        if(!selected.isEmpty()) {
            for (Character c : selected) {
                if (!c.finishMove())
                    System.out.println("Invalid move");
            }
            clearSelectedCharacters();
        } else {
            AnimationController.finishMoveCamera();
        }
    }

    public static void scrollMouse(double deltaY) {
        AnimationController.scaleCamera(deltaY);
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
    private static void performActions(double dt) {
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
    public static void addAction(GameAction action) {
        if(!activeActions.contains(action))
            activeActions.add(action);
    }

    /**
     *
     * @param action
     */
    public static void removeAction(GameAction action) {
        activeActions.remove(action);
    }

    /**
     *
     */
    private static void initializeUnits() {

        Squad s = SquadFactory.getNewSquad("Space Marines", "Tactical Squad");
        squads.add(s);

        for(Character c : s.getCharacters())
            AnimationController.addCharacterSprite(SpriteFactory.getNewCharacter(c));

    }

    /**
     *
     */
    private static void initializeScenery() {
        Scenery space = new Scenery();
        space.setCenter(new Point2D(256, 256));
        space.setWidth(2048);
        space.setHeight(2048);

        scenery.add(space);
        Sprite spaceSprite = new ImageSprite(space, new Image("media/space.png"));
        AnimationController.addScenerySprite(spaceSprite);
    }

    /**
     *
     * @param dt
     */
    private static void moveSelectedLeft(double dt) {
        for(Character selected : currentCharacters.getSelectionModel().getSelectedItems()) {
            selected.setCenter(selected.getCenter().add(-1 * dt * X_MOVE, 0));
        }
    }

    /**
     *
     * @param dt
     */
    private static void moveSelectedRight(double dt) {
        for(Character selected : currentCharacters.getSelectionModel().getSelectedItems()) {
            selected.setCenter(selected.getCenter().add(dt * X_MOVE, 0));
        }
    }

    /**
     *
     * @param dt
     */
    private static void moveSelectedUp(double dt) {
        for(Character selected : currentCharacters.getSelectionModel().getSelectedItems()) {
            selected.setCenter(selected.getCenter().add(0, -1 * dt * Y_MOVE));
        }
    }

    /**
     *
     * @param dt
     */
    private static void moveSelectedDown(double dt) {
        for(Character selected : currentCharacters.getSelectionModel().getSelectedItems()) {
            selected.setCenter(selected.getCenter().add(0, dt * Y_MOVE));
        }
    }

    /**
     *
     */
    public enum GameAction{ MOVELEFT, MOVERIGHT, MOVEUP, MOVEDOWN}
}
