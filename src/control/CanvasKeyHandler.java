package control;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.security.Key;

public class CanvasKeyHandler implements EventHandler<KeyEvent> {
    private GameController gc;

    public CanvasKeyHandler(GameController gc) {
        this.gc = gc;
    }
    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            pressKey(event.getCode());
        } else if(event.getEventType() == KeyEvent.KEY_RELEASED) {
            releaseKey(event.getCode());
        }
    }

    private void pressKey(KeyCode code) {
        if(code == KeyCode.LEFT)
            gc.addAction(GameController.GameAction.MOVELEFT);
        else if(code == KeyCode.RIGHT)
            gc.addAction(GameController.GameAction.MOVERIGHT);
        else if(code == KeyCode.UP)
            gc.addAction(GameController.GameAction.MOVEUP);
        else if(code == KeyCode.DOWN)
            gc.addAction(GameController.GameAction.MOVEDOWN);
    }

    private void releaseKey(KeyCode code) {
        if(code == KeyCode.LEFT)
            gc.removeAction(GameController.GameAction.MOVELEFT);
        else if(code == KeyCode.RIGHT)
            gc.removeAction(GameController.GameAction.MOVERIGHT);
        else if(code == KeyCode.UP)
            gc.removeAction(GameController.GameAction.MOVEUP);
        else if(code == KeyCode.DOWN)
            gc.removeAction(GameController.GameAction.MOVEDOWN);
    }
}
