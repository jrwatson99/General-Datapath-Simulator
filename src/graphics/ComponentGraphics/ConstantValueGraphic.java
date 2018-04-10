package graphics.ComponentGraphics;

import graphics.GUIElements.DefaultConfigWindow;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import logic.DataValue;
import logic.components.Component;
import logic.components.ConstantValue;

public class ConstantValueGraphic extends ComponentGraphic{
	private Rectangle rectangle;
	
	private ComponentOutputWireNode outputNode;

    private static final double WIDTH = 30;
    private static final double HEIGHT = 30;
    
    private ConstantValue val;
    
	public ConstantValueGraphic() {
		rectangle = new Rectangle();
		rectangle.setWidth(WIDTH);
		rectangle.setHeight(HEIGHT);
		rectangle.setFill(Color.WHITE);
		rectangle.setStroke(Color.BLACK);
		outputNode = new ComponentOutputWireNode(this);
		
		val = new ConstantValue();

		addMouseHandler();
	}
	
	@Override
	public void updateLoc(double x, double y) {
		this.updateTextLoc(x+5, y+15);
		
		rectangle.setX(x);
		rectangle.setY(y);
		

        outputNode.setStartX(x + WIDTH);
        outputNode.setStartY(y + 15);
        outputNode.setEndX(x + WIDTH + outputNode.getLength());
        outputNode.setEndY(y + 15);
		
		
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
		return val;
	}

	@Override
	public void config() {
		DefaultConfigWindow cfg = new DefaultConfigWindow("config",this);
		cfg.showAndWait();	
		val.setValue(new DataValue(cfg.getName()));
	}

	@Override
	public void addMouseHandler() {
		rectangle.setOnMouseClicked(e -> {
			if (e.getButton().compareTo(MouseButton.SECONDARY) == 0) {
				this.config();
			}
			else if (e.getButton().compareTo(MouseButton.PRIMARY) == 0) {
				//TODO add click and drag;
			}
		});
	}
}
