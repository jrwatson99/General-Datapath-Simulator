package graphics.GUIElements;

import graphics.ComponentGraphics.ALUGraphic;
import javafx.scene.control.TextField;

public class ALUConfigWindow extends ConfigWindow {
	private ALUGraphic alug;
	public ALUConfigWindow(String title, ALUGraphic alug) {
		super(title);
		this.alug=alug;
		this.addInputLine(new InputLine("Name"));
		this.addInputLine(new InputLine("In/out bitwidth"));
		this.addInputLine(new InputLine("ALU-op bitwidth"));
		
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
		alug.getComponent().setOPBitLength(getALUOPWidth());
	}


}
