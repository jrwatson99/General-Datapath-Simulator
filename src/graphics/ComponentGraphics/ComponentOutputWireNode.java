package graphics.ComponentGraphics;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import logic.ExecutionEnvironment;

import java.util.ArrayList;

public class ComponentOutputWireNode extends Line {
    private static final double LENGTH = 5;
    private ArrayList<Line> wireGraphicLines;

    public double getLength() {
        return LENGTH;
    }

    public ComponentOutputWireNode() {
        wireGraphicLines = new ArrayList<Line>();
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (ExecutionEnvironment.getExecutionEnvironment().getPlacingWireStatus() == true) {
                    getParent().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            int i;
                            for (i = wireGraphicLines.size() - 1; i >= 0; i--) {
                                ((Pane)getParent()).getChildren().remove(wireGraphicLines.remove(i));
                            }
                            Line horizontalLine = new Line(getEndX(), getStartY(), e.getX(), getStartY());
                            Line verticalLine = new Line(e.getX(), getStartY(), e.getX(), e.getY());

                            wireGraphicLines.add(horizontalLine);
                            wireGraphicLines.add(verticalLine);

                            ((Pane)getParent()).getChildren().addAll(horizontalLine, verticalLine);
                        }
                    });
                }
            }
        });
    }
}
