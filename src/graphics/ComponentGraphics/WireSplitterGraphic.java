package graphics.ComponentGraphics;

import graphics.GUIElements.WireSplitterConfigWindow;
import javafx.scene.shape.Shape;
import logic.components.Component;
import logic.components.WireSplitter;

public class WireSplitterGraphic extends ComponentGraphic {

	private ComponentOutputWireNode outputANode;
	private ComponentInputWireNode inputNode;
    
	private WireSplitter junct;
	
	public WireSplitterGraphic() {
		outputANode = new ComponentOutputWireNode();
		inputNode = new ComponentInputWireNode();
		junct = new WireSplitter();
		
	}
	
	@Override
	public void updateLoc(double x, double y) {		

        outputANode.setStartX(x);
        outputANode.setStartY(y);
        outputANode.setEndX(x + outputANode.getLength());
        outputANode.setEndY(y);
        
        
        inputNode.setStartX(x);
        inputNode.setStartY(y);
        inputNode.setEndX(x - inputNode.getLength());
        inputNode.setEndY(y);
		
		
	}

	@Override
	public Shape[] getGraphics() {
		Shape[] graphics = new Shape[]  {
				outputANode,
				inputNode
		};
		return graphics;
	}

	@Override
	public WireSplitter getComponent() {
		return junct;
	}


	@Override
	public void config() {
		WireSplitterConfigWindow cfg = new WireSplitterConfigWindow("config",this);
		cfg.showAndWait();
	}


}
