package view;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Character;
import model.Squad;

public class UnitWindow {
    private static final Stage stage = new Stage();
    private static boolean stageHidden = true;
    private static Squad currentSquad;
    private static ListView<Character> unitCharacters;
    private static Label unitName;
    private static Text unitText;

    private static UnitWindow unitWindow = new UnitWindow();

    private UnitWindow() {
        stage.setOnCloseRequest(event -> {stageHidden = true;}); // Let me know when the view has been hidden
        stage.setTitle("Squad Viewer");
        stage.setHeight(400);
        stage.setWidth(300);
        stage.setResizable(false);
        unitText = new Text();
        unitText.setTextAlignment(TextAlignment.CENTER);
        unitText.setFont(new Font("Arial", 16));
        unitText.setWrappingWidth(200);
    }

    public void showUnit(Squad squad) {
        currentSquad = squad;
        unitName = new Label(squad.toString());
        unitName.setFont(new Font("Arial", 20));
        remakeUnitCharacters();
        for(Character c : squad.characters)
            unitCharacters.getItems().add(c);

        if(stageHidden) stage.show();
    }

    private void remakeUnitCharacters() {
        unitCharacters = new ListView<Character>();
        // Might need to do more here
    }


}
