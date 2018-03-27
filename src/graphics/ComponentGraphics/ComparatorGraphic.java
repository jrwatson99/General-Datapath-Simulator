package graphics.ComponentGraphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import logic.components.Comparator;

public class ComparatorGraphic extends ComponentGraphic {
    private Rectangle rectangle;

    private Comparator comparator;

    public Comparator getComponent() {
        return comparator;
    }

    private static final double HEIGHT = 60;
    private static final double WIDTH = 100;

    public ComparatorGraphic() {
        rectangle = new Rectangle();
        rectangle.setHeight(HEIGHT);
        rectangle.setWidth(WIDTH);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);

        comparator = new Comparator();
    }

    public void updateLoc(double x, double y) {
        rectangle.setX(x);
        rectangle.setY(y);
    }

    public Shape[] getGraphics() {
        Shape[] graphics = {rectangle};

        return graphics;
    }
}
