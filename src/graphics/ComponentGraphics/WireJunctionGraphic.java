package graphics.ComponentGraphics;

import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.Component;
import logic.components.WireJunction;

public class WireJunctionGraphic extends ComponentGraphic {

    ContextMenu menu;

	private ComponentOutputWireNode outputANode;
	private ComponentOutputWireNode outputBNode;
	private ComponentInputWireNode inputNode;
	
	public WireJunctionGraphic() {
        init();
        menu = createContextMenu();
        addMouseHandler();
	}

	private void init() {
	    initNodes();
	    initComponent();
    }

    private void initNodes() {
        inputNode = new ComponentInputWireNode(this, "input");
        outputANode = new ComponentOutputWireNode(this, "outputA");
        outputBNode = new ComponentOutputWireNode(this, "outputB");
    }

    private void initComponent() {
        component = new WireJunction();
    }

	public void updateLoc(double x, double y) {		
        changeNodeLocs(x, y);
	}

	private void changeNodeLocs(double x, double y) {
	    changeInputNodeLoc(x, y);
	    changeOutputANodeLoc(x, y);
	    changeOutputBNodeLoc(x, y);
    }

	private void changeInputNodeLoc(double x, double y) {
        inputNode.setStartX(x);
        inputNode.setStartY(y);
        inputNode.setEndX(x - inputNode.getLength());
        inputNode.setEndY(y);
    }

    private void changeOutputANodeLoc(double x, double y) {
        outputANode.setStartX(x);
        outputANode.setStartY(y);
        outputANode.setEndX(x + outputANode.getLength());
        outputANode.setEndY(y + 5);
    }

    private void changeOutputBNodeLoc(double x, double y) {
        outputBNode.setStartX(x);
        outputBNode.setStartY(y);
        outputBNode.setEndX(x + outputBNode.getLength());
        outputBNode.setEndY(y - 5);
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
	    //Do not use
	}

	@Override
	public void addMouseHandler() {
        inputNode.addEventHandler(MouseEvent.ANY, new ComponentGraphicMouseHandler(menu, getGraphics()));
        outputANode.addEventHandler(MouseEvent.ANY, new ComponentGraphicMouseHandler(menu, getGraphics()));
        outputBNode.addEventHandler(MouseEvent.ANY, new ComponentGraphicMouseHandler(menu, getGraphics()));
	}

    @Override
	public void updateWireText() {
    	outputANode.updateText();
    	outputBNode.updateText();
	}

	@Override
	protected void removeOutputLines() {
        Pane parentPane = getParentPane();
        outputANode.clearLines(parentPane);
        outputBNode.clearLines(parentPane);
	}
}
