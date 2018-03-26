package graphics.ComponentGraphics;

import javafx.scene.Node;
import javafx.scene.layout.Region;

public abstract class ComponentGraphic extends Region {
	private int xLoc;
	private int yLoc;

	public abstract void updateLoc(double x, double y);
}
