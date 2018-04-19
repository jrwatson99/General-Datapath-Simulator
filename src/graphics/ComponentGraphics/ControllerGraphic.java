package graphics.ComponentGraphics;

import java.util.ArrayList;

import graphics.GUIElements.ControllerConfigWindow1;
import graphics.GUIElements.ControllerConfigWindow2;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.ExecutionEnvironment;
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
		numOutputs=0;
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
		
		for(int i=0;i<numOutputs;i++) {
			int yoff = (i+1)*HEIGHT/(numOutputs+1);
			outputs.get(i).setStartY(y+yoff);		
			outputs.get(i).setStartX(x+WIDTH);
			outputs.get(i).setEndY(y+yoff);		
			outputs.get(i).setEndX(x+outputs.get(i).getLength()+WIDTH);
		}
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
		ExecutionEnvironment.getExecutionEnvironment().getDataPathWindow().getPane().getChildren().addAll(outputs);
		updateLoc(shape.getX(),shape.getY());
		addMouseHandler();
	}

	public void config2() {
		ControllerConfigWindow2 cfg = new ControllerConfigWindow2("config",this);
		cfg.showAndWait();
	}
	
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
	 public void delete() {
		 super.delete();
		 ExecutionEnvironment.getExecutionEnvironment().getDataPathWindow().getPane().getChildren().removeAll(outputs);
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
