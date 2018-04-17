package graphics.ComponentGraphics;

import javafx.scene.shape.Line;

public class ComponentInputWireNode extends Line {
    private static final double LENGTH = 5;
    private ComponentGraphic componentGraphic;
    private String name;
    private ComponentOutputWireNode outputNode;

    public double getLength() {
        return LENGTH;
    }

    public String getName() { return name; }

    public ComponentGraphic getComponentGraphic() { return componentGraphic;}

    public void setOutputNode(ComponentOutputWireNode newOutputNode) { outputNode = newOutputNode;}
    public ComponentOutputWireNode getOutputNode() { return outputNode;}

    public ComponentInputWireNode(ComponentGraphic parentComponentGraphic, String inputName) {
        setStrokeWidth(3);
        componentGraphic = parentComponentGraphic;
        name = inputName;
        outputNode = null;
    }
}
