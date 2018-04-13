package graphics.ComponentGraphics;

import javafx.scene.shape.Shape;
import logic.components.Component;
import logic.components.WireJunction;

public class WireJunctionGraphic extends ComponentGraphic {

	private ComponentOutputWireNode outputANode;
	private ComponentOutputWireNode outputBNode;
	private ComponentInputWireNode inputNode;
    
	private WireJunction junct;
	
	public WireJunctionGraphic() {
		outputANode = new ComponentOutputWireNode(this, "outputA");
		outputBNode = new ComponentOutputWireNode(this, "outputB");
		inputNode = new ComponentInputWireNode(this, "input");
		junct = new WireJunction();
		
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

	public Component getComponent() {
		return junct;
	}

	public void config() {
	}

	@Override
	public void addMouseHandler() {

	}

}
