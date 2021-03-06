package graphics.ComponentGraphics;

import graphics.GUIElements.BitExtenderConfigWindow;
import graphics.GUIElements.DefaultConfigWindow;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.BitExtender;
import logic.components.Component;

public class BitExtenderGraphic extends ComponentGraphic {

    private static final double HEIGHT = 30;
    private static final double WIDTH = 60;

    private Rectangle rectangle;
    private ContextMenu menu;

    private ComponentInputWireNode inputNode;
    private ComponentOutputWireNode outputNode;
    
    public BitExtenderGraphic() {
        init();
        menu = createContextMenu();
        addMouseHandler();
    }

    private void init() {
        initShape();
        initNodes();
        initComponent();
    }

    private void initShape() {
        rectangle = new Rectangle();
        rectangle.setHeight(HEIGHT);
        rectangle.setWidth(WIDTH);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
    }

    private void initNodes() {
        inputNode = new ComponentInputWireNode(this, "input");
        outputNode = new ComponentOutputWireNode(this, "output");
    }

    private void initComponent() {
        component = new BitExtender();
    }

	@Override
	public void updateLoc(double x, double y) {
        changeTextLoc(x, y);
        changeShapeLoc(x, y);
        changeNodeLocs(x, y);
	}

	private void changeShapeLoc(double x, double y) {
        rectangle.setX(x);
        rectangle.setY(y);
    }

    private void changeNodeLocs(double x, double y) {
        changeInputNodeLoc(x, y);
        changeOutputNodeLoc(x, y);
    }

    private void changeInputNodeLoc(double x, double y) {
        inputNode.setStartX(x - inputNode.getLength());
        inputNode.setStartY(y + 15);
        inputNode.setEndX(x);
        inputNode.setEndY(y + 15);
    }

    private void changeOutputNodeLoc(double x, double y) {
        outputNode.setStartX(x + WIDTH);
        outputNode.setStartY(y + 15);
        outputNode.setEndX(x + WIDTH + outputNode.getLength());
        outputNode.setEndY(y + 15);
    }

    private void changeTextLoc(double x, double y) {
        updateTextLoc(x+5,y+15);
    }

	@Override
	public Shape[] getGraphics() {
		Shape[] graphics = {
		        rectangle,
        		outputNode,
        		inputNode};
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

	@Override
	public void config() {
		BitExtenderConfigWindow cfg = new BitExtenderConfigWindow("config",this);
		cfg.showAndWait();
	}

    @Override
    public void addMouseHandler() {
        rectangle.addEventHandler(MouseEvent.ANY, new ComponentGraphicMouseHandler(menu, getGraphics()));
    }

    @Override
    protected void removeOutputLines() {
        Pane parentPane = getParentPane();
        outputNode.clearLines();
    }

    @Override
	public void updateWireText() {
		outputNode.updateText();		
	}
}
