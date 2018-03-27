package graphics.ComponentGraphics;


import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import logic.components.Adder;

public class AdderGraphic extends ComponentGraphic {
    private Line lineDiagonalTopRight;
    private Line lineDiagonalBottomRight;
    private Line lineDiagonalTopLeft;
    private Line lineDiagonalBottomLeft;
    private Line lineStraightTop;
    private Line lineStraightBottom;
    private Line lineStraightEnd;

    private Adder adder;

    private ComponentInputWireNode inputANode;
    private ComponentInputWireNode inputBNode;
    private ComponentOutputWireNode outputNode;

    private static final double BIG_DIAGONAL_LENGTH_X = 40;
    private static final double BIG_DIAGONAL_LENGTH_Y = 20;
    private static final double LITTLE_DIAGONAL_LENGTH_X = 10;
    private static final double LITTLE_DIAGONAL_LENGTH_Y = 5;
    private static final double STRAIGHT_LENGTH = 30;

    public Adder getComponent() {
        return adder;
    }

    public AdderGraphic() {
        lineDiagonalTopRight = new Line();
        lineDiagonalBottomRight = new Line();
        lineDiagonalTopLeft = new Line();
        lineDiagonalBottomLeft = new Line();
        lineStraightTop = new Line();
        lineStraightBottom = new Line();
        lineStraightEnd = new Line();

        inputANode = new ComponentInputWireNode();
        inputBNode = new ComponentInputWireNode();
        outputNode = new ComponentOutputWireNode();

        adder = new Adder();
    }

    public void updateLoc(double x, double y) {
        lineDiagonalTopRight.setStartX(x);
        lineDiagonalTopRight.setStartY(y);
        lineDiagonalTopRight.setEndX(x + BIG_DIAGONAL_LENGTH_X);
        lineDiagonalTopRight.setEndY(y + BIG_DIAGONAL_LENGTH_Y);

        lineDiagonalBottomRight.setStartX(x);
        lineDiagonalBottomRight.setStartY(y + (2 * BIG_DIAGONAL_LENGTH_Y) + STRAIGHT_LENGTH);
        lineDiagonalBottomRight.setEndX(x + BIG_DIAGONAL_LENGTH_X);
        lineDiagonalBottomRight.setEndY(y + BIG_DIAGONAL_LENGTH_Y + STRAIGHT_LENGTH);

        lineDiagonalTopLeft.setStartX(x);
        lineDiagonalTopLeft.setStartY(y + STRAIGHT_LENGTH);
        lineDiagonalTopLeft.setEndX(x + LITTLE_DIAGONAL_LENGTH_X);
        lineDiagonalTopLeft.setEndY(y + (STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y));

        lineDiagonalBottomLeft.setStartX(x);
        lineDiagonalBottomLeft.setStartY(y + (STRAIGHT_LENGTH + (2 * LITTLE_DIAGONAL_LENGTH_Y)));
        lineDiagonalBottomLeft.setEndX(x + LITTLE_DIAGONAL_LENGTH_X);
        lineDiagonalBottomLeft.setEndY(y + (STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y));

        lineStraightTop.setStartX(x);
        lineStraightTop.setStartY(y);
        lineStraightTop.setEndX(x);
        lineStraightTop.setEndY(y + STRAIGHT_LENGTH);

        lineStraightBottom.setStartX(x);
        lineStraightBottom.setStartY(y + (STRAIGHT_LENGTH + (2 * LITTLE_DIAGONAL_LENGTH_Y)));
        lineStraightBottom.setEndX(x);
        lineStraightBottom.setEndY(y + (2 * (STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y)));

        lineStraightEnd.setStartX(x + BIG_DIAGONAL_LENGTH_X);
        lineStraightEnd.setStartY(y + BIG_DIAGONAL_LENGTH_Y);
        lineStraightEnd.setEndX(x + BIG_DIAGONAL_LENGTH_X);
        lineStraightEnd.setEndY(y + (BIG_DIAGONAL_LENGTH_Y + STRAIGHT_LENGTH));

        inputANode.setStartX(x - inputANode.getLength());
        inputANode.setStartY(y + (STRAIGHT_LENGTH / 2));
        inputANode.setEndX(x);
        inputANode.setEndY(y + (STRAIGHT_LENGTH / 2));

        inputBNode.setStartX(x - inputANode.getLength());
        inputBNode.setStartY(y + STRAIGHT_LENGTH + (2 * LITTLE_DIAGONAL_LENGTH_Y) + (STRAIGHT_LENGTH / 2));
        inputBNode.setEndX(x);
        inputBNode.setEndY(y + STRAIGHT_LENGTH + (2 * LITTLE_DIAGONAL_LENGTH_Y) + (STRAIGHT_LENGTH / 2));

        outputNode.setStartX(x + BIG_DIAGONAL_LENGTH_X);
        outputNode.setStartY(y + STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y);
        outputNode.setEndX(x + BIG_DIAGONAL_LENGTH_X + outputNode.getLength());
        outputNode.setEndY(y + STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y);
    }

    public Shape[] getGraphics() {
        Shape[] graphics = {
                lineDiagonalTopRight,
                lineDiagonalBottomRight,
                lineDiagonalTopLeft,
                lineDiagonalBottomLeft,
                lineStraightTop,
                lineStraightBottom,
                lineStraightEnd,
                inputANode,
                inputBNode,
                outputNode};

        return graphics;
    }
}
