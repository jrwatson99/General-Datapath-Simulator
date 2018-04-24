package graphics.ComponentGraphics;

import java.util.ArrayList;

import graphics.GUIElements.RegisterConfigWindow;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.ExecutionEnvironment;
import logic.components.Component;
import logic.components.Register;

public class RegisterGraphic extends ComponentGraphic{
	private Rectangle shape;
	private ArrayList<ComponentOutputWireNode> outputs;
	private ArrayList<ComponentInputWireNode> inputs;
	private int numConnections;
	
	private Register reg;
	
	private int HEIGHT; //semi-static... may be changed to expand register size.
	private final static int WIDTH = 60;
	
	public void setHeight(int newHeight) {HEIGHT = newHeight;}
	public int getHeight() {return HEIGHT;}
	
	public RegisterGraphic() {
		HEIGHT = 80;
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
		shape.setX(x);
		shape.setY(y);
		
		for(int i=0;i<numConnections;i++) {
			int yoff = (i+1)*HEIGHT/(numConnections+1);
			outputs.get(i).setStartY(y+yoff);		
			outputs.get(i).setStartX(x+WIDTH);
			outputs.get(i).setEndY(y+yoff);		
			outputs.get(i).setEndX(x+outputs.get(i).getLength()+WIDTH);
			
			inputs.get(i).setStartY(y+yoff);		
			inputs.get(i).setStartX(x);
			inputs.get(i).setEndY(y+yoff);		
			inputs.get(i).setEndX(x-inputs.get(i).getLength());
		}
		
	}

	@Override
	public Shape[] getGraphics() {
		Shape[] t = {shape};
		return t;  //inputs and outputs are added in config
	}

	@Override
	public Component getComponent() {
		return reg;
	}

	@Override
	public void config() {
		RegisterConfigWindow cfg = new RegisterConfigWindow("config",this);
		cfg.showAndWait();
		ExecutionEnvironment.getExecutionEnvironment().getDataPathWindow().getPane().getChildren().addAll(outputs);
		ExecutionEnvironment.getExecutionEnvironment().getDataPathWindow().getPane().getChildren().addAll(inputs);
		updateLoc(shape.getX(),shape.getY());
		addMouseHandler();
	}

	@Override
	public void addMouseHandler() {
		shape.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	          @Override
	          public void handle(MouseEvent e) {
	              if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
	                  updateLoc(e.getX(), e.getY());
	              }
	              else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
	                  if(e.getButton()==MouseButton.PRIMARY) {
	                  	
	                  }
	                  else {
	                  	getMenu().show(shape, e.getX(),e.getY());
	                  }
	              }
	          }
	      });
	} 
	@Override
	public Text[] getValueText() {
		Text[] t = new Text[numConnections];
		for(int i=0;i<numConnections;i++) {
			t[i] = outputs.get(i).getValue();
		}
		return t;
	}

	@Override
	public void updateWireText() {
		for(int i=0;i<numConnections;i++) {
			outputs.get(i).updateText();;
		}
	}
	 @Override
	 public void delete() {
		 super.delete();
		 ExecutionEnvironment.getExecutionEnvironment().getDataPathWindow().getPane().getChildren().removeAll(outputs);
		 ExecutionEnvironment.getExecutionEnvironment().getDataPathWindow().getPane().getChildren().removeAll(inputs);
	 }

}
