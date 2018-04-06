package graphics.ComponentGraphics;

import graphics.GUIElements.DefaultConfigWindow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import logic.DataValue;
import logic.components.Component;
import logic.components.ConstantValue;

public class ConstantValueGraphic extends ComponentGraphic{
	private Rectangle rect;
	
	private ComponentOutputWireNode outputNode;

    private static final double WIDTH = 30;
    private static final double HEIGHT = 30;
    
    private ConstantValue val;
    
	public ConstantValueGraphic() {
		rect = new Rectangle();
		rect.setWidth(WIDTH);
		rect.setHeight(HEIGHT);
		rect.setFill(Color.WHITE);
		rect.setStroke(Color.BLACK);
		outputNode = new ComponentOutputWireNode();
		
		val = new ConstantValue();
	}
	
	@Override
	public void updateLoc(double x, double y) {
		this.updateTextLoc(x+5, y+15);
		
		rect.setX(x);
		rect.setY(y);
		

        outputNode.setStartX(x + WIDTH);
        outputNode.setStartY(y + 15);
        outputNode.setEndX(x + WIDTH + outputNode.getLength());
        outputNode.setEndY(y + 15);
		
		
	}

	@Override
	public Shape[] getGraphics() {
		Shape[] graphics = new Shape[]  {
				rect,
				outputNode
		};
		return graphics;
	}

	@Override
	public Component getComponent() {
		return val;
	}

	@Override
	public void config() {
		DefaultConfigWindow cfg = new DefaultConfigWindow("config",this);
		cfg.showAndWait();	
		val.setValue(new DataValue(cfg.getName()));
	}

}
