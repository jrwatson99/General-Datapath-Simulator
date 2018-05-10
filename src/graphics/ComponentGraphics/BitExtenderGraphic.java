package graphics.ComponentGraphics;

import graphics.GUIElements.configWindow.BitExtenderConfigWindow;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.BitExtender;
import logic.components.Component;

public class BitExtenderGraphic extends ComponentGraphic{
    private Rectangle rectangle;

    private ComponentInputWireNode inputNode;
    private ComponentOutputWireNode outputNode;

    private static final double HEIGHT = 30;
    private static final double WIDTH = 60;
    
    public BitExtenderGraphic() {
    	component = new BitExtender();
    	
        rectangle = new Rectangle();
        rectangle.setHeight(HEIGHT);
        rectangle.setWidth(WIDTH);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);

        inputNode = new ComponentInputWireNode(this, "input");
        outputNode = new ComponentOutputWireNode(this, "output");

        addMouseHandler();
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
	public Text[] getValueText() {
		Text[] t = new Text[] {
				outputNode.getValue()
		};
		return t;
	}
	@Override
	public Component getComponent() {
		return component;
	}

	@Override
	public void config() {
		BitExtenderConfigWindow cfg = new BitExtenderConfigWindow("config",this);
		cfg.showAndWait();
		
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
    @Override
	public void updateWireText() {
		outputNode.updateText();		
	}
}
