package graphics.ComponentGraphics;

import graphics.GUIElements.WireSplitterConfigWindow;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.Component;
import logic.components.WireSplitter;

public class WireSplitterGraphic extends ComponentGraphic {

    ContextMenu menu;

	private ComponentOutputWireNode outputNode;
	private ComponentInputWireNode inputNode;
	
	public WireSplitterGraphic() {
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
        outputNode = new ComponentOutputWireNode(this, "output");
    }

    private void initComponent() {
        component = new WireSplitter();
    }

	public void updateLoc(double x, double y) {		
        changeNodeLocs(x, y);
	}

	private void changeNodeLocs(double x, double y) {
	    changeInputNodeLoc(x, y);
	    changeOutputNodeLoc(x, y);
    }

	private void changeInputNodeLoc(double x, double y) {
        inputNode.setStartX(x);
        inputNode.setStartY(y);
        inputNode.setEndX(x - inputNode.getLength());
        inputNode.setEndY(y);
    }

    private void changeOutputNodeLoc(double x, double y) {
        outputNode.setStartX(x);
        outputNode.setStartY(y);
        outputNode.setEndX(x + outputNode.getLength());
        outputNode.setEndY(y);
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

	@Override
	public Component getComponent() {
		return component;
	}

	public void config() {
		WireSplitterConfigWindow cfg = new WireSplitterConfigWindow("config",this);
		cfg.showAndWait();
	}

	@Override
	public void addMouseHandler() {
        inputNode.addEventHandler(MouseEvent.ANY, new ComponentGraphicMouseHandler(menu, getGraphics()));
        outputNode.addEventHandler(MouseEvent.ANY, new ComponentGraphicMouseHandler(menu, getGraphics()));
	}

	@Override
	public void updateWireText() {
		outputNode.updateText();		
	}

	@Override
	protected void removeOutputLines() {
        Pane parentPane = getParentPane();
        outputNode.clearLines();
	}
}
