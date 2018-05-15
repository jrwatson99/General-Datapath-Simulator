package graphics.ComponentGraphics;

import graphics.GUIElements.DefaultConfigWindow;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.Comparator;
import logic.components.Component;

public class ComparatorGraphic extends ComponentGraphic {

    private static final double HEIGHT = 60;
    private static final double WIDTH = 100;

    private Rectangle rectangle;
    private ContextMenu menu;

    private ComponentInputWireNode inputANode;
    private ComponentInputWireNode inputBNode;
    private ComponentOutputWireNode outputLTNode;
    private ComponentOutputWireNode outputEQNode;
    private ComponentOutputWireNode outputGTNode;

    @Override
    public Component getComponent() {
        return component;
    }

    public ComparatorGraphic() {
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
        inputANode = new ComponentInputWireNode(this, "inputA");
        inputBNode = new ComponentInputWireNode(this, "inputB");
        outputLTNode = new ComponentOutputWireNode(this, "outputLT");
        outputEQNode = new ComponentOutputWireNode(this, "outputEQ");
        outputGTNode = new ComponentOutputWireNode(this, "outputGT");
    }

    private void initComponent() {
        component = new Comparator();
    }

    public void updateLoc(double x, double y) {
    	changeShapeLoc(x, y);
    	changeNodeLocs(x, y);
    	changeTextLoc(x, y);
    }

    private void changeShapeLoc(double x, double y) {
        rectangle.setX(x);
        rectangle.setY(y);
    }

    private void changeNodeLocs(double x, double y) {
        changeInputANodeLoc(x, y);
        changeInputBNodeLoc(x, y);
        changeOutputLTNodeLoc(x, y);
        changeOutputEQNodeLoc(x, y);
        changeOutputGTNodeLoc(x, y);
    }

    private void changeInputANodeLoc(double x, double y) {
        inputANode.setStartX(x - inputANode.getLength());
        inputANode.setStartY(y + 15);
        inputANode.setEndX(x);
        inputANode.setEndY(y + 15);
    }

    private void changeInputBNodeLoc(double x, double y) {
        inputBNode.setStartX(x - inputANode.getLength());
        inputBNode.setStartY(y + 30 );
        inputBNode.setEndX(x);
        inputBNode.setEndY(y + 30);
    }

    private void changeOutputLTNodeLoc(double x, double y) {
        outputLTNode.setStartX(x + WIDTH);
        outputLTNode.setStartY(y + 15);
        outputLTNode.setEndX(x + WIDTH + outputLTNode.getLength());
        outputLTNode.setEndY(y + 15);
    }

    private void changeOutputEQNodeLoc(double x, double y) {
        outputEQNode.setStartX(x + WIDTH);
        outputEQNode.setStartY(y + 30);
        outputEQNode.setEndX(x + WIDTH + outputEQNode.getLength());
        outputEQNode.setEndY(y + 30);
    }

    private void changeOutputGTNodeLoc(double x, double y) {
        outputGTNode.setStartX(x + WIDTH);
        outputGTNode.setStartY(y + 45);
        outputGTNode.setEndX(x + WIDTH + outputGTNode.getLength());
        outputGTNode.setEndY(y + 45);
    }

    private void changeTextLoc(double x, double y) {
        updateTextLoc(x+5,y+15);
    }

    public Shape[] getGraphics() {
        Shape[] graphics = {rectangle,
        		outputLTNode,
        		outputEQNode,
        		outputGTNode,
        		inputANode,
        		inputBNode};

        return graphics;
    }

    @Override
	public Text[] getValueText() {
		Text[] t = new Text[] {
				outputLTNode.getValue(),
				outputEQNode.getValue(),
				outputGTNode.getValue()
		};
		return t;
	}

	@Override
	public void config() {
		DefaultConfigWindow cfg = new DefaultConfigWindow("config",this);
		cfg.showAndWait();		
	}

    @Override
    public void addMouseHandler() {
        rectangle.addEventHandler(MouseEvent.ANY, new ComponentGraphicMouseHandler(menu, getGraphics()));
    }

    @Override
	public void updateWireText() {
    	outputLTNode.updateText();
    	outputGTNode.updateText();
    	outputEQNode.updateText();
	}

	@Override
    protected void removeOutputLines() {
        Pane parentPane = getParentPane();
        outputLTNode.clearLines();
        outputEQNode.clearLines();
        outputGTNode.clearLines();
    }
}
