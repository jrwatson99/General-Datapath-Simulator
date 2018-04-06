package graphics.ComponentGraphics;

import graphics.GUIElements.DefaultConfigWindow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        

        inputANode = new ComponentInputWireNode();
        inputBNode = new ComponentInputWireNode();
        outputLTNode = new ComponentOutputWireNode();
        outputEQNode = new ComponentOutputWireNode();
        outputGTNode = new ComponentOutputWireNode();

        comparator = new Comparator();
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
	public void config() {
		DefaultConfigWindow cfg = new DefaultConfigWindow("config",this);
		cfg.showAndWait();		
	}
}
