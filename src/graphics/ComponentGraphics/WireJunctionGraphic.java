package graphics.ComponentGraphics;

import javafx.scene.shape.Shape;
import logic.components.Component;
import logic.components.WireJunction;

public class WireJunctionGraphic extends ComponentGraphic{

	private ComponentOutputWireNode outputANode;
	private ComponentOutputWireNode outputBNode;
	private ComponentInputWireNode inputNode;
    
	private WireJunction junct;
	
	public WireJunctionGraphic() {
		outputANode = new ComponentOutputWireNode();
		outputBNode = new ComponentOutputWireNode();
		inputNode = new ComponentInputWireNode();
		junct = new WireJunction();
		
	}
	
	@Override
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

	@Override
	public Shape[] getGraphics() {
		Shape[] graphics = new Shape[]  {
				outputANode,
				inputNode,
				outputBNode
		};
		return graphics;
	}

	@Override
	public Component getComponent() {
		return junct;
	}


	@Override
	public void config() {
	}


}
