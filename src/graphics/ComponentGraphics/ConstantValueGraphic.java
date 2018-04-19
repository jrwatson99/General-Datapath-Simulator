package graphics.ComponentGraphics;

import graphics.GUIElements.DefaultConfigWindow;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.DataValue;
import logic.components.Component;
import logic.components.ConstantValue;

public class ConstantValueGraphic extends ComponentGraphic{
	private Rectangle rectangle;
	
	private ComponentOutputWireNode outputNode;

    private static final double WIDTH = 30;
    private static final double HEIGHT = 30;
    
    private ConstantValue val;
    
    public Rectangle getRect() {return rectangle;}
    public ComponentOutputWireNode getOutput() {return outputNode;}
    
	public ConstantValueGraphic() {
		rectangle = new Rectangle();
		rectangle.setWidth(WIDTH);
		rectangle.setHeight(HEIGHT);
		rectangle.setFill(Color.WHITE);
		rectangle.setStroke(Color.BLACK);
		outputNode = new ComponentOutputWireNode(this, "output");
		
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
		val.Update();
		updateWireText();
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
