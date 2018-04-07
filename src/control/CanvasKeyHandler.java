package control;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *  CanvasKeyHandler accepts all KeyEvents for the Canvas. Contains reference to GameController to notify it of changes.
 */
class CanvasKeyHandler implements EventHandler<KeyEvent> {
    private final GameController gc;

    /**
     *
     * @param gc
     */
    public CanvasKeyHandler(GameController gc) {
        this.gc = gc;
    }

    /**
     *
     * @param event
     */
    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            pressKey(event.getCode());
        } else if(event.getEventType() == KeyEvent.KEY_RELEASED) {
            releaseKey(event.getCode());
        }
    }

    /**
     *
     * @param code
     */
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

    /**
     *
     * @param code
     */
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
