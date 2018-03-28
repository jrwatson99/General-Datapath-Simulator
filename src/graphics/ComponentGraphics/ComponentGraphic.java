package graphics.ComponentGraphics;

import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;
import logic.components.Component;

public abstract class ComponentGraphic extends Region {
	private int xLoc;
	private int yLoc;
	private String name;
	
	public abstract void updateLoc(double x, double y);

	public abstract Shape[] getGraphics();

	public abstract Component getComponent();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract void config() ;
}
