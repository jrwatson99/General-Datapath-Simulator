package graphics.ComponentGraphics;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;
import logic.components.Component;

public abstract class ComponentGraphic extends Region {
	private int xLoc;
	private int yLoc;

	public abstract void updateLoc(double x, double y);

	public abstract Shape[] getGraphics();

	public abstract Component getComponent();
}
