package graphics.ComponentGraphics;

import graphics.GUIElements.DefaultConfigWindow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import logic.components.BitShifter;
import logic.components.Component;

public class BitShifterGraphic extends ComponentGraphic{

    private Rectangle rectangle;
    
    private BitShifter bitShifter;

    private ComponentInputWireNode inputNode;
    private ComponentOutputWireNode outputNode;

    private static final double HEIGHT = 30;
    private static final double WIDTH = 60;
    
    public BitShifterGraphic() {
    	bitShifter = new BitShifter();
    	
        rectangle = new Rectangle();
        rectangle.setHeight(HEIGHT);
        rectangle.setWidth(WIDTH);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);

        inputNode = new ComponentInputWireNode();
        outputNode = new ComponentOutputWireNode();
    }
    
	@Override
	public void updateLoc(double x, double y) {
    	updateTextLoc(x+5,y+15);
    	
        rectangle.setX(x);
        rectangle.setY(y);
        

        inputNode.setStartX(x - inputNode.getLength());
        inputNode.setStartY(y + 15);
        inputNode.setEndX(x);
        inputNode.setEndY(y + 15);
        

        outputNode.setStartX(x + WIDTH);
        outputNode.setStartY(y + 15);
        outputNode.setEndX(x + WIDTH + outputNode.getLength());
        outputNode.setEndY(y + 15);
		
	}

	@Override
	public Shape[] getGraphics() {
		Shape[] graphics = {rectangle,
        		outputNode,
        		inputNode};
		return graphics;
	}

	@Override
	public Component getComponent() {
		return bitShifter;
	}

	@Override
	public void config() {
		DefaultConfigWindow cfg = new DefaultConfigWindow("config",this);
		cfg.showAndWait();
		
	}

}
