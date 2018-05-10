package graphics.ComponentGraphics;


import graphics.GUIElements.DefaultConfigWindow;
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
import logic.components.Adder;
import logic.components.Component;

import javax.swing.plaf.basic.BasicTreeUI;

public class AdderGraphic extends ComponentGraphic {

    private static final double BIG_DIAGONAL_LENGTH_X = 50;
    private static final double BIG_DIAGONAL_LENGTH_Y = 20;
    private static final double LITTLE_DIAGONAL_LENGTH_X = 10;
    private static final double LITTLE_DIAGONAL_LENGTH_Y = 5;
    private static final double STRAIGHT_LENGTH = 30;

	private Polygon shape;
    private ContextMenu menu;
    
    private ComponentInputWireNode inputANode;
    private ComponentInputWireNode inputBNode;
    private ComponentOutputWireNode outputNode;

    @Override
    public Component getComponent() {
        return component;
    }

    public AdderGraphic() {
        init();
        addMouseHandler();
        createContextMenu();
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
        inputANode = new ComponentInputWireNode(this, "inputA");
        inputBNode = new ComponentInputWireNode(this, "inputB");
        outputNode = new ComponentOutputWireNode(this, "output");
    }

    private void initComponent() {
        component = new Adder();
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

    private void changeNodeLocs(double x, double y) {
        changeInputANodeLoc(x, y);
        changeInputBNodeLoc(x, y);
        changeOutputNodeLoc(x, y);
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

    private void updateNameTextLoc(double x, double y) {
        updateTextLoc(x + 2,y + STRAIGHT_LENGTH);
    }

    public void updateLoc(double x, double y) {
    	updateNameTextLoc(x, y);
    	changeShapeLoc(x, y);
    	changeNodeLocs(x, y);
    }

    public Shape[] getGraphics() {
        Shape[] graphics = {
        		shape,
                inputANode,
                inputBNode,
                outputNode};

        return graphics;
    }

	@Override
	public void config() {
		DefaultConfigWindow cfg = new DefaultConfigWindow("config",this);
		cfg.showAndWait();		
	}

    @Override
    public void addMouseHandler() {
        shape.addEventHandler(MouseEvent.ANY, new AdderGraphicMouseHandler());
    }

    private class AdderGraphicMouseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                updateLoc(e.getX(), e.getY());
            }
            else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                if(e.getButton() == MouseButton.PRIMARY) {
                    //do we want anything here? maybe highlight it?
                    //                        	config();
                }
                else {
                    menu.show(shape, e.getX(), e.getY());
                }
            }
        }
    }

    protected void createContextMenu() { //this should probably be moved to componentgraphic class
    	menu = new ContextMenu();

    	MenuItem cfg = new MenuItem("Config");
    	cfg.setOnAction(e -> config());

        MenuItem del = new MenuItem("Delete");
    	del.setOnAction(e -> delete());

    	menu.getItems().addAll(cfg,del);
    }

	protected void delete() {
        removeOutputLines();
        removeFromParent();
    }

    private void removeFromParent() {
        Pane parentPane = getParentPane();

        parentPane.getChildren().removeAll(getGraphics());
        parentPane.getChildren().removeAll(getValueText());
        parentPane.getChildren().remove(getText());
    }

    private void removeOutputLines() {
        Pane parentPane = getParentPane();
        outputNode.clearLines(parentPane);
    }

    private Pane getParentPane() {
        Pane parentPane;
        parentPane = ExecutionEnvironment.getExecutionEnvironment().getDataPathWindow().getPane();

        return parentPane;
    }

	@Override
	public Text[] getValueText() {
		Text[] t = new Text[] {
				outputNode.getValue()
		};
		return t;
	}

	@Override
	public void updateWireText() {
		outputNode.updateText();		
	}
}
