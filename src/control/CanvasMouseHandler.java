package control;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *  CanvasMouseHandler accepts all MouseEvents for the Canvas. Contains reference to GameController to notify it of changes.
 */
class CanvasMouseHandler implements EventHandler<MouseEvent> {

    private static final double MIN_DRAG_DISTANCE = 25.0;
    private boolean dragging = false;
    private final GameController gc;

    /**
     *
     * @param gc
     */
    public CanvasMouseHandler(GameController gc) {
        this.gc = gc;
    }

    /**
     *
     * @param event
     */
    @Override
    public void handle(MouseEvent event) {
        if(event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            double xPress = event.getSceneX();
            double yPress = event.getSceneY();
            gc.click(new Point2D(xPress, yPress));
            dragging = false;
        }
        else if(event.getEventType() == MouseEvent.DRAG_DETECTED) dragging = true;
        else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) handleDrag(event);
        else if(event.getEventType() == MouseEvent.MOUSE_RELEASED) handleRelease(event);
    }

    /**
     *
     * @param event
     */
    private void handleDrag(MouseEvent event) {
        double xDrag = event.getSceneX();
        double yDrag = event.getSceneY();


        Point2D dragVec = new Point2D(xDrag, yDrag);

        double dragDistance = dragVec.magnitude();
        if(dragDistance > MIN_DRAG_DISTANCE) {
            gc.drag(dragVec);
        }
    }

    /**
     *
     * @param event
     */
    private void handleRelease(MouseEvent event) {
        if(event.getButton() == MouseButton.SECONDARY) handleRightClick(event);
        else if(event.getButton() == MouseButton.MIDDLE) handleMiddleClick(event);
        else handleLeftClick(event);
    }

    /**
     *
     * @param event
     */
    private void handleRightClick(MouseEvent event) {

    }

    /**
     *
     * @param event
     */
    private void handleMiddleClick(MouseEvent event) {

    }

    private void handleLeftClick(MouseEvent event) {
        if(dragging) {
            gc.finishMove();
        }
    }
}
