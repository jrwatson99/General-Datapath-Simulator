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

public class AdderGraphic extends ComponentGraphic {
	private Polygon shape;
    private Adder adder;
    private ContextMenu menu;
    
    private ComponentInputWireNode inputANode;
    private ComponentInputWireNode inputBNode;
    private ComponentOutputWireNode outputNode;

    private static final double BIG_DIAGONAL_LENGTH_X = 50;
    private static final double BIG_DIAGONAL_LENGTH_Y = 20;
    private static final double LITTLE_DIAGONAL_LENGTH_X = 10;
    private static final double LITTLE_DIAGONAL_LENGTH_Y = 5;
    private static final double STRAIGHT_LENGTH = 30;

    public Adder getComponent() {
        return adder;
    }

    public AdderGraphic() {
    	shape = new Polygon();
    	shape.setFill(Color.WHITE);
    	shape.setStroke(Color.BLACK);
        inputANode = new ComponentInputWireNode(this, "inputA");
        inputBNode = new ComponentInputWireNode(this, "inputB");
        outputNode = new ComponentOutputWireNode(this, "output");

        adder = new Adder();

        addMouseHandler();
        createContextMenu();
    }

    public void updateLoc(double x, double y) {
    	updateTextLoc(x + 2,y + STRAIGHT_LENGTH);
    	
    	shape.getPoints().clear();
    	shape.getPoints().addAll(new Double[] {
    	         x,y,
    	         x + BIG_DIAGONAL_LENGTH_X, y + BIG_DIAGONAL_LENGTH_Y,
    	         x + BIG_DIAGONAL_LENGTH_X, y + (BIG_DIAGONAL_LENGTH_Y + STRAIGHT_LENGTH),
    	         x, y + (2 * BIG_DIAGONAL_LENGTH_Y) + STRAIGHT_LENGTH,
    	         x, y + (STRAIGHT_LENGTH + (2 * LITTLE_DIAGONAL_LENGTH_Y)),
    	         x + LITTLE_DIAGONAL_LENGTH_X, y + (STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y),
    	         x, y + STRAIGHT_LENGTH
    	});

        inputANode.setStartX(x - inputANode.getLength());
        inputANode.setStartY(y + (STRAIGHT_LENGTH / 2));
        inputANode.setEndX(x);
        inputANode.setEndY(y + (STRAIGHT_LENGTH / 2));

        inputBNode.setStartX(x - inputANode.getLength());
        inputBNode.setStartY(y + STRAIGHT_LENGTH + (2 * LITTLE_DIAGONAL_LENGTH_Y) + (STRAIGHT_LENGTH / 2));
        inputBNode.setEndX(x);
        inputBNode.setEndY(y + STRAIGHT_LENGTH + (2 * LITTLE_DIAGONAL_LENGTH_Y) + (STRAIGHT_LENGTH / 2));

        outputNode.setStartX(x + BIG_DIAGONAL_LENGTH_X);
        outputNode.setStartY(y + STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y);
        outputNode.setEndX(x + BIG_DIAGONAL_LENGTH_X + outputNode.getLength());
        outputNode.setEndY(y + STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y);
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

//    @Override
//    public void addMouseHandler() {
//        shape.setOnMouseClicked(e -> {
//            if (e.getButton().compareTo(MouseButton.SECONDARY) == 0) {
//                this.config();
//            }
//            else if (e.getButton().compareTo(MouseButton.PRIMARY) == 0) {
//                shape.getParent().addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent e) {
//                        if (e.getEventType() == MouseEvent.MOUSE_MOVED) {
//                            updateLoc(e.getX(), e.getY());
//                        }
//                        else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
//                            shape.getParent().removeEventHandler(MouseEvent.ANY, this);
////                            e.consume();
////                            addMouseHandler();
//                        }
//                    }
//                });
//            }
//        });
//    }
	  @Override
	    public void addMouseHandler() {
            shape.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                @Override
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
                        	menu.show(shape, e.getX(),e.getY());
                        }
                    }
                }
            });
	            
	    }
    protected void createContextMenu() { //this should probably be moved to componentgraphic class
    	menu = new ContextMenu();
    	MenuItem cfg = new MenuItem("Config");
    	MenuItem del = new MenuItem("Delete");
    	cfg.setOnAction(e -> config());
    	del.setOnAction(e -> delete());
    	menu.getItems().addAll(cfg,del);
    }

	protected void delete() {
    	Pane dp;
    	dp = ExecutionEnvironment.getExecutionEnvironment().getDataPathWindow().getPane();
    	dp.getChildren().removeAll(getGraphics());
    	dp.getChildren().removeAll(getValueText());
    	dp.getChildren().removeAll(outputNode.getLines());
    	dp.getChildren().remove(getText());
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
