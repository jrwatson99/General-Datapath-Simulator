package graphics.ComponentGraphics;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.ExecutionEnvironment;
import logic.components.Component;

public abstract class ComponentGraphic {
	private Text name;

    private ContextMenu menu;
    
    public ContextMenu getMenu() {return menu;}
	public abstract void updateLoc(double x, double y);
	
	public void updateTextLoc(double x, double y) {
		name.setX(x);
		name.setY(y);
	}
	public abstract void updateWireText();
	public ComponentGraphic() {
		this.name=new Text();

        createContextMenu();
	}

	public abstract Shape[] getGraphics();
	
	public abstract Text[] getValueText();

	public abstract Component getComponent();

	public String getName() {
		return name.getText();
	}
	
	public Text getText() {return name;}
	
	public void setName(String name) {
		this.name.setText(name);
	}

	public abstract void config();

	public abstract void addMouseHandler();

	protected void delete() {
    	Pane dp;
    	dp = ExecutionEnvironment.getExecutionEnvironment().getDataPathWindow().getPane();
    	dp.getChildren().removeAll(getGraphics());
    	dp.getChildren().removeAll(getValueText());
    	dp.getChildren().remove(getText());
    	for(Shape i : getGraphics()) {
    		if(i.getClass() == ComponentOutputWireNode.class) {
    			dp.getChildren().removeAll(((ComponentOutputWireNode)i).getLines());
    		}
    	}
    }
    protected void createContextMenu() { //this should probably be moved to componentgraphic class
    	menu = new ContextMenu();
    	MenuItem cfg = new MenuItem("Config");
    	MenuItem del = new MenuItem("Delete");
    	cfg.setOnAction(e -> config());
    	del.setOnAction(e -> delete());
    	menu.getItems().addAll(cfg,del);
    }

}
