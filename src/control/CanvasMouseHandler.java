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
    private double xPress, yPress, xDrag, yDrag;
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
            xPress = event.getSceneX();
            yPress = event.getSceneY();
            dragging = false;
        }
        else if(event.getEventType() == MouseEvent.DRAG_DETECTED) dragging = true;
        else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) handleDrag(event);
        else if(event.getEventType() == MouseEvent.MOUSE_RELEASED) handleClick(event);
    }

    /**
     *
     * @param event
     */
    private void handleDrag(MouseEvent event) {
        xDrag = event.getSceneX();
        yDrag = event.getSceneY();

        gc.selectCharacter(new Point2D(xPress, yPress)); // Select the Character that you're dragging.

        Point2D dragVec = new Point2D(xDrag - xPress, yDrag - yPress);

        double dragDistance = dragVec.magnitude();
        if(dragDistance > MIN_DRAG_DISTANCE) {
            gc.dragSelected(dragVec);
        }
    }

    /**
     *
     * @param event
     */
    private void handleClick(MouseEvent event) {
        if(event.getButton() == MouseButton.SECONDARY) handleRightClick(event);
        else if(event.getButton() == MouseButton.MIDDLE) handleMiddleClick(event);
        else {
            if(!dragging) { // If the mouse is released and wasn't dragged, toggle select a GameObject.
                gc.toggleSelectCharacter(new Point2D(xPress, yPress));
            } else {
                gc.finishMove();
            }
        }
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
}
