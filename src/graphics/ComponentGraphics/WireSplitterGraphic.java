package graphics.ComponentGraphics;

import graphics.GUIElements.WireSplitterConfigWindow;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.Component;
import logic.components.WireSplitter;

public class WireSplitterGraphic extends ComponentGraphic {

	private ComponentOutputWireNode outputNode;
	private ComponentInputWireNode inputNode;
    
	private WireSplitter junct;
	
	public WireSplitterGraphic() {
		outputNode = new ComponentOutputWireNode(this, "output");
		inputNode = new ComponentInputWireNode(this, "input");
		junct = new WireSplitter();
		
	}

	public void updateLoc(double x, double y) {		

        outputNode.setStartX(x);
        outputNode.setStartY(y);
        outputNode.setEndX(x + outputNode.getLength());
        outputNode.setEndY(y);
        
        
        inputNode.setStartX(x);
        inputNode.setStartY(y);
        inputNode.setEndX(x - inputNode.getLength());
        inputNode.setEndY(y);
		
		
	}

	public Shape[] getGraphics() {
		Shape[] graphics = new Shape[]  {
				outputNode,
				inputNode
		};
		return graphics;
	}
    @Override
	public Text[] getValueText() {
		Text[] t = new Text[] {
				outputNode.getValue()
		};
		return t;
	}
	public WireSplitter getComponent() {
		return junct;
	}


	public void config() {
		WireSplitterConfigWindow cfg = new WireSplitterConfigWindow("config",this);
		cfg.showAndWait();
	}

	@Override
	public void addMouseHandler() {

	}
}
