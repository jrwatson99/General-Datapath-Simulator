package graphics.ComponentGraphics;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import logic.ExecutionEnvironment;

import java.util.ArrayList;

public class ComponentOutputWireNode extends Line {
    private static final double LENGTH = 5;
    private ArrayList<Line> wireGraphicLines;

    private static final double MAX_CONNECTION_DISTANCE = 5.0;

    public double getLength() {
        return LENGTH;
    }

    public ComponentOutputWireNode() {
        setStrokeWidth(3);
        wireGraphicLines = new ArrayList<Line>();
        addOutputNodeClickListener();
    }

    private void addOutputNodeClickListener() {
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                if (ExecutionEnvironment.getExecutionEnvironment().getPlacingWireStatus() == true) {
                    ExecutionEnvironment.getExecutionEnvironment().stopPlacingWire();

                    //remove all lines to reset wire
                    Pane parentPane = ((Pane) getParent());

                    for (int i = wireGraphicLines.size() - 1; i >= 0; i--) {
                        parentPane.getChildren().remove(wireGraphicLines.remove(i));
                    }

                    //Add first line in wire
                    Line firstLine = new Line(getEndX(), getEndY(), getEndX(), getEndY());
                    wireGraphicLines.add(firstLine);
                    parentPane.getChildren().add(firstLine);

                    addParentPaneWirePlacingListener(parentPane);
                }
            }
        });
    }

    public void addParentPaneWirePlacingListener(Pane parentPane) {
        parentPane.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getEventType() == MouseEvent.MOUSE_MOVED) {

                    Line lastLine = wireGraphicLines.get(wireGraphicLines.size() - 1);

                    if (wireGraphicLines.size() % 2 == 0) {
                        lastLine.setEndY(e.getY());
                    }
                    else {
                        lastLine.setEndX(e.getX());
                    }

                    ExecutionEnvironment.getExecutionEnvironment().startPlacingWire();
                }
                else if (e.getEventType() == MouseEvent.MOUSE_CLICKED && ExecutionEnvironment.getExecutionEnvironment().getPlacingWireStatus() == true) {

                    ComponentInputWireNode connectingInputNode = null;

                    for (int i = 0; i < ((Pane)getParent()).getChildren().size(); i++) {
                        Node nodeInPane = ((Pane)getParent()).getChildren().get(i);
                        if (nodeInPane instanceof ComponentInputWireNode) {
                            double distanceBetweenWireAndNode =
                                    Math.sqrt(
                                    Math.pow(wireGraphicLines.get(wireGraphicLines.size() - 1).getEndX() - ((ComponentInputWireNode)nodeInPane).getStartX(), 2)
                                    + Math.pow(wireGraphicLines.get(wireGraphicLines.size() - 1).getEndY() - ((ComponentInputWireNode)nodeInPane).getStartY(), 2)
                                    );

                            System.out.println(distanceBetweenWireAndNode);

                            if (distanceBetweenWireAndNode < MAX_CONNECTION_DISTANCE) {
                                connectingInputNode = (ComponentInputWireNode)nodeInPane;
                            }
                        }
                    }

                    Line lastLine = wireGraphicLines.get(wireGraphicLines.size() - 1);
                    if (connectingInputNode != null) {
                        lastLine.setEndX(connectingInputNode.getStartX());
                        lastLine.setEndY(connectingInputNode.getStartY());
                        getParent().removeEventHandler(MouseEvent.ANY, this);
                    }
                    else {
                        Line newLine = new Line(lastLine.getEndX(), lastLine.getEndY(), lastLine.getEndX(), lastLine.getEndY());
                        wireGraphicLines.add(newLine);
                        parentPane.getChildren().add(newLine);
                    }
                }
            }
        });
    }


}
