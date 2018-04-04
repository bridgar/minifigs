package control;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.GameObject;
import model.Scenery;
import model.Unit;
import view.*;

import java.util.ArrayList;

public class GameController {
    private AnimationController ac;
    private ArrayList<Unit> units;      //TODO should have better data structure
    private ArrayList<Scenery> scenery; //TODO should have better data structure
    private ArrayList<GameObject> selectedGameObjects;
    private GameObjectType selectedType;

    public GameController(GraphicsContext gc) {
        ac = new AnimationController(System.nanoTime(), gc);
        units = new ArrayList<Unit>();
        scenery = new ArrayList<Scenery>();

        gc.getCanvas().addEventHandler(MouseEvent.ANY, new CanvasMouseHandler(this));

        initializeUnits();
        initializeScenery();

        selectedType = GameObjectType.NONE;

        ac.start();
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

    public void toggleSelectGameObject(double canvasX, double canvasY) {
        Sprite s = ac.spriteAt(canvasX, canvasY);
        GameObject go = s.getGameObject();
        if(s == null) return;
        if(go instanceof Unit) toggleSelectUnit((Unit) go);
        else if(go instanceof Scenery) toggleSelectScenery((Scenery) go);

    }


    private void toggleSelectUnit(Unit unit) {
        if(selectedType == GameObjectType.NONE)
            selectedType = GameObjectType.UNIT;
        if(selectedType == GameObjectType.UNIT) {
           selectedGameObjects.add(unit);
        } else {
            selectedGameObjects.clear();
            selectedGameObjects.add(unit);
            selectedType = GameObjectType.UNIT;
        }
    }

    private void toggleSelectScenery(Scenery scenery) {
        if(selectedType == GameObjectType.NONE)
            selectedType = GameObjectType.SCENERY;
        if(selectedType == GameObjectType.SCENERY) {
            selectedGameObjects.add(scenery);
        } else {
            selectedGameObjects.clear();
            selectedGameObjects.add(scenery);
            selectedType = GameObjectType.SCENERY;
        }
    }

    public void dragSelected(double canvasX, double canvasY) {

    }

    public enum GameObjectType { NONE, UNIT, SCENERY}
}
