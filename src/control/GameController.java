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

    public static final double INCH_TO_PIXEL = 50;
    public static final double PIXEL_TO_INCH = 1/INCH_TO_PIXEL;

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
                movement = movement.multiply(PIXEL_TO_INCH);
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
        s.initializePositions(new Point2D(1,1));

        s = SquadFactory.getNewSquad("Space Marines", "Scout Squad");
        squads.add(s);
        s.initializePositions(new Point2D(1,2));

        for(Squad squad : squads) {
            for (Character c : squad.getCharacters())
                AnimationController.addCharacterSprite(SpriteFactory.getNewCharacter(c));
            squad.newTurn(); //TODO move this somewhere else
        }

        Squad s1 = squads.get(0);
        Squad s2 = squads.get(1);
        s1.fireWeapon(s1.getCharacters(), WeaponFactory.getWeapon("Space Marines", "Bolt Pistol"), s2);
    }

    /**
     *
     */
    private static void initializeScenery() {
        Scenery space = new Scenery();
        space.setWidth(100);
        space.setHeight(100);

        scenery.add(space);
        Sprite spaceSprite = new ImageSprite(space, new Image("media/space.png"));
        AnimationController.addScenerySprite(spaceSprite);
    }

    /**
     *
     */
    public enum GameAction{ MOVELEFT, MOVERIGHT, MOVEUP, MOVEDOWN}
}
