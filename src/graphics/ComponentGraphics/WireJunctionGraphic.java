package graphics.ComponentGraphics;

import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.Component;
import logic.components.WireJunction;

public class WireJunctionGraphic extends ComponentGraphic {

	private ComponentOutputWireNode outputANode;
	private ComponentOutputWireNode outputBNode;
	private ComponentInputWireNode inputNode;
	
	public WireJunctionGraphic() {
		outputANode = new ComponentOutputWireNode(this, "outputA");
		outputBNode = new ComponentOutputWireNode(this, "outputB");
		inputNode = new ComponentInputWireNode(this, "input");
		component = new WireJunction();
		
	}

	public void updateLoc(double x, double y) {		

        outputANode.setStartX(x);
        outputANode.setStartY(y);
        outputANode.setEndX(x + outputANode.getLength());
        outputANode.setEndY(y + 5);
        
        outputBNode.setStartX(x);
        outputBNode.setStartY(y);
        outputBNode.setEndX(x + outputBNode.getLength());
        outputBNode.setEndY(y - 5);
        
        inputNode.setStartX(x);
        inputNode.setStartY(y);
        inputNode.setEndX(x - inputNode.getLength());
        inputNode.setEndY(y);
		
		
	}

	public Shape[] getGraphics() {
		Shape[] graphics = new Shape[]  {
				outputANode,
				inputNode,
				outputBNode
		};
		return graphics;
	}
    @Override
	public Text[] getValueText() {
		Text[] t = new Text[] {
				outputANode.getValue(),
				outputBNode.getValue()
		};
		return t;
	}
	public Component getComponent() {
		return component;
	}

	public void config() {
	}

	@Override
	public void addMouseHandler() {

	}

    @Override
	public void updateWireText() {
    	outputANode.updateText();
    	outputBNode.updateText();
	}
}
