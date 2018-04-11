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
    private final AnimationController ac;
    private final ArrayList<Army> armies;               //TODO switch over to keeping track of characters this way
    private final ArrayList<Character> characters;      //TODO should have better data structure
    private final ArrayList<Scenery> scenery;           //TODO should have better data structure
    private final ListView<Character> currentCharacters;
    private final ArrayList<GameAction> activeActions;

    private Point2D clickCoords;

    private final CharacterFactory characterFactory = new CharacterFactory("data/Characters.csv");

    private static final double X_MOVE = 5.0;
    private static final double Y_MOVE = 5.0;

    /**
     *
     * @param gc
     */
    public GameController(GraphicsContext gc, ListView<Character> currentCharacters) {
        ac = new AnimationController(System.nanoTime(), gc, currentCharacters);
        ac.registerListener(this);
        armies = new ArrayList<Army>();
        characters = new ArrayList<Character>();
        scenery = new ArrayList<Scenery>();
        this.currentCharacters = currentCharacters;

        activeActions = new ArrayList<GameAction>();

        gc.getCanvas().addEventHandler(MouseEvent.ANY, new CanvasMouseHandler(this));
        gc.getCanvas().addEventHandler(ScrollEvent.ANY, new CanvasScrollHandler(this));
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
     * @param s
     */
    public void selectCharacter(Sprite s) {
        GameObject go = s.getGameObject();
        if(go instanceof Character) {
            if (!currentCharacters.getSelectionModel().getSelectedItems().contains(go)) {
                currentCharacters.getSelectionModel().select((Character) go);
            }
        } else clearSelectedCharacters(); //TODO maybe handle selecting Scenery?
    }

    public void click(Point2D clickVec) {
        clickCoords = clickVec;
        Sprite s = ac.spriteAt(clickVec);
        if(s != null) selectCharacter(s);
        else clearSelectedCharacters();
    }

    /**
     *
     * @param dragVec
     */
    public void drag(Point2D dragVec) {
        ObservableList<Character> selected = currentCharacters.getSelectionModel().getSelectedItems();
        if(!selected.isEmpty()) {
            for (Character u : selected) {
                Point2D movement = dragVec.subtract(clickCoords).multiply(1.0 / ac.getScale());
                u.setPhantomCenter(u.getCenter().add(movement));
            }
        } else {
            ac.moveCamera(dragVec.subtract(clickCoords));
        }
    }

    /**
     *
     */
    public void finishMove() {
        ObservableList<Character> selected = currentCharacters.getSelectionModel().getSelectedItems();
        if(!selected.isEmpty()) {
            for (Character c : selected) {
                if (!c.finishMove())
                    System.out.println("Invalid move");
            }
            clearSelectedCharacters();
        } else {
            ac.finishMoveCamera();
        }
    }

    public void scrollMouse(double deltaY) {
        ac.scaleCamera(deltaY);
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

        Character earth = characterFactory.getNewCharacter("Space Marines", "Troops", "Initiate");
        earth.name = "Earth";
        Character sun = characterFactory.getNewCharacter("Space Marines", "Troops", "Initiate");
        sun.name = "Sun";
        Character circle = characterFactory.getNewCharacter("Space Marines", "Troops", "Initiate");
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
        space.setWidth(2048);
        space.setHeight(2048);

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
