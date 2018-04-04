package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class GameController {
    private AnimationController ac;
    private ArrayList<Unit> units;      //TODO should have better data structure
    private ArrayList<Scenery> scenery; //TODO should have better data structure
    private Unit selectedUnit;

    public GameController(GraphicsContext gc) {
        ac = new AnimationController(System.nanoTime(), gc);
        units = new ArrayList<Unit>();
        scenery = new ArrayList<Scenery>();

        gc.getCanvas().addEventHandler(MouseEvent.ANY, new CanvasMouseHandler(this));

        initializeUnits();
        initializeScenery();

        ac.start();
    }

    private void initializeUnits() {
        Unit earth = new Unit();
        Unit sun = new Unit();
        Unit circle = new Unit();
        circle.setHeight(100);
        circle.setWidth(100);

        units.add(earth);
        units.add(sun);
        units.add(circle);

        Image[] earthArr = {new Image("sample/ufo_0.png"), new Image("sample/ufo_1.png"),
                new Image("sample/ufo_2.png"), new Image("sample/ufo_3.png"),
                new Image("sample/ufo_4.png"), new Image("sample/ufo_5.png"),};
        Sprite earthSprite = new AnimatedSprite(earth, earthArr);
        Sprite sunSprite   = new Sprite(sun, new Image("sample/sun.png"));
        Sprite circleSprite = new SimpleSprite(circle, Sprite.Shape.CIRCLE);
        ac.addUnitSprite(earthSprite);
        ac.addUnitSprite(sunSprite);
        ac.addUnitSprite(circleSprite);
    }

    private void initializeScenery() {
        Scenery space = new Scenery();
        space.setPosition(new Position(256, 256));

        scenery.add(space);
        Sprite spaceSprite = new Sprite(space, new Image("sample/space.png"));
        ac.addScenerySprite(spaceSprite);
    }

    public void selectUnit(double canvasX, double canvasY) {
//        selectedUnit = ac.unitAt(canvasX, canvasY).;
    }

    public void dragSelected(double canvasX, double canvasY) {
        
    }

}
