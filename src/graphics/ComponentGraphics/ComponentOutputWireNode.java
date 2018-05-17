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
    private ComponentGraphic getComponentGraphic() { return componentGraphic; }
    public Wire getWire() {return logicalWire;}
    public void setWire(Wire newWire) { logicalWire = newWire;}
    public String getName() { return name; }

    private ArrayList<Line> getLines() {return wireGraphicLines;}

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

    private void disconnectWire() {
        logicalWire = null;
        clearLines();
    }

    public void clearLines() {
        for (int i = wireGraphicLines.size() - 1; i >= 0; i--) {
            removeLine(wireGraphicLines.get(i));
        }
    }

    private void removeLine(Line line) {
        wireGraphicLines.remove(line);
        getParentPane().getChildren().remove(line);
    }

    private void removeLine(int index) {
        removeLine(wireGraphicLines.get(index));
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
    }

    private void addFirstLinesToNodeAndWindow() {
        addLine(new Line(getEndX(), getEndY(), getEndX(), getEndY()));
        addLine(new Line(getEndX(), getEndY(), getEndX(), getEndY()));
    }

    private void addLine(Line line) {
        wireGraphicLines.add(line);
        getParentPane().getChildren().add(line);
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
                connectOutputToInput(proximalInputNodes.get(0));
            }
            else {
                ArrayList<ComponentOutputWireNode> proximalOutputNodes = findProximalOutputNodes();
                if (proximalOutputNodes.size() == 1) {
                    changeSelectedOutputNode(proximalOutputNodes.get(0));
                }
                else {
                    setWireJoint();
                }
            }
        }

        private void moveLastLines(double x, double y) {
            moveLastLineVertical(x, y);
            moveLastLineHorizontal(x);
        }

        private void moveLastLineVertical(double x, double y) {
            Line lastLineVertical = outputNode.getLastLineVertical();
            lastLineVertical.setStartX(x);
            lastLineVertical.setEndX(x);
            lastLineVertical.setEndY(y);
        }

        private void moveLastLineHorizontal(double x) {
            Line lastLineHorizontal = outputNode.getLastLineHorizontal();
            lastLineHorizontal.setEndX(x);
        }

        private double getDistanceBetweenWireAndNode(Node nodeInPane) {
            return Math.sqrt(
                    Math.pow(outputNode.getLastLineVertical().getEndX() - ((Line) nodeInPane).getStartX(), 2)
                    + Math.pow(outputNode.getLastLineVertical().getEndY() - ((Line) nodeInPane).getStartY(), 2)
            );
        }

        private void connectOutputToInput(ComponentInputWireNode connectingInputNode) {

            alignWireWithInput(connectingInputNode);

            ComponentOutputWireNode previouslyConnectedOutputNode = connectingInputNode.getOutputNode();
            if (previouslyConnectedOutputNode != null && !outputNode.equals(previouslyConnectedOutputNode)) {
                previouslyConnectedOutputNode.disconnectWire();
            }

            connectingInputNode.setOutputNode(outputNode);
            moveValueTextLoc();
            Wire connectingWire = createWire(connectingInputNode);
            connectWireToInputAndOutput(connectingInputNode, connectingWire);
            endWirePlacement();
        }

        private void endWirePlacement() {
            ExecutionEnvironment.setWireSelectedStatus(false);
            ExecutionEnvironment.setCurrentlySelectedOutputNode(null);
            getParent().removeEventHandler(MouseEvent.ANY, this);
        }

        private Wire createWire(ComponentInputWireNode connectingInputNode) {
            Wire newWire = new Wire();
            newWire.addWireListener(new TextUpdater(connectingInputNode.getComponent()));

            return newWire;
        }

        private void connectWireToInputAndOutput(ComponentInputWireNode connectingInputNode, Wire connectingWire) {
            connectWireToOutput(connectingWire);
            connectWireToInput(connectingInputNode);
        }

        private void connectWireToOutput(Wire connectingWire) {
            outputNode.setWire(connectingWire);

            Component outputComponent = outputNode.getComponent();
            outputComponent.connectOutputWire(outputNode.getWire(), outputNode.getName());
        }

        private void connectWireToInput(ComponentInputWireNode connectingInputNode) {
            Component inputComponent = connectingInputNode.getComponent();
            inputComponent.connectInputWire(outputNode.getWire(), connectingInputNode.getName());
        }

        private void moveValueTextLoc() {
            Line lastLineVertical = outputNode.getLastLineVertical();
            outputNode.getValue().setX(lastLineVertical.getStartX());
            outputNode.getValue().setY(lastLineVertical.getStartY());
        }

        private void alignWireWithInput(ComponentInputWireNode connectingInputNode) {
            if (connectingInputNode.getStartY() != outputNode.getLastLineHorizontal().getStartY()) {
                alignWireWithInputDifferentY(connectingInputNode);
            }
            else {
                alignWireWithInputSameY(connectingInputNode);
            }
        }

        private void alignWireWithInputDifferentY(ComponentInputWireNode connectingInputNode) {
            if (connectingInputNode.getStartX() > outputNode.getLastLineHorizontal().getStartX()) {
                double intermediateX = getWireTurnX(connectingInputNode);
                moveLastLines(intermediateX, connectingInputNode.getStartY());
                outputNode.addLine(new Line(intermediateX, connectingInputNode.getStartY(), connectingInputNode.getStartX(), connectingInputNode.getStartY()));
            }
            else {
                moveLastLines(connectingInputNode.getStartX(), connectingInputNode.getStartY());
            }
        }

        private void alignWireWithInputSameY(ComponentInputWireNode connectingInputNode) {
            outputNode.removeLine(outputNode.getLastLineVertical());
            moveLastLineHorizontal(connectingInputNode.getStartX());
        }

        private double getWireTurnX(ComponentInputWireNode connectingInputNode) {
            return outputNode.getLastLineHorizontal().getStartX() + ((connectingInputNode.getStartX() - outputNode.getLastLineHorizontal().getStartX()) * (4.0 / 5.0));
        }

        private void changeSelectedOutputNode(ComponentOutputWireNode newOutputNode) {
            outputNode.clearLines();

            newOutputNode.clearLines();
            newOutputNode.addFirstLinesToNodeAndWindow();

            ExecutionEnvironment.setCurrentlySelectedOutputNode(newOutputNode);
            getParent().removeEventHandler(MouseEvent.ANY, this);
            addParentPaneWirePlacingHandler(parentPane, newOutputNode);
        }

        private void setWireJoint() {
            Line lastLineVertical = outputNode.getLastLineVertical();

            outputNode.addLine(new Line(lastLineVertical.getEndX(), lastLineVertical.getEndY(), lastLineVertical.getEndX(), lastLineVertical.getEndY()));
            outputNode.addLine(new Line(lastLineVertical.getEndX(), lastLineVertical.getEndY(), lastLineVertical.getEndX(), lastLineVertical.getEndY()));
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
                if (nodeInPane instanceof ComponentOutputWireNode && !nodeInPane.equals(outputNode)) {
                    if (getDistanceBetweenWireAndNode(nodeInPane) < MAX_CONNECTION_DISTANCE) {
                        proximalOutputNodes.add((ComponentOutputWireNode) nodeInPane);
                    }
                }
            }

            return proximalOutputNodes;
        }
    }

    public Component getComponent() {
        return componentGraphic.getComponent();
    }

    private Line getLastLineVertical() {
        return getLines().get(getLines().size() - 2);
    }
    private Line getLastLineHorizontal() {
        return getLines().get(getLines().size() - 1);
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