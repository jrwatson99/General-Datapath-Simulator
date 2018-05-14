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
import logic.DataValue;
import logic.components.Component;
import logic.components.ConstantValue;

public class ConstantValueGraphic extends ComponentGraphic{

    private static final double WIDTH = 30;
    private static final double HEIGHT = 30;

	private Rectangle rectangle;
	private ContextMenu menu;
	
	private ComponentOutputWireNode outputNode;
    
    protected Rectangle getRect() {return rectangle;}
    public ComponentOutputWireNode getOutput() {return outputNode;}
    
	public ConstantValueGraphic() {
		init();
		menu = createContextMenu();
		addMouseHandler();
	}

	private void init() {
        initShape();
        initNode();
        initComponent();
    }

    private void initShape() {
        rectangle = new Rectangle();
        rectangle.setWidth(WIDTH);
        rectangle.setHeight(HEIGHT);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
    }

    private void initNode() {
        outputNode = new ComponentOutputWireNode(this, "output");
    }

    private void initComponent() {
        component = new ConstantValue();
    }
	
	@Override
	public void updateLoc(double x, double y) {
        changeShapeLoc(x, y);
        changeNodeLoc(x, y);
        changeTextLoc(x, y);
	}

	private void changeShapeLoc(double x, double y) {
        rectangle.setX(x);
        rectangle.setY(y);
    }

    private void changeNodeLoc(double x, double y) {
        outputNode.setStartX(x + WIDTH);
        outputNode.setStartY(y + 15);
        outputNode.setEndX(x + WIDTH + outputNode.getLength());
        outputNode.setEndY(y + 15);
    }

    private void changeTextLoc(double x, double y) {
        this.updateTextLoc(x+5, y+15);
    }

	@Override
	public Shape[] getGraphics() {
		Shape[] graphics = new Shape[]  {
				rectangle,
				outputNode
		};
		return graphics;
	}

	@Override
	public Component getComponent() {
		return component;
	}

	@Override
	public void config() {
		try {
		    tryConfig();
        }
        catch (Exception e) {
		    System.out.println(e.getMessage());
        }
	}

	private void tryConfig() throws Exception {
        if (component instanceof ConstantValue) {
            DefaultConfigWindow cfg = new DefaultConfigWindow("config", this);
            cfg.showAndWait();
            ((ConstantValue) component).setValue(new DataValue(cfg.getName()));
            ((ConstantValue) component).Update();
        }
        else {
            throw new Exception("ConstantValue graphic component reference is not of type ConstantValue!");
        }
    }

	@Override
	public void addMouseHandler() {
		rectangle.addEventHandler(MouseEvent.ANY, new ComponentGraphicMouseHandler(menu, getGraphics()));
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

	@Override
	protected void removeOutputLines() {
        Pane parentPane = getParentPane();
        outputNode.clearLines(parentPane);
	}
}
