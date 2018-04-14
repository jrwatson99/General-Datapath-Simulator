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

    public String getName() { return name; }

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
    
    public void updateText() {
    	if(value != null && logicalWire !=null) {
    		value.setText(logicalWire.getValue().toString(ExecutionEnvironment.getExecutionEnvironment().getRadix()));
    	}
    }
    private class TextUpdater implements WireListener{

        
		@Override
		public void onValueChange() {
			value.setText(logicalWire.getValue().toString(ExecutionEnvironment.getExecutionEnvironment().getRadix()));
//			System.out.println(logicalWire.getValue().toString());
		}
    	
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
                            if (connectingInputNode.getStartX() > lastLineHorizontal.getStartX()) {
                                double intermediateX = lastLineHorizontal.getStartX() + ((connectingInputNode.getStartX() - lastLineHorizontal.getStartX()) * (4.0 / 5.0));

                                lastLineHorizontal.setEndX(intermediateX);

                                lastLineVertical.setStartX(intermediateX);
                                lastLineVertical.setEndX(intermediateX);
                                lastLineVertical.setEndY(connectingInputNode.getStartY());

                                Line finalLine = new Line(intermediateX, connectingInputNode.getStartY(), connectingInputNode.getStartX(), connectingInputNode.getStartY());
                                wireGraphicLines.add(finalLine);
                                parentPane.getChildren().add(finalLine);
                            }
                            else {
                                lastLineHorizontal.setEndX(connectingInputNode.getStartX());

                                lastLineVertical.setStartX(connectingInputNode.getStartX());
                                lastLineVertical.setEndX(connectingInputNode.getStartX());
                                lastLineVertical.setEndY(connectingInputNode.getStartY());
                            }
                        }
                        else {
                            parentPane.getChildren().remove(wireGraphicLines.remove(wireGraphicLines.size() - 1));
                            lastLineHorizontal.setEndX(connectingInputNode.getStartX());
                        }
                        
                        value.setX(lastLineVertical.getStartX());
                        value.setY(lastLineVertical.getStartY());
//                        System.out.println("x: "+value.getX()+"    y: "+value.getY());
                        Component outputComponent = getComponentGraphic().getComponent();
                        Component inputComponent = connectingInputNode.getComponentGraphic().getComponent();

                        logicalWire = new Wire();
                        logicalWire.addWireListener(new TextUpdater());
                        outputComponent.connectOutputWire(logicalWire, getName());
                        inputComponent.connectInputWire(logicalWire, connectingInputNode.getName());

                        getParent().removeEventHandler(MouseEvent.ANY, this);
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
| DataMemory    | address       | readEn          | writeData     | writeEn        | readData      |               
 --------------------------------------------------------------------------------------------------
| MUX           | inputA        | inputB          | select        | output         |               |
 --------------------------------------------------------------------------------------------------
| RegisterFile  |               |                 |               |                |               |
 --------------------------------------------------------------------------------------------------
| WireJunction  | input         | outputA         | outputB       |                |               |
 --------------------------------------------------------------------------------------------------
| WireSplitter  | input         | output          |               |                |               |
 --------------------------------------------------------------------------------------------------
 */