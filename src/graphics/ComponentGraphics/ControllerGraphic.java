package graphics.ComponentGraphics;

import java.util.ArrayList;

import graphics.GUIElements.ControllerConfigWindow1;
import graphics.GUIElements.ControllerConfigWindow2;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.Component;
import logic.components.Controller;

public class ControllerGraphic extends ComponentGraphic {
	private Rectangle shape;
	private ArrayList<ComponentOutputWireNode> outputs;
	private ComponentInputWireNode input;
	private int numOutputs;
	
	private final static int WIDTH = 50;
	private final static int HEIGHT = 80;
	
	private Controller controller;
	
	public ControllerGraphic() {
		controller = new Controller();
		
		shape = new Rectangle();
		outputs = new ArrayList<ComponentOutputWireNode>();
		input = new ComponentInputWireNode(this,"input");
		shape.setWidth(WIDTH);
		shape.setHeight(HEIGHT);
		shape.setFill(Color.WHITE);
		shape.setStroke(Color.BLACK);
	}
	
	@Override
	public void updateLoc(double x, double y) {
		shape.setX(x);
		shape.setY(y);

		input.setStartY(y+HEIGHT/2);		
		input.setStartX(x);
		input.setEndY(y+HEIGHT/2);		
		input.setEndX(x-input.getLength());
	}

	@Override
	public void updateWireText() {
		for(int i=0;i<numOutputs;i++) {
			outputs.get(i).updateText();;
		}
	}

	@Override
	public Shape[] getGraphics() {
		Shape[] graphics = {
			shape,
			input
		};
		return graphics;
	}

	@Override
	public Text[] getValueText() {
		Text[] t = new Text[numOutputs];
		for(int i=0;i<numOutputs;i++) {
			t[i] = outputs.get(i).getValue();
		}
		return t;
	}

	@Override
	public Component getComponent() {
		return controller;
	}

	@Override
	public void config() {
		ControllerConfigWindow1 cfg = new ControllerConfigWindow1("config",this);
		cfg.showAndWait();
		addMouseHandler();
	}

	public void config2() {
		ControllerConfigWindow2 cfg = new ControllerConfigWindow2("config",this);
		cfg.showAndWait();
	}
	

	@Override
	public void addMouseHandler() {
		shape.setOnMouseClicked(e-> {
			if(e.getButton().compareTo(MouseButton.SECONDARY)==0) {
				this.config2();
			}
			else if(e.getButton().compareTo(MouseButton.PRIMARY)==0) {
				//TODO add click and drag;
				
			}
		});
	}

	public int getNumOutputs() {
		return numOutputs;
	}

	public void setNumOutputs(int numOutputs) {
		this.numOutputs = numOutputs;
	}

	public void createOutputNode() {
		for(int i=0;i<numOutputs;i++) {
			outputs.add(new ComponentOutputWireNode(this,"output"+i));
		}
	}

}
