package graphics.ComponentGraphics;

import graphics.GUIElements.ALUConfigWindow;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import logic.components.ALU;

public class ALUGraphic extends ComponentGraphic {

	private Polygon shape;
    private ALU alu;
    

    private ComponentInputWireNode inputANode;
    private ComponentInputWireNode aluOpNode;
    private ComponentInputWireNode inputBNode;
    private ComponentOutputWireNode outputNode;

    private static final double BIG_DIAGONAL_LENGTH_X = 50;
    private static final double BIG_DIAGONAL_LENGTH_Y = 20;
    private static final double LITTLE_DIAGONAL_LENGTH_X = 10;
    private static final double LITTLE_DIAGONAL_LENGTH_Y = 5;
    private static final double STRAIGHT_LENGTH = 30;

    public ALU getComponent() {
        return alu;
    }

    public ALUGraphic() {

    	shape = new Polygon();
    	shape.setFill(Color.WHITE);
    	shape.setStroke(Color.BLACK);
        aluOpNode = new ComponentInputWireNode(this);
        inputANode = new ComponentInputWireNode(this);
        inputBNode = new ComponentInputWireNode(this);
        outputNode = new ComponentOutputWireNode(this);
        alu = new ALU();
        addMouseHandler();
    }

    public void updateLoc(double x, double y) {
    	updateTextLoc(x+LITTLE_DIAGONAL_LENGTH_X, y+STRAIGHT_LENGTH+LITTLE_DIAGONAL_LENGTH_Y );
    	
    	shape.getPoints().clear();
    	shape.getPoints().addAll(new Double[] {
    	         x,y,
    	         x + BIG_DIAGONAL_LENGTH_X, y + BIG_DIAGONAL_LENGTH_Y,
    	         x + BIG_DIAGONAL_LENGTH_X, y + (BIG_DIAGONAL_LENGTH_Y + STRAIGHT_LENGTH),
    	         x, y + (2 * BIG_DIAGONAL_LENGTH_Y) + STRAIGHT_LENGTH,
    	         x, y + (STRAIGHT_LENGTH + (2 * LITTLE_DIAGONAL_LENGTH_Y)),
    	         x + LITTLE_DIAGONAL_LENGTH_X, y + (STRAIGHT_LENGTH + LITTLE_DIAGONAL_LENGTH_Y),
    	         x, y + STRAIGHT_LENGTH
    	});

    	aluOpNode.setStartX(x+STRAIGHT_LENGTH);
    	aluOpNode.setStartY(y + 5);
        aluOpNode.setEndX(x+STRAIGHT_LENGTH);
        aluOpNode.setEndY(y + (STRAIGHT_LENGTH / 2)-5);
        
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
        		shape,
        		aluOpNode,
                inputANode,
                inputBNode,
                outputNode};

        return graphics;
    }

	@Override
	public void config() {
		ALUConfigWindow cfg = new ALUConfigWindow("ALU Configuration", this);
		cfg.showAndWait();
		
	}

	@Override
	public void addMouseHandler() {shape.setOnMouseClicked(e-> {
			if(e.getButton().compareTo(MouseButton.SECONDARY)==0) {
				this.config();
			}
			else if(e.getButton().compareTo(MouseButton.PRIMARY)==0) {
				//TODO add click and drag;
			}
		});
	}
}
