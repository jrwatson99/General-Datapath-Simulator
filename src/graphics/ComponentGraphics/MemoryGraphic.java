package graphics.ComponentGraphics;

import graphics.GUIElements.MemoryConfigWindow;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import logic.components.memories.Memory;

public abstract class MemoryGraphic extends ComponentGraphic{
	private Memory mem;
	private Rectangle rectangle;
	

    protected static final double HEIGHT = 200;
    protected static final double WIDTH = 100;

	@Override
	public Memory getComponent() {return mem;}
	
	public void setComponent(Memory newMemory) {mem=newMemory;}
	
    public MemoryGraphic() {
        setRectangle(new Rectangle());
        getRectangle().setHeight(HEIGHT);
        getRectangle().setWidth(WIDTH);
        getRectangle().setFill(Color.WHITE);
        getRectangle().setStroke(Color.BLACK);

        addMouseHandler();
    }

    public void updateLoc(double x, double y) {
        getRectangle().setX(x);
        getRectangle().setY(y);
		updateTextLoc(x+10,y+HEIGHT/2);
    }

    public Rectangle getRect() {
        return getRectangle();
    }
    public Shape[] getGraphics() {
        Shape[] graphics = {getRectangle()};

        return graphics;
    }
	@Override
	public void config() {
		MemoryConfigWindow cfg = new MemoryConfigWindow("Mem Config",this);
		cfg.showAndWait();
	}

    public abstract void config2();
    
    @Override
	public ContextMenu getMenu() {
    	ContextMenu menu = new ContextMenu();
    	MenuItem cfg = new MenuItem("Config");
    	MenuItem del = new MenuItem("Delete");
    	cfg.setOnAction(e -> config2());
    	del.setOnAction(e -> delete());
    	menu.getItems().addAll(cfg,del);
    	return menu;
	}

	 @Override
	 public void addMouseHandler() {
      getRectangle().addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
              if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                  updateLoc(e.getX(), e.getY());
              }
              else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                  if(e.getButton()==MouseButton.PRIMARY) {
                  	
                  }
                  else {
                  	getMenu().show(getRectangle(), e.getX(),e.getY());
                  }
              }
          }
      });
	            
	}
	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
}
