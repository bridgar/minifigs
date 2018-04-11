package control;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;

public class CanvasScrollHandler implements EventHandler<ScrollEvent> {

    private final GameController gc;

    public CanvasScrollHandler(GameController gc) {
        this.gc = gc;
    }

    @Override
    public void handle(ScrollEvent event) {
        if(event.getEventType() == ScrollEvent.SCROLL) {
            gc.scrollMouse(event.getDeltaY());
        }
    }
}
