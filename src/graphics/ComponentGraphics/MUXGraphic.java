package graphics.ComponentGraphics;

import graphics.GUIElements.DefaultConfigWindow;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import logic.ExecutionEnvironment;
import logic.components.Component;
import logic.components.MUX;

public class MUXGraphic extends ComponentGraphic {

    private static final double STRAIGHT_LENGTH = 40;
    private static final double STRAIGHT_SEPARATION_DISTANCE = 16;
    private static final double ARC_RADIUS = STRAIGHT_SEPARATION_DISTANCE / 2;
    private static final double NAME_X_LOC_CONSTANT = 4.8;

    private Rectangle rectangle;
    private Arc arcTop;
    private Arc arcBottom;
    private Line lineStraightLeft;
    private Line lineStraightRight;
    private ContextMenu menu;

    public Component getComponent() {
        return component;
    }

    private ComponentInputWireNode inputA;
    private ComponentInputWireNode inputB;
    private ComponentInputWireNode sel;
    private ComponentOutputWireNode out;
    
    public MUXGraphic() {
        init();
        addMouseHandler();
        createContextMenu();
    }

    private void init() {
        initShape();
        initComponent();
        initNodes();
    }

    private void initShape() {
        initRectangle();
        initArcTop();
        initArcBottom();
        initLines();
    }

    private void initRectangle() {
        rectangle = new Rectangle();
        rectangle.setWidth(STRAIGHT_SEPARATION_DISTANCE);
        rectangle.setHeight(STRAIGHT_LENGTH);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.TRANSPARENT);
    }

    private void initArcTop() {
        arcTop = new Arc();
        arcTop.setRadiusX(ARC_RADIUS);
        arcTop.setRadiusY(ARC_RADIUS);
        arcTop.setLength(180);
        arcTop.setStartAngle(0);
        arcTop.setType(ArcType.OPEN);
        arcTop.setFill(Color.WHITE);
        arcTop.setStroke(Color.BLACK);
    }

    private void initArcBottom() {
        arcBottom = new Arc();
        arcBottom.setRadiusX(ARC_RADIUS);
        arcBottom.setRadiusY(ARC_RADIUS);
        arcBottom.setLength(180);
        arcBottom.setStartAngle(180);
        arcBottom.setType(ArcType.OPEN);
        arcBottom.setFill(Color.WHITE);
        arcBottom.setStroke(Color.BLACK);
    }

    private void initLines() {
        lineStraightLeft = new Line();
        lineStraightRight = new Line();
    }

    private void initComponent() {
        component = new MUX();
    }

    private void initNodes() {
        inputA = new ComponentInputWireNode(this, "inputA");
        inputB = new ComponentInputWireNode(this, "inputB");
        sel = new ComponentInputWireNode(this, "select");
        out = new ComponentOutputWireNode(this, "output");
    }

    public void updateLoc(double x, double y) {
        changeShapeLoc(x, y);
        changeNodeLocs(x, y);
        changeTextLoc(x, y);
    }

    private void changeShapeLoc(double x, double y) {
        changeRectangleLoc(x, y);
        changeArcTopLoc(x, y);
        changeArcBottomLoc(x, y);
        changeLineStraightLeftLoc(x, y);
        changeLineStraightRightLoc(x, y);
    }

    private void changeRectangleLoc(double x, double y) {
        rectangle.setX(x);
        rectangle.setY(y);
    }

    private void changeArcTopLoc(double x, double y) {
        arcTop.setCenterX(x + (STRAIGHT_SEPARATION_DISTANCE / 2));
        arcTop.setCenterY(y);
    }

    private void changeArcBottomLoc(double x, double y) {
        arcBottom.setCenterX(x + (STRAIGHT_SEPARATION_DISTANCE / 2));
        arcBottom.setCenterY(y + STRAIGHT_LENGTH);
    }

    private void changeLineStraightLeftLoc(double x, double y) {
        lineStraightLeft.setStartX(x);
        lineStraightLeft.setStartY(y);
        lineStraightLeft.setEndX(x);
        lineStraightLeft.setEndY(y + STRAIGHT_LENGTH);
    }

    private void changeLineStraightRightLoc(double x, double y) {
        lineStraightRight.setStartX(x + STRAIGHT_SEPARATION_DISTANCE);
        lineStraightRight.setStartY(y);
        lineStraightRight.setEndX(x + STRAIGHT_SEPARATION_DISTANCE);
        lineStraightRight.setEndY(y + STRAIGHT_LENGTH);
    }

    private void changeNodeLocs(double x, double y) {
        changeInputANodeLoc(x, y);
        changeInputBNodeLoc(x, y);
        changeSelectNodeLoc(x, y);
        changeOutputNodeLoc(x, y);
    }

    private void changeInputANodeLoc(double x, double y) {
        inputA.setStartX(x);
        inputA.setStartY(y+8);
        inputA.setEndX(x-inputA.getLength());
        inputA.setEndY(y+8);
    }

    private void changeInputBNodeLoc(double x, double y) {
        inputB.setStartX(x);
        inputB.setStartY(y+32);
        inputB.setEndX(x-inputB.getLength());
        inputB.setEndY(y+32);
    }

    private void changeSelectNodeLoc(double x, double y) {
        sel.setStartX(x + (STRAIGHT_SEPARATION_DISTANCE / 2));
        sel.setStartY(y+STRAIGHT_LENGTH+ARC_RADIUS);
        sel.setEndX(x + (STRAIGHT_SEPARATION_DISTANCE / 2));
        sel.setEndY(y+STRAIGHT_LENGTH+ARC_RADIUS+sel.getLength());
    }

    private void changeOutputNodeLoc(double x, double y) {
        out.setStartX(x+STRAIGHT_SEPARATION_DISTANCE);
        out.setStartY(y+20);
        out.setEndX(x+STRAIGHT_SEPARATION_DISTANCE+out.getLength());
        out.setEndY(y+20);
    }

    public void changeTextLoc(double x, double y) {
        updateTextLoc(x - ((getText().getText().length() / 2) * NAME_X_LOC_CONSTANT), y - (ARC_RADIUS * 2));
    }

    public Shape[] getGraphics() {
        Shape[] graphics = {
                rectangle,
                arcTop,
                arcBottom,
                lineStraightLeft,
                lineStraightRight,
                inputA,
                inputB,
                sel,
                out
        };

        return graphics;
    }

    @Override
	public Text[] getValueText() {
		Text[] t = new Text[] {
				out.getValue()
		};
		return t;
	}

	@Override
	public void config() {
		DefaultConfigWindow cfg = new DefaultConfigWindow("config",this);
		cfg.showAndWait();
		changeTextLoc(rectangle.getX(), rectangle.getY());
	}

    @Override
    public void addMouseHandler() {
        rectangle.addEventHandler(MouseEvent.ANY, new MUXGraphicMouseHandler());
        arcTop.addEventHandler(MouseEvent.ANY, new MUXGraphicMouseHandler());
        arcBottom.addEventHandler(MouseEvent.ANY, new MUXGraphicMouseHandler());
    }

    private class MUXGraphicMouseHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent e) {
            if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                updateLoc(e.getX(), e.getY());
            }
            else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                if(e.getButton()==MouseButton.PRIMARY) {
                    //do we want anything here? maybe highlight it?
//                        	config();
                }
                else {
                    menu.show(rectangle, e.getX(),e.getY());
                }
            }
        }
    }

    private void createContextMenu() { //this should probably be moved to componentgraphic class
        MenuItem cfg = createConfigMenuItem();
        MenuItem del = createDeleteMenuItem();

        menu = new ContextMenu(cfg, del);
    }

    private MenuItem createConfigMenuItem() {
        MenuItem cfg = new MenuItem("Config");
        cfg.setOnAction(e -> config());

        return cfg;
    }

    private MenuItem createDeleteMenuItem() {
        MenuItem del = new MenuItem("Delete");
        del.setOnAction(e -> delete());

        return del;
    }

    private void delete() {
        removeOutputLines();
        removeFromParent();
    }

    private void removeFromParent() {
        Pane parentPane = getParentPane();
        parentPane.getChildren().removeAll(getGraphics());
        parentPane.getChildren().removeAll(getValueText());
        parentPane.getChildren().removeAll(getText());
    }

    private void removeOutputLines() {
        Pane parentPane = getParentPane();
        out.clearLines(parentPane);
    }

    private Pane getParentPane() {
        Pane parentPane;
        parentPane = ExecutionEnvironment.getExecutionEnvironment().getDataPathWindow().getPane();

        return parentPane;
    }

    @Override
	public void updateWireText() {
		out.updateText();		
	}
}
