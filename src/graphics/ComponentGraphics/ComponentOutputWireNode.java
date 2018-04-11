package graphics.ComponentGraphics;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import logic.ExecutionEnvironment;
import logic.Wire;
import logic.components.Component;

import java.util.ArrayList;

public class ComponentOutputWireNode extends Line {
    private static final double LENGTH = 5;
    private ArrayList<Line> wireGraphicLines;
    private ComponentGraphic componentGraphic;
    private String name;

    private static final double MAX_CONNECTION_DISTANCE = 10.0;

    public double getLength() {
        return LENGTH;
    }

    public ComponentGraphic getComponentGraphic() { return componentGraphic; }

    public String getName() { return name; }

    public ComponentOutputWireNode(ComponentGraphic parentComponentGraphic, String outputName){
        setStrokeWidth(3);
        wireGraphicLines = new ArrayList<Line>();
        componentGraphic = parentComponentGraphic;
        name = outputName;
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
                    Line firstLineHorizontal = new Line(getEndX(), getEndY(), getEndX(), getEndY());
                    wireGraphicLines.add(firstLineHorizontal);
                    parentPane.getChildren().add(firstLineHorizontal);

                    Line firstLineVertical = new Line(getEndX(), getEndY(), getEndX(), getEndY());
                    wireGraphicLines.add(firstLineVertical);
                    parentPane.getChildren().add(firstLineVertical);

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

                    Line lastLineVertical = wireGraphicLines.get(wireGraphicLines.size() - 1);
                    Line lastLineHorizontal = wireGraphicLines.get(wireGraphicLines.size() - 2);

                    lastLineHorizontal.setEndX(e.getX());

                    lastLineVertical.setStartX(e.getX());
                    lastLineVertical.setEndX(e.getX());
                    lastLineVertical.setEndY(e.getY());

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

                            if (distanceBetweenWireAndNode < MAX_CONNECTION_DISTANCE) {
                                connectingInputNode = (ComponentInputWireNode)nodeInPane;
                            }
                        }
                    }

                    Line lastLineVertical = wireGraphicLines.get(wireGraphicLines.size() - 1);
                    Line lastLineHorizontal = wireGraphicLines.get(wireGraphicLines.size() - 2);
                    if (connectingInputNode != null) {
                        if (connectingInputNode.getStartY() != lastLineVertical.getStartY()) {
                            lastLineHorizontal.setEndX(connectingInputNode.getStartX());

                            lastLineVertical.setStartX(connectingInputNode.getStartX());
                            lastLineVertical.setEndX(connectingInputNode.getStartX());
                            lastLineVertical.setEndY(connectingInputNode.getStartY());

                            getParent().removeEventHandler(MouseEvent.ANY, this);
                        }
                        else {
                            parentPane.getChildren().remove(wireGraphicLines.remove(wireGraphicLines.size() - 1));
                            lastLineHorizontal.setEndX(connectingInputNode.getStartX());
                        }

                        Component outputComponent = getComponentGraphic().getComponent();
                        Component inputComponent = connectingInputNode.getComponentGraphic().getComponent();

                        Wire logicalWire = new Wire();
                        inputComponent.connectOutputWire(logicalWire, getName());
                        outputComponent.connectInputWire(logicalWire, connectingInputNode.getName());
                    }
                    else {
                        Line newLineVertical = new Line(lastLineVertical.getEndX(), lastLineVertical.getEndY(), lastLineVertical.getEndX(), lastLineVertical.getEndY());
                        wireGraphicLines.add(newLineVertical);
                        parentPane.getChildren().add(newLineVertical);

                        Line newLineHorizontal = new Line(lastLineVertical.getEndX(), lastLineVertical.getEndY(), lastLineVertical.getEndX(), lastLineVertical.getEndY());
                        wireGraphicLines.add(newLineHorizontal);
                        parentPane.getChildren().add(newLineHorizontal);
                    }
                }
            }
        });
    }
}

/*
 --------------------------------------------------------------------------------------------------
| ---------------------------------- Input and Output names -------------------------------------- |
 --------------------------------------------------------------------------------------------------
| Adder         | inputA        | inputB          | output        |                |               |
 --------------------------------------------------------------------------------------------------
| ALU           | opCode        | inputA          | inputB        | output         |               |
 --------------------------------------------------------------------------------------------------
| BitExtender   | input         | output          |               |                |               |
 --------------------------------------------------------------------------------------------------
| BitShifter    | input         | output          |               |                |               |
 --------------------------------------------------------------------------------------------------
| Comparator    | inputA        | inputB          | outputLT      | outputEQ       | outputGT      |
 --------------------------------------------------------------------------------------------------
| ConstantValue | output        |                 |               |                |               |
 --------------------------------------------------------------------------------------------------
| DataMemory    | ?             |                 |               |                |               |
 --------------------------------------------------------------------------------------------------
| MUX           | ?             |                 |               |                |               |
 --------------------------------------------------------------------------------------------------
| RegisterFile  | ?             |                 |               |                |               |
 --------------------------------------------------------------------------------------------------
| WireJunction  | input         | outputA         | outputB       |                |               |
 --------------------------------------------------------------------------------------------------
| WireSplitter  | input         | output          |               |                |               |
 --------------------------------------------------------------------------------------------------
 */