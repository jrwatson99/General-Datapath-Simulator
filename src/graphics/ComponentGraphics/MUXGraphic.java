package graphics.ComponentGraphics;

import graphics.GUIElements.DefaultConfigWindow;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import logic.components.MUX;

public class MUXGraphic extends ComponentGraphic {

    private Line lineStraightLeft;
    private Line lineStraightRight;
    private Arc arcTop;
    private Arc arcBottom;

    private MUX mux;

    public MUX getComponent() {
        return mux;
    }

    private static final double STRAIGHT_LENGTH = 40;
    private static final double STRAIGHT_SEPARATION_DISTANCE = 16;
    private static final double ARC_RADIUS = STRAIGHT_SEPARATION_DISTANCE / 2;

    public MUXGraphic() {
        lineStraightLeft = new Line();
        lineStraightRight = new Line();
        arcTop = new Arc();
        arcBottom = new Arc();

        arcTop.setRadiusX(ARC_RADIUS);
        arcTop.setRadiusY(ARC_RADIUS);
        arcTop.setLength(180);
        arcTop.setStartAngle(0);
        arcTop.setType(ArcType.OPEN);
        arcTop.setFill(Color.TRANSPARENT);
        arcTop.setStroke(Color.BLACK);


        arcBottom.setRadiusX(ARC_RADIUS);
        arcBottom.setRadiusY(ARC_RADIUS);
        arcBottom.setLength(180);
        arcBottom.setStartAngle(180);
        arcBottom.setType(ArcType.OPEN);
        arcBottom.setFill(Color.TRANSPARENT);
        arcBottom.setStroke(Color.BLACK);

        mux = new MUX();

        addMouseHandler();
    }

    public void updateLoc(double x, double y) {
        lineStraightLeft.setStartX(x);
        lineStraightLeft.setStartY(y);
        lineStraightLeft.setEndX(x);
        lineStraightLeft.setEndY(y + STRAIGHT_LENGTH);

        lineStraightRight.setStartX(x + STRAIGHT_SEPARATION_DISTANCE);
        lineStraightRight.setStartY(y);
        lineStraightRight.setEndX(x + STRAIGHT_SEPARATION_DISTANCE);
        lineStraightRight.setEndY(y + STRAIGHT_LENGTH);

        arcTop.setCenterX(x + (STRAIGHT_SEPARATION_DISTANCE / 2));
        arcTop.setCenterY(y);

        arcBottom.setCenterX(x + (STRAIGHT_SEPARATION_DISTANCE / 2));
        arcBottom.setCenterY(y + STRAIGHT_LENGTH);
    }

    public Shape[] getGraphics() {
        Shape[] graphics = {
                lineStraightLeft,
                lineStraightRight,
                arcTop,
                arcBottom
        };

        return graphics;
    }

	@Override
	public void config() {
		DefaultConfigWindow cfg = new DefaultConfigWindow("config",this);
		cfg.showAndWait();
		
	}

    @Override
    public void addMouseHandler() {
        lineStraightLeft.setOnMouseClicked(e -> {
            if (e.getButton().compareTo(MouseButton.SECONDARY) == 0) {
                this.config();
            }
            else if (e.getButton().compareTo(MouseButton.PRIMARY) == 0) {
                //TODO add click and drag;
            }
        });
    }
}
