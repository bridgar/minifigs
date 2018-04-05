package control;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CanvasMouseHandler implements EventHandler<MouseEvent> {

    private final double MIN_DRAG_DISTANCE = 25.0;
    private boolean dragging = false;
    private double xPress, yPress, xDrag, yDrag;
    private GameController gc;

    public CanvasMouseHandler(GameController gc) {
        this.gc = gc;
    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            xPress = event.getSceneX();
            yPress = event.getSceneY();
            gc.toggleSelectGameObject(xPress, yPress); //TODO shouldn't always select
            dragging = false;
        }
        else if(event.getEventType() == MouseEvent.DRAG_DETECTED) dragging = true;
        else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) handleDrag(event);
        else if(event.getEventType() == MouseEvent.MOUSE_RELEASED) handleClick(event);
    }

    private void handleDrag(MouseEvent event) {
        xDrag = event.getSceneX();
        yDrag = event.getSceneY();

        double dragDistance = Math.sqrt((xPress - xDrag) * (xPress - xDrag) + (yPress - yDrag) * (yPress - yDrag));
        if(dragDistance > MIN_DRAG_DISTANCE) {
            //TODO notify gc of drag
            System.out.println(dragDistance);
        }
    }

    private void handleClick(MouseEvent event) {
        if(event.getButton() == MouseButton.SECONDARY) handleRightClick(event);
        else if(event.getButton() == MouseButton.MIDDLE) handleMiddleClick(event);
    }

    private void handleRightClick(MouseEvent event) {

    }

    private void handleMiddleClick(MouseEvent event) {

    }
}
