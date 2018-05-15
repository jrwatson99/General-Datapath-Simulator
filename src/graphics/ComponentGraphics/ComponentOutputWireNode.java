package graphics.ComponentGraphics;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
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
    private static final double MAX_CONNECTION_DISTANCE = 10.0;

    private ArrayList<Line> wireGraphicLines;
    private ComponentGraphic componentGraphic;
    private String name;
    private Wire logicalWire;
    private Text value;

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
        init();
        componentGraphic = parentComponentGraphic;
        name = outputName;
        addOutputNodeClickListener();
    }

    private void init() {
        initShape();
        initValueText();
    }

    private void initShape() {
        setStrokeWidth(3);
        wireGraphicLines = new ArrayList<Line>();
    }

    private void initValueText() {
        value = new Text();
        value.setFill(Color.RED);
        value.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20));
    }

    public void disconnectWire() {
        logicalWire = null;
        clearLines();
    }

    public void clearLines() {
        Pane parentPane = getParentPane();
        for (int i = wireGraphicLines.size() - 1; i >= 0; i--) {
            parentPane.getChildren().remove(wireGraphicLines.remove(i));
        }
    }

    private Pane getParentPane() {
        return componentGraphic.getParentPane();
    }
    
    private void addOutputNodeClickListener() {
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new OutputNodeClickListener());
    }

    private class OutputNodeClickListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            if (ExecutionEnvironment.getPlacingWireStatus()) {

                if (ExecutionEnvironment.getWireSelectedStatus()) {
                    cancelPlacingCurrentWire();
                }

                setupWirePlacement((ComponentOutputWireNode) e.getSource());
            }
        }

        private void setupWirePlacement(ComponentOutputWireNode outputNode) {
            disconnectWire();
            addFirstLinesToNodeAndWindow();
            ExecutionEnvironment.setCurrentlySelectedOutputNode(outputNode);

            addParentPaneWirePlacingHandler(getParentPane(), outputNode);
        }

        private void addFirstLinesToNodeAndWindow() {
            ArrayList<Line> firstLines = new ArrayList<Line>();
            firstLines.add(new Line(getEndX(), getEndY(), getEndX(), getEndY()));
            firstLines.add(new Line(getEndX(), getEndY(), getEndX(), getEndY()));
            wireGraphicLines.addAll(firstLines);
            getParentPane().getChildren().addAll(firstLines);
        }
    }

    private void cancelPlacingCurrentWire() {
        ExecutionEnvironment.getCurrentlySelectedOutputNode().clearLines();
        ExecutionEnvironment.setWireSelectedStatus(false);
    }
    
    public void updateText() {
    	if(value != null && logicalWire != null) {
    		value.setText(logicalWire.getValueAsString());
    	}
    }

    private class TextUpdater implements WireListener {

        private Component outputComponent;

        private TextUpdater(Component outputComponent) {
            this.outputComponent = outputComponent;
        }

		@Override
		public void onValueChange() {
			updateText();

            try {
                outputComponent.Update();
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void addParentPaneWirePlacingHandler(Pane parentPane, ComponentOutputWireNode outputNode) {
        parentPane.addEventHandler(MouseEvent.ANY, new ParentPaneWirePlacingHandler(parentPane, outputNode));
    }

    private class ParentPaneWirePlacingHandler implements EventHandler<MouseEvent> {

        private Pane parentPane;
        private ComponentOutputWireNode outputNode;

        private ParentPaneWirePlacingHandler(Pane parentPane, ComponentOutputWireNode outputNode) {
            this.parentPane = parentPane;
            this.outputNode = outputNode;
        }

        @Override
        public void handle(MouseEvent e) {
            if (ExecutionEnvironment.getPlacingWireStatus()) {
                updateWireStatus(e);
            }
            else {
                cancelWirePlacement();
            }
        }

        private void updateWireStatus(MouseEvent e) {
            if (e.getEventType() == MouseEvent.MOUSE_MOVED) {
                handleMouseMoved(e.getX(), e.getY());
            }
            else if (e.getEventType() == MouseEvent.MOUSE_CLICKED && ExecutionEnvironment.getWireSelectedStatus()) {
                handleMouseClicked();
            }
        }

        private void handleMouseMoved(double x, double y) {
            moveLastLines(x, y);
            ExecutionEnvironment.setWireSelectedStatus(true);
        }

        private void handleMouseClicked() {
            ArrayList<ComponentInputWireNode> proximalInputNodes = findProximalInputNodes();
            if (proximalInputNodes.size() == 1) {
                connectOutputToInput(findProximalInputNodes().get(0));
            }
            else {
                ArrayList<ComponentOutputWireNode> proximalOutputNodes = findProximalOutputNodes();
                if (proximalOutputNodes.size() == 1) {
                    changeSelectedOutputNode(findProximalOutputNodes().get(0));
                }
                else {
                    setWireJoint();
                }
            }
        }

        private void moveLastLines(double x, double y) {
            moveLastLineVertical(x, y);
            moveLastLineHorizontal(x, y);
        }

        private void moveLastLineVertical(double x, double y) {
            Line lastLineVertical = outputNode.getLines().get(outputNode.getLines().size() - 1);
            lastLineVertical.setStartX(x);
            lastLineVertical.setEndX(x);
            lastLineVertical.setEndY(y);
        }

        private void moveLastLineHorizontal(double x, double y) {
            Line lastLineHorizontal = outputNode.getLines().get(outputNode.getLines().size() - 2);
            lastLineHorizontal.setEndX(x);
        }

        private double getDistanceBetweenWireAndNode(Node nodeInPane) {
            return Math.sqrt(
                    Math.pow(outputNode.getLastLineVertical().getEndX() - ((Line) nodeInPane).getStartX(), 2)
                    + Math.pow(outputNode.getLastLineVertical().getEndY() - ((Line) nodeInPane).getStartY(), 2)
            );
        }

        private void connectOutputToInput(ComponentInputWireNode connectingInputNode) {
            Line lastLineVertical = outputNode.getLastLineVertical();
            Line lastLineHorizontal = outputNode.getLastLineHorizontal();

            if (connectingInputNode.getStartY() != lastLineVertical.getStartY()) {
                if (connectingInputNode.getStartX() > lastLineHorizontal.getStartX()) {
                    double intermediateX = getWireTurnX(connectingInputNode);

                    lastLineHorizontal.setEndX(intermediateX);

                    lastLineVertical.setStartX(intermediateX);
                    lastLineVertical.setEndX(intermediateX);
                    lastLineVertical.setEndY(connectingInputNode.getStartY());

                    Line finalLine = new Line(intermediateX, connectingInputNode.getStartY(), connectingInputNode.getStartX(), connectingInputNode.getStartY());
                    outputNode.getLines().add(finalLine);
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
                parentPane.getChildren().remove(outputNode.getLines().remove(outputNode.getLines().size() - 1));
                lastLineHorizontal.setEndX(connectingInputNode.getStartX());
            }

            ComponentOutputWireNode previouslyConnectedOutputNode = connectingInputNode.getOutputNode();

            if (previouslyConnectedOutputNode != null && !outputNode.equals(previouslyConnectedOutputNode)) {
                previouslyConnectedOutputNode.disconnectWire();
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

            ExecutionEnvironment.setWireSelectedStatus(false);
            ExecutionEnvironment.setCurrentlySelectedOutputNode(null);
            getParent().removeEventHandler(MouseEvent.ANY, this);
        }

        private double getWireTurnX(ComponentInputWireNode connectingInputNode) {
            return outputNode.getLastLineHorizontal().getStartX() + ((connectingInputNode.getStartX() - outputNode.getLastLineHorizontal().getStartX()) * (4.0 / 5.0));
        }

        private void changeSelectedOutputNode(ComponentOutputWireNode newOutputNode) {
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

            ExecutionEnvironment.setCurrentlySelectedOutputNode(newOutputNode);
            getParent().removeEventHandler(MouseEvent.ANY, this);
            addParentPaneWirePlacingHandler(parentPane, newOutputNode);
        }

        private void setWireJoint() {
            Line lastLineVertical = outputNode.getLastLineVertical();
            Line lastLineHorizontal = outputNode.getLastLineHorizontal();

            Line newLineVertical = new Line(lastLineVertical.getEndX(), lastLineVertical.getEndY(), lastLineVertical.getEndX(), lastLineVertical.getEndY());
            outputNode.getLines().add(newLineVertical);
            parentPane.getChildren().add(newLineVertical);

            Line newLineHorizontal = new Line(lastLineVertical.getEndX(), lastLineVertical.getEndY(), lastLineVertical.getEndX(), lastLineVertical.getEndY());
            outputNode.getLines().add(newLineHorizontal);
            parentPane.getChildren().add(newLineHorizontal);
        }

        private void cancelWirePlacement() {
            outputNode.clearLines();
            getParent().removeEventHandler(MouseEvent.ANY, this);
        }

        private ArrayList<ComponentInputWireNode> findProximalInputNodes() {
            ArrayList<ComponentInputWireNode> proximalInputNodes = new ArrayList<ComponentInputWireNode>();

            int numberOfNodesInWindow = parentPane.getChildren().size();
            for (int i = 0; i < numberOfNodesInWindow; i++) {
                Node nodeInPane = outputNode.getParentPane().getChildren().get(i);
                if (nodeInPane instanceof ComponentInputWireNode) {
                    if (getDistanceBetweenWireAndNode(nodeInPane) < MAX_CONNECTION_DISTANCE) {
                        proximalInputNodes.add((ComponentInputWireNode) nodeInPane);
                    }
                }
            }

            return proximalInputNodes;
        }

        private ArrayList<ComponentOutputWireNode> findProximalOutputNodes() {
            ArrayList<ComponentOutputWireNode> proximalOutputNodes = new ArrayList<ComponentOutputWireNode>();

            int numberOfNodesInWindow = parentPane.getChildren().size();
            for (int i = 0; i < numberOfNodesInWindow; i++) {
                Node nodeInPane = outputNode.getParentPane().getChildren().get(i);
                if (nodeInPane instanceof ComponentOutputWireNode) {
                    if (getDistanceBetweenWireAndNode(nodeInPane) < MAX_CONNECTION_DISTANCE) {
                        proximalOutputNodes.add((ComponentOutputWireNode) nodeInPane);
                    }
                }
            }

            return proximalOutputNodes;
        }
    }

    private Line getLastLineVertical() {
        return getLines().get(getLines().size() - 1);
    }
    private Line getLastLineHorizontal() {
        return getLines().get(getLines().size() - 2);
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