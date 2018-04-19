package graphics.ComponentGraphics;

import graphics.GUIElements.DefaultConfigWindow;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.BitExtender;
import logic.components.Component;

public class BitExtenderGraphic extends ComponentGraphic{
    private Rectangle rectangle;
    
    private BitExtender bitExtender;

    private ComponentInputWireNode inputNode;
    private ComponentOutputWireNode outputNode;

    private static final double HEIGHT = 30;
    private static final double WIDTH = 60;
    
    public BitExtenderGraphic() {
    	bitExtender = new BitExtender();
    	
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
		return bitExtender;
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
		outputNode.updateText();		
	}
}
