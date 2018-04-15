package graphics.ComponentGraphics;

import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.components.Component;

public abstract class ComponentGraphic {
	private Text name;
	
	public abstract void updateLoc(double x, double y);
	
	public void updateTextLoc(double x, double y) {
		name.setX(x);
		name.setY(y);
	}
	public abstract void updateWireText();
	public ComponentGraphic() {
		this.name=new Text();
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
}
