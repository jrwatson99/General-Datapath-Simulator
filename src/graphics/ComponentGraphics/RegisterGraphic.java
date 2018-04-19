package graphics.ComponentGraphics;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.Component;
import logic.components.Register;

public class RegisterGraphic extends ComponentGraphic{
	private Rectangle shape;
	private ArrayList<ComponentOutputWireNode> outputs;
	private ArrayList<ComponentInputWireNode> inputs;
	private int numConnections;
	
	private Register reg;
	
	private int HEIGHT; //semi-static... may be changed to expand register size.
	private final static int WIDTH = 30;
	
	public void setHeight(int newHeight) {HEIGHT = newHeight;}
	public int getHeight() {return HEIGHT;}
	
	public RegisterGraphic() {
		HEIGHT = 70;
		reg = new Register();
		
		outputs = new ArrayList<ComponentOutputWireNode>();
		inputs = new ArrayList<ComponentInputWireNode>();
		
		shape = new Rectangle();
		shape.setWidth(WIDTH);
		shape.setHeight(HEIGHT);
		shape.setFill(Color.WHITE);
		shape.setStroke(Color.BLACK);
		
	}
	
	@Override
	public void updateLoc(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Shape[] getGraphics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void config() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMouseHandler() {
		// TODO Auto-generated method stub
		
	} 
	@Override
	public Text[] getValueText() {
		Text[] t = new Text[] {
//				readData1.getValue(),
//				readData1.getValue()
		};
		return t;
	}

	@Override
	public void updateWireText() {
		// TODO Auto-generated method stub
		
	}

}
