package graphics.ComponentGraphics;

import javafx.scene.shape.Line;

public class ComponentInputWireNode extends Line {
    private static final double LENGTH = 5;
    private ComponentGraphic componentGraphic;
    private String name;

    public double getLength() {
        return LENGTH;
    }

    public String getName() { return name; }

    public ComponentGraphic getComponentGraphic() { return componentGraphic;}

    public ComponentInputWireNode(ComponentGraphic parentComponentGraphic, String inputName) {
        setStrokeWidth(3);
        componentGraphic = parentComponentGraphic;
        name = inputName;
    }
}
