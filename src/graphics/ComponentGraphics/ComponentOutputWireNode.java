package graphics.ComponentGraphics;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.ExecutionEnvironment;
import logic.Wire;
import logic.WireListener;
import logic.components.Component;

import java.util.ArrayList;

public class ComponentOutputWireNode extends Line {
    private static final double LENGTH = 5;
    private ArrayList<Line> wireGraphicLines;
    private ComponentGraphic componentGraphic;
    private String name;
    private Wire logicalWire;
    private Text value;
    
    private static final double MAX_CONNECTION_DISTANCE = 10.0;

    public double getLength() {
        return LENGTH;
    }
    public Text getValue() {return value;}
    public ComponentGraphic getComponentGraphic() { return componentGraphic; }
    public Wire getWire() {return logicalWire;}
    public void setWire(Wire newWire) { logicalWire = newWire;}
    public String getName() { return name; }

    public ArrayList<Line> getLines() {return wireGraphicLines;}

    public ComponentOutputWireNode(ComponentGraphic parentComponentGraphic, String outputName){
        setStrokeWidth(3);
        wireGraphicLines = new ArrayList<Line>();
        componentGraphic = parentComponentGraphic;
        name = outputName;
        addOutputNodeClickListener();
        value = new Text();
        value.setFill(Color.RED);
        value.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20));
    }

    public void clearLines(Pane parentPane) {
        //remove all lines to reset wire
        for (int i = wireGraphicLines.size() - 1; i >= 0; i--) {
            parentPane.getChildren().remove(wireGraphicLines.remove(i));
        }
    }
    
    
    private void addOutputNodeClickListener() {
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                if (ExecutionEnvironment.getExecutionEnvironment().getPlacingWireStatus() && e.getSource() instanceof ComponentOutputWireNode) {
                    Pane parentPane = ((Pane) getParent());

                    if (ExecutionEnvironment.getExecutionEnvironment().getWireSelectedStatus()) {
                        ExecutionEnvironment.getExecutionEnvironment().getCurrentlySelectedOutputNode().clearLines(parentPane);
                        ExecutionEnvironment.getExecutionEnvironment().setWireSelectedStatus(false);
                    }

                    clearLines(parentPane);

                    //Add first line in wire
                    Line firstLineHorizontal = new Line(getEndX(), getEndY(), getEndX(), getEndY());
                    wireGraphicLines.add(firstLineHorizontal);
                    parentPane.getChildren().add(firstLineHorizontal);

                    Line firstLineVertical = new Line(getEndX(), getEndY(), getEndX(), getEndY());
                    wireGraphicLines.add(firstLineVertical);
                    parentPane.getChildren().add(firstLineVertical);

                    ExecutionEnvironment.getExecutionEnvironment().setCurrentlySelectedOutputNode((ComponentOutputWireNode)e.getSource());

                    addParentPaneWirePlacingListener(parentPane, (ComponentOutputWireNode)e.getSource());
                }
            }
        });
    }
    
    public void updateText() {
    	if(value != null && logicalWire !=null) {
    		value.setText(logicalWire.getValue().toString(ExecutionEnvironment.getExecutionEnvironment().getRadix()));
    	}
    }
    private class TextUpdater implements WireListener{

        private Component outputComponent;

        public TextUpdater(Component outputComponent) {
            this.outputComponent = outputComponent;
        }

		@Override
		public void onValueChange() {
			value.setText(logicalWire.getValue().toString(ExecutionEnvironment.getExecutionEnvironment().getRadix()));

            try {
                outputComponent.Update();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    	
    }

    public void addParentPaneWirePlacingListener(Pane parentPane, ComponentOutputWireNode outputNode) {
        parentPane.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (ExecutionEnvironment.getExecutionEnvironment().getPlacingWireStatus()) {
                    if (e.getEventType() == MouseEvent.MOUSE_MOVED) {

                        Line lastLineVertical = outputNode.getLines().get(outputNode.getLines().size() - 1);
                        Line lastLineHorizontal = outputNode.getLines().get(outputNode.getLines().size() - 2);

                        lastLineHorizontal.setEndX(e.getX());

                        lastLineVertical.setStartX(e.getX());
                        lastLineVertical.setEndX(e.getX());
                        lastLineVertical.setEndY(e.getY());

                        ExecutionEnvironment.getExecutionEnvironment().setWireSelectedStatus(true);
                    }
                    else if (e.getEventType() == MouseEvent.MOUSE_CLICKED && ExecutionEnvironment.getExecutionEnvironment().getWireSelectedStatus()) {

                        ComponentInputWireNode connectingInputNode = null;
                        ComponentOutputWireNode newOutputNode = null;

                        for (int i = 0; i < ((Pane) (outputNode.getParent())).getChildren().size(); i++) {
                            Node nodeInPane = ((Pane) (outputNode.getParent())).getChildren().get(i);
                            if (nodeInPane instanceof ComponentInputWireNode) {
                                double distanceBetweenWireAndNode =
                                        Math.sqrt(
                                                Math.pow(outputNode.getLines().get(outputNode.getLines().size() - 1).getEndX() - ((ComponentInputWireNode) nodeInPane).getStartX(), 2)
                                                        + Math.pow(outputNode.getLines().get(outputNode.getLines().size() - 1).getEndY() - ((ComponentInputWireNode) nodeInPane).getStartY(), 2)
                                        );

                                if (distanceBetweenWireAndNode < MAX_CONNECTION_DISTANCE) {
                                    connectingInputNode = (ComponentInputWireNode) nodeInPane;
                                }
                            } else if ((nodeInPane instanceof ComponentOutputWireNode) && !((ComponentOutputWireNode) nodeInPane).equals((ComponentOutputWireNode) ExecutionEnvironment.getExecutionEnvironment().getCurrentlySelectedOutputNode())) {
                                double distanceBetweenWireAndNode =
                                        Math.sqrt(
                                            Math.pow(outputNode.getLines().get(outputNode.getLines().size() - 1).getEndX() - ((ComponentOutputWireNode) nodeInPane).getStartX(), 2)
                                            + Math.pow(outputNode.getLines().get(outputNode.getLines().size() - 1).getEndY() - ((ComponentOutputWireNode) nodeInPane).getStartY(), 2)
                                        );

                                if (distanceBetweenWireAndNode < MAX_CONNECTION_DISTANCE) {
                                    newOutputNode = (ComponentOutputWireNode) nodeInPane;
                                }
                            }
                        }

                        Line lastLineVertical = outputNode.getLines().get(outputNode.getLines().size() - 1);
                        Line lastLineHorizontal = outputNode.getLines().get(outputNode.getLines().size() - 2);
                        if (connectingInputNode != null) {
                            if (connectingInputNode.getStartY() != lastLineVertical.getStartY()) {
                                if (connectingInputNode.getStartX() > lastLineHorizontal.getStartX()) {
                                    double intermediateX = lastLineHorizontal.getStartX() + ((connectingInputNode.getStartX() - lastLineHorizontal.getStartX()) * (4.0 / 5.0));

                                    lastLineHorizontal.setEndX(intermediateX);

                                    lastLineVertical.setStartX(intermediateX);
                                    lastLineVertical.setEndX(intermediateX);
                                    lastLineVertical.setEndY(connectingInputNode.getStartY());

                                    Line finalLine = new Line(intermediateX, connectingInputNode.getStartY(), connectingInputNode.getStartX(), connectingInputNode.getStartY());
                                    outputNode.getLines().add(finalLine);
                                    parentPane.getChildren().add(finalLine);
                                } else {
                                    lastLineHorizontal.setEndX(connectingInputNode.getStartX());

                                    lastLineVertical.setStartX(connectingInputNode.getStartX());
                                    lastLineVertical.setEndX(connectingInputNode.getStartX());
                                    lastLineVertical.setEndY(connectingInputNode.getStartY());
                                }
                            } else {
                                parentPane.getChildren().remove(outputNode.getLines().remove(outputNode.getLines().size() - 1));
                                lastLineHorizontal.setEndX(connectingInputNode.getStartX());
                            }

                            ComponentOutputWireNode previouslyConnectedOutputNode = connectingInputNode.getOutputNode();

                            if (previouslyConnectedOutputNode != null && !outputNode.equals(previouslyConnectedOutputNode)) {
                                previouslyConnectedOutputNode.clearLines(parentPane);
                            }

                            connectingInputNode.setOutputNode(outputNode);

                            outputNode.getValue().setX(lastLineVertical.getStartX());
                            outputNode.getValue().setY(lastLineVertical.getStartY());
                            //System.out.println("x: "+value.getX()+"    y: "+value.getY());
                            Component outputComponent = outputNode.getComponentGraphic().getComponent();
                            Component inputComponent = connectingInputNode.getComponentGraphic().getComponent();

                            outputNode.setWire(new Wire());
                            outputNode.getWire().addWireListener(new TextUpdater(connectingInputNode.getComponent()));
                            outputComponent.connectOutputWire(outputNode.getWire(), outputNode.getName());
                            inputComponent.connectInputWire(outputNode.getWire(), connectingInputNode.getName());

                            ExecutionEnvironment.getExecutionEnvironment().setWireSelectedStatus(false);
                            ExecutionEnvironment.getExecutionEnvironment().setCurrentlySelectedOutputNode(null);
                            getParent().removeEventHandler(MouseEvent.ANY, this);
                        } else if (newOutputNode != null) {

                            //remove all lines to reset current output node
                            for (int i = outputNode.getLines().size() - 1; i >= 0; i--) {
                                parentPane.getChildren().remove(outputNode.getLines().remove(i));
                            }

                            //remove all lines from the new output node to reset it
                            for (int i = newOutputNode.getLines().size() - 1; i >= 0; i--) {
                                parentPane.getChildren().remove(newOutputNode.getLines().remove(i));
                            }

                            //Add first line in wire
                            Line firstLineHorizontal = new Line(newOutputNode.getEndX(), newOutputNode.getEndY(), newOutputNode.getEndX(), newOutputNode.getEndY());
                            newOutputNode.getLines().add(firstLineHorizontal);
                            parentPane.getChildren().add(firstLineHorizontal);

                            Line firstLineVertical = new Line(newOutputNode.getEndX(), newOutputNode.getEndY(), newOutputNode.getEndX(), newOutputNode.getEndY());
                            newOutputNode.getLines().add(firstLineVertical);
                            parentPane.getChildren().add(firstLineVertical);

                            ExecutionEnvironment.getExecutionEnvironment().setCurrentlySelectedOutputNode(newOutputNode);

                            addParentPaneWirePlacingListener(parentPane, newOutputNode);
                        }
                        else {
                            Line newLineVertical = new Line(lastLineVertical.getEndX(), lastLineVertical.getEndY(), lastLineVertical.getEndX(), lastLineVertical.getEndY());
                            outputNode.getLines().add(newLineVertical);
                            parentPane.getChildren().add(newLineVertical);

                            Line newLineHorizontal = new Line(lastLineVertical.getEndX(), lastLineVertical.getEndY(), lastLineVertical.getEndX(), lastLineVertical.getEndY());
                            outputNode.getLines().add(newLineHorizontal);
                            parentPane.getChildren().add(newLineHorizontal);
                        }
                    }
                }
                else {
                    outputNode.clearLines(parentPane);
                    getParent().removeEventHandler(MouseEvent.ANY, this);
                }
            }
        });
    }
}

/*
 ----------------------------------------------------------------------------------------------------------------------------------
| ---------------------------------- Input and Output names ---------------------------------------------------------------------- |
 ----------------------------------------------------------------------------------------------------------------------------------
| Adder         | inputA        | inputB          | output        |                |               |               |               |
 ----------------------------------------------------------------------------------------------------------------------------------
| ALU           | opCode        | inputA          | inputB        | output         | zero          |               |               |
 ----------------------------------------------------------------------------------------------------------------------------------
| BitExtender   | input         | output          |               |                |               |               |               |
 ----------------------------------------------------------------------------------------------------------------------------------
| BitShifter    | input         | output          |               |                |               |               |               |
 ----------------------------------------------------------------------------------------------------------------------------------
| Comparator    | inputA        | inputB          | outputLT      | outputEQ       | outputGT      |               |               |
 ----------------------------------------------------------------------------------------------------------------------------------
| ConstantValue | output        |                 |               |                |               |               |               |
 ----------------------------------------------------------------------------------------------------------------------------------
| DataMemory    | address       | readEn          | writeData     | writeEn        | CLK           | readData      |               |
 ----------------------------------------------------------------------------------------------------------------------------------
| MUX           | inputA        | inputB          | select        | output         |               |               |               |
 ----------------------------------------------------------------------------------------------------------------------------------
| RegisterFile  | readAddress1  | readAddress2    | writeAddress  | writeData      | writeEn       | readData1     | readData2     |
 ----------------------------------------------------------------------------------------------------------------------------------
| WireJunction  | input         | outputA         | outputB       |                |               |               |               |
 ----------------------------------------------------------------------------------------------------------------------------------
| WireSplitter  | input         | output          |               |                |               |               |               |
 ----------------------------------------------------------------------------------------------------------------------------------
 */