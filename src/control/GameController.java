package control;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.GameObject;
import model.Scenery;
import model.Unit;
import view.*;

import java.util.ArrayList;


public class GameController implements FrameListener{
    private final AnimationController ac;
    private final ArrayList<Unit> units;      //TODO should have better data structure
    private final ArrayList<Scenery> scenery; //TODO should have better data structure
    private final ArrayList<Unit> selectedUnits;
    private final ArrayList<GameAction> activeActions;

    private static final double X_MOVE = 5.0;
    private static final double Y_MOVE = 5.0;

    public GameController(GraphicsContext gc) {
        ac = new AnimationController(System.nanoTime(), gc);
        ac.registerListener(this);
        units = new ArrayList<Unit>();
        scenery = new ArrayList<Scenery>();
        selectedUnits = new ArrayList<Unit>();
        activeActions = new ArrayList<GameAction>();

        gc.getCanvas().addEventHandler(MouseEvent.ANY, new CanvasMouseHandler(this));
        gc.getCanvas().addEventHandler(KeyEvent.ANY, new CanvasKeyHandler(this));

        initializeUnits();
        initializeScenery();


        ac.start();
    }

    public void toggleSelectGameObject(double canvasX, double canvasY) {
        Sprite s = ac.spriteAt(canvasX, canvasY);
        if(s == null) {
            selectedUnits.clear();
            return;
        }
        GameObject go = s.getGameObject();

        if(go instanceof Unit) toggleSelectUnit((Unit) go);
        else selectedUnits.clear();

    }

    public void dragSelected(double canvasX, double canvasY) {

    }

    public void newFrame(double dt) { performActions(dt); } //TODO this should really not be frame dependent

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

    public void addAction(GameAction action) {
        if(!activeActions.contains(action))
            activeActions.add(action);
    }

    public void removeAction(GameAction action) {
        activeActions.remove(action);
    }

    private void initializeUnits() {
        Unit earth = new Unit();
        Unit sun = new Unit();
        Unit circle = new Unit();
        earth.setHeight(100);
        earth.setWidth(100);
        sun.setHeight(200);
        sun.setWidth(200);
        sun.setCenter(new Point2D(256, 256));
        circle.setHeight(100);
        circle.setWidth(100);

        units.add(earth);
        units.add(sun);
        units.add(circle);

        Image[] earthArr = {new Image("media/ufo_0.png"), new Image("media/ufo_1.png"),
                new Image("media/ufo_2.png"), new Image("media/ufo_3.png"),
                new Image("media/ufo_4.png"), new Image("media/ufo_5.png"),};
        Sprite earthSprite = new AnimatedSprite(earth, earthArr);
        Sprite sunSprite   = new ImageSprite(sun, new Image("media/sun.png"));
        Sprite circleSprite = new SimpleSprite(circle, Sprite.Shape.CIRCLE);
        ac.addUnitSprite(sunSprite);
        ac.addUnitSprite(earthSprite);
        ac.addUnitSprite(circleSprite);
    }

    private void initializeScenery() {
        Scenery space = new Scenery();
        space.setCenter(new Point2D(256, 256));
        space.setWidth(512);
        space.setHeight(512);

        scenery.add(space);
        Sprite spaceSprite = new ImageSprite(space, new Image("media/space.png"));
        ac.addScenerySprite(spaceSprite);
    }

    private void toggleSelectUnit(Unit unit) {
        if(selectedUnits.contains(unit)) selectedUnits.remove(unit);
        else selectedUnits.add(unit);
    }

    private void moveSelectedLeft(double dt) {
        for(Unit selected : selectedUnits) {
            selected.setCenter(selected.getCenter().add(-1 * dt * X_MOVE, 0));
        }
    }

    private void moveSelectedRight(double dt) {
        for(Unit selected : selectedUnits) {
            selected.setCenter(selected.getCenter().add(dt * X_MOVE, 0));
        }
    }

    private void moveSelectedUp(double dt) {
        for(Unit selected : selectedUnits) {
            selected.setCenter(selected.getCenter().add(0, -1 * dt * Y_MOVE));
        }
    }

    private void moveSelectedDown(double dt) {
        for(Unit selected : selectedUnits) {
            selected.setCenter(selected.getCenter().add(0, dt * Y_MOVE));
        }
    }

    public enum GameAction{ MOVELEFT, MOVERIGHT, MOVEUP, MOVEDOWN}
}
