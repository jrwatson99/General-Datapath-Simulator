package graphics.ComponentGraphics;

import graphics.GUIElements.DefaultConfigWindow;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.Comparator;

public class ComparatorGraphic extends ComponentGraphic {
    private Rectangle rectangle;

    private Comparator comparator;

    private ComponentInputWireNode inputANode;
    private ComponentInputWireNode inputBNode;
    private ComponentOutputWireNode outputLTNode;
    private ComponentOutputWireNode outputEQNode;
    private ComponentOutputWireNode outputGTNode;

    public Comparator getComponent() {
        return comparator;
    }

    private static final double HEIGHT = 60;
    private static final double WIDTH = 100;

    public ComparatorGraphic() {
        rectangle = new Rectangle();
        rectangle.setHeight(HEIGHT);
        rectangle.setWidth(WIDTH);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        

        inputANode = new ComponentInputWireNode(this, "inputA");
        inputBNode = new ComponentInputWireNode(this, "inputB");
        outputLTNode = new ComponentOutputWireNode(this, "outputLT");
        outputEQNode = new ComponentOutputWireNode(this, "outputEQ");
        outputGTNode = new ComponentOutputWireNode(this, "outputGT");

        comparator = new Comparator();

        addMouseHandler();
    }

    public void updateLoc(double x, double y) {
    	updateTextLoc(x+5,y+15);
    	
        rectangle.setX(x);
        rectangle.setY(y);
        

        inputANode.setStartX(x - inputANode.getLength());
        inputANode.setStartY(y + 15);
        inputANode.setEndX(x);
        inputANode.setEndY(y + 15);

        inputBNode.setStartX(x - inputANode.getLength());
        inputBNode.setStartY(y + 30 );
        inputBNode.setEndX(x);
        inputBNode.setEndY(y + 30);

        outputLTNode.setStartX(x + WIDTH);
        outputLTNode.setStartY(y + 15);
        outputLTNode.setEndX(x + WIDTH + outputLTNode.getLength());
        outputLTNode.setEndY(y + 15);

        outputEQNode.setStartX(x + WIDTH);
        outputEQNode.setStartY(y + 30);
        outputEQNode.setEndX(x + WIDTH + outputEQNode.getLength());
        outputEQNode.setEndY(y + 30);

        outputGTNode.setStartX(x + WIDTH);
        outputGTNode.setStartY(y + 45);
        outputGTNode.setEndX(x + WIDTH + outputGTNode.getLength());
        outputGTNode.setEndY(y + 45);
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
       rectangle.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent e) {
               if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                   updateLoc(e.getX(), e.getY());
               }
               else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                   if(e.getButton()==MouseButton.PRIMARY) {
                   	
                   }
                   else {
                   	getMenu().show(rectangle, e.getX(),e.getY());
                   }
               }
           }
       });
	            
	}
    @Override
	public void updateWireText() {
    	outputLTNode.updateText();
    	outputGTNode.updateText();
    	outputEQNode.updateText();
	}
}
