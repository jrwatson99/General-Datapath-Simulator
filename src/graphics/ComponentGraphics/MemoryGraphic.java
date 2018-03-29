package graphics.ComponentGraphics;

import graphics.GUIElements.MemoryConfigWindow;
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
        rectangle = new Rectangle();
        rectangle.setHeight(HEIGHT);
        rectangle.setWidth(WIDTH);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
    }

    public void updateLoc(double x, double y) {
        rectangle.setX(x);
        rectangle.setY(y);
		updateTextLoc(x+10,y+HEIGHT/2);
    }

    public Rectangle getRect() {
        return rectangle;
    }
    public Shape[] getGraphics() {
        Shape[] graphics = {rectangle};

        return graphics;
    }
	@Override
	public void config() {
		MemoryConfigWindow cfg = new MemoryConfigWindow("Mem Config",this);
		cfg.showAndWait();
		
	}


}
