package graphics.GUIElements;

import graphics.ComponentGraphics.ALUGraphic;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.OpOrderList;
import logic.components.ALU;

import java.util.ArrayList;

public class ALUConfigWindow extends ConfigWindow {
    private ArrayList<OperationLine> operationLines;
	private ALUGraphic alug;
	public ALUConfigWindow(String title, ALUGraphic alug) {
		super(title);
		this.alug=alug;

		this.operationLines = new ArrayList<OperationLine>();

		this.addInputLine(new InputLine("Name"));
		this.addInputLine(new InputLine("In/out bitwidth"));
		this.addInputLine(new InputLine("ALU-op bitwidth"));
		this.addOperationLine(ALU.Operation.ADD, "Add");
		this.addOperationLine(ALU.Operation.SUBTRACT, "Subtract");
		this.addOperationLine(ALU.Operation.MULTIPLY, "Multiply");
		this.addOperationLine(ALU.Operation.SLL, "Shift left logical");
		this.addOperationLine(ALU.Operation.SRL, "Shift right logical");
	}
	
	public String getName() {
		return ((TextField)getLayout().getChildren().get(1)).getText();		
	}
	public int getInOutWidth() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(3)).getText());
	}
	public int getALUOPWidth() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(5)).getText());
	}

	@Override
	public void updateComponent() throws Exception {
		alug.setName(getName());
		alug.getComponent().setBitLength(getInOutWidth());
		((ALU)alug.getComponent()).setOPBitLength(getALUOPWidth());

        OpOrderList opOrderList = new OpOrderList();

        for (int i = 0; i < operationLines.size(); i++) {
            if (operationLines.get(i).isActivated()) {
                opOrderList.addOpOrderObject(operationLines.get(i).getOperationType(), operationLines.get(i).getText());
            }
        }
        alug.setOpOrder(opOrderList.createALUOpOrder(getALUOPWidth()));
	}

	private void addOperationLine(ALU.Operation operation, String operationName) {
	    OperationLine operationLine = new OperationLine(operation, operationName);
	    operationLines.add(operationLine);
		layout.addRow(layout.getChildren().size()/2, operationLine.getName(), operationLine.getActivatedCheckBox(), operationLine.getTextField());
	}

	protected class OperationLine {
		private Label name;
		private TextField text;
		private CheckBox activatedCheckBox;
		private ALU.Operation operationType;

		public OperationLine(ALU.Operation operation, String name) {
		    operationType = operation;
			this.name = new Label(name+": ");
			activatedCheckBox = new CheckBox();
			text = new TextField();
			text.setPromptText("Enter opCode for " + name);
		}

		public Label getName() {
			return name;
		}

		public void setName(Label name) {
			this.name = name;
		}

		public TextField getTextField() { return text;}

		public String getText() {
			return text.getText();
		}

		public void setText(TextField text) {
			this.text = text;
		}

		public CheckBox getActivatedCheckBox() { return activatedCheckBox;}

		public ALU.Operation getOperationType() { return operationType;}

		public boolean isActivated() {
			return activatedCheckBox.isSelected();
		}
	}
}
