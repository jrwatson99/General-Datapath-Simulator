package graphics.ComponentGraphics;

import graphics.GUIElements.configWindow.DefaultConfigWindow;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import logic.components.Component;
import logic.components.MUX;

public class MUXGraphic extends ComponentGraphic {

    private Line lineStraightLeft;
    private Line lineStraightRight;
    private Arc arcTop;
    private Arc arcBottom;

    public Component getComponent() {
        return component;
    }

    private static final double STRAIGHT_LENGTH = 40;
    private static final double STRAIGHT_SEPARATION_DISTANCE = 16;
    private static final double ARC_RADIUS = STRAIGHT_SEPARATION_DISTANCE / 2;

    private ComponentInputWireNode inputA;
    private ComponentInputWireNode inputB;
    private ComponentInputWireNode sel;
    private ComponentOutputWireNode out;
    
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

        component = new MUX();

        inputA = new ComponentInputWireNode(this, "inputA");
        inputB = new ComponentInputWireNode(this, "inputB");
        sel = new ComponentInputWireNode(this, "select");
        out = new ComponentOutputWireNode(this, "output");
        
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
        
        inputA.setStartX(x);
        inputA.setStartY(y+8);
        inputA.setEndX(x-inputA.getLength());
        inputA.setEndY(y+8);
        
        inputB.setStartX(x);
        inputB.setStartY(y+32);
        inputB.setEndX(x-inputB.getLength());
        inputB.setEndY(y+32);
        
        sel.setStartX(x + (STRAIGHT_SEPARATION_DISTANCE / 2));
        sel.setStartY(y+STRAIGHT_LENGTH+ARC_RADIUS);
        sel.setEndX(x + (STRAIGHT_SEPARATION_DISTANCE / 2));
        sel.setEndY(y+STRAIGHT_LENGTH+ARC_RADIUS+sel.getLength());
        
        out.setStartX(x+STRAIGHT_SEPARATION_DISTANCE);
        out.setStartY(y+20);
        out.setEndX(x+STRAIGHT_SEPARATION_DISTANCE+out.getLength());
        out.setEndY(y+20);
        
    }

    public Shape[] getGraphics() {
        Shape[] graphics = {
                lineStraightLeft,
                lineStraightRight,
                arcTop,
                arcBottom,
                inputA,
                inputB,
                sel,
                out
        };

        return graphics;
    }

    @Override
	public Text[] getValueText() {
		Text[] t = new Text[] {
				out.getValue()
		};
		return t;
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
    @Override
	public void updateWireText() {
		out.updateText();		
	}
}
