package sample;

import control.GameController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Character;
import view.AnimationController;

public class Main extends Application {

    private static final BorderPane PANE = new BorderPane();
    private static final ListView<Character> currentCharacters = new ListView<Character>();


    @Override
    public void start(Stage theStage) throws Exception{

        theStage.setTitle( "Timeline Example" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );


        Canvas canvas = new Canvas( 512, 512 );
        canvas.setFocusTraversable(true);

        Pane centerPane = new Pane();

        PANE.setCenter(canvas);
        initializeRightPane();
        root.getChildren().add(PANE);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        initializeControllers(gc);
        theStage.show();

    }

    private void initializeControllers(GraphicsContext gc) {
        AnimationController.setGraphicsContext(gc);
        GameController.setGraphicsContext(gc);
        AnimationController.setCurrentCharacters(currentCharacters);
        GameController.setCurrentCharacters(currentCharacters);

        AnimationController.staticStart();
    }


    private void initializeRightPane() {
        currentCharacters.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        HBox selection = new HBox();
        PANE.setRight(selection);

        VBox units = new VBox();
        //TODO curent units
        VBox chars = new VBox();
        chars.getChildren().add(currentCharacters);

        selection.getChildren().addAll(units, chars);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
