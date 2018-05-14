package graphics.ComponentGraphics;

import graphics.GUIElements.ALUConfigWindow;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.ExecutionEnvironment;
import logic.components.ALU;
import logic.components.Component;

import java.util.ArrayList;

public class ALUGraphic extends ComponentGraphic {

    private static final double BIG_DIAGONAL_LENGTH_X = 50;
    private static final double BIG_DIAGONAL_LENGTH_Y = 20;
    private static final double LITTLE_DIAGONAL_LENGTH_X = 10;
    private static final double LITTLE_DIAGONAL_LENGTH_Y = 5;
    private static final double STRAIGHT_LENGTH = 30;

	private Polygon shape;
    private ContextMenu menu;

    private ComponentInputWireNode inputANode;
    private ComponentInputWireNode aluOpNode;
    private ComponentInputWireNode inputBNode;
    private ComponentOutputWireNode outputNode;
    private ComponentOutputWireNode zeroNode;

    @Override
    public Component getComponent() {
        return component;
    }

    public ALUGraphic() {
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
        shape = new Polygon();
        shape.setFill(Color.WHITE);
        shape.setStroke(Color.BLACK);
    }

    private void initNodes() {
        aluOpNode = new ComponentInputWireNode(this, "opCode");
        inputANode = new ComponentInputWireNode(this, "inputA");
        inputBNode = new ComponentInputWireNode(this, "inputB");
        outputNode = new ComponentOutputWireNode(this, "output");
        zeroNode = new ComponentOutputWireNode(this, "zero");
    }

    private void initComponent() {
        component = new ALU();
    }

    public void updateLoc(double x, double y) {
    	changeTextLoc(x, y);
    	changeShapeLoc(x, y);
        changeNodeLocs(x, y);
    }

    private void changeTextLoc(double x, double y) {
        updateTextLoc(x+LITTLE_DIAGONAL_LENGTH_X, y+STRAIGHT_LENGTH+LITTLE_DIAGONAL_LENGTH_Y );
    }

    private void changeShapeLoc(double x, double y) {
        shape.getPoints().clear();
        shape.getPoints().addAll(
                x,y,
                x + BIG_DIAGONAL_LENGTH_X, y + BIG_DIAGONAL_LENGTH_Y,
                x + BIG_DIAGONAL_LENGTH_X, y + (BIG_DIAGONAL_LENGTH_Y + STRAIGHT_LENGTH),
                x, y + (2 * BIG_DIAGONAL_LENGTH_Y) + STRAIGHT_LENGTH,
                x, y + (STRAIGHT_LENGTH + (2 * LITTLE_DIAGONAL_LENGTH_Y)),
                x + LITTLE_DIAGONAL_LENGTH_X, y + (STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y),
                x, y + STRAIGHT_LENGTH
        );
    }

    private void changeNodeLocs(double x, double y) {
        changeOpCodeNodeLoc(x, y);
        changeInputANodeLoc(x, y);
        changeInputBNodeLoc(x, y);
        changeOutputNodeLoc(x, y);
        changeZeroNodeLoc(x, y);
    }

    private void changeOpCodeNodeLoc(double x, double y) {
        aluOpNode.setStartX(x+STRAIGHT_LENGTH);
        aluOpNode.setStartY(y + 5);
        aluOpNode.setEndX(x+STRAIGHT_LENGTH);
        aluOpNode.setEndY(y + (STRAIGHT_LENGTH / 2)-5);
    }

    private void changeInputANodeLoc(double x, double y) {
        inputANode.setStartX(x - inputANode.getLength());
        inputANode.setStartY(y + (STRAIGHT_LENGTH / 2));
        inputANode.setEndX(x);
        inputANode.setEndY(y + (STRAIGHT_LENGTH / 2));
    }

    private void changeInputBNodeLoc(double x, double y) {
        inputBNode.setStartX(x - inputANode.getLength());
        inputBNode.setStartY(y + STRAIGHT_LENGTH + (2 * LITTLE_DIAGONAL_LENGTH_Y) + (STRAIGHT_LENGTH / 2));
        inputBNode.setEndX(x);
        inputBNode.setEndY(y + STRAIGHT_LENGTH + (2 * LITTLE_DIAGONAL_LENGTH_Y) + (STRAIGHT_LENGTH / 2));
    }

    private void changeOutputNodeLoc(double x, double y) {
        outputNode.setStartX(x + BIG_DIAGONAL_LENGTH_X);
        outputNode.setStartY(y + STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y);
        outputNode.setEndX(x + BIG_DIAGONAL_LENGTH_X + outputNode.getLength());
        outputNode.setEndY(y + STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y);
    }

    private void changeZeroNodeLoc(double x, double y) {
        zeroNode.setStartX(x + BIG_DIAGONAL_LENGTH_X);
        zeroNode.setStartY(y + (STRAIGHT_LENGTH * 1.5) + LITTLE_DIAGONAL_LENGTH_Y);
        zeroNode.setEndX(x + BIG_DIAGONAL_LENGTH_X + outputNode.getLength());
        zeroNode.setEndY(y + (STRAIGHT_LENGTH * 1.5) + LITTLE_DIAGONAL_LENGTH_Y);
    }

    public Shape[] getGraphics() {
        Shape[] graphics = {
        		shape,
        		aluOpNode,
                inputANode,
                inputBNode,
                outputNode,
                zeroNode};

        return graphics;
    }

	@Override
	public void config() {
		ALUConfigWindow cfg = new ALUConfigWindow("ALU Configuration", this);
		cfg.showAndWait();
	}

	@Override
	public void addMouseHandler() {
        shape.addEventHandler(MouseEvent.ANY, new ComponentGraphicMouseHandler(menu, getGraphics()));
	}

    protected void removeOutputLines() {
        Pane parentPane = getParentPane();
        outputNode.clearLines(parentPane);
        zeroNode.clearLines(parentPane);
    }

	@Override
	public Text[] getValueText() {
		Text[] t = new Text[] {
				outputNode.getValue(),
                zeroNode.getValue()
		};
		return t;
	}

	@Override
	public void updateWireText() {
		outputNode.updateText();
		zeroNode.updateText();
	}

	public void setOpOrder(ArrayList<ALU.Operation> opOrder) {
        try {
            trySetOpOrder(opOrder);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
	}

	private void trySetOpOrder(ArrayList<ALU.Operation> opOrder) throws Exception {
        if (component instanceof ALU) {
            ((ALU) component).setOpOrder(opOrder);
        }
        else {
            throw new Exception("Component instance in ALUGraphic is not of type ALU");
        }
    }
}
