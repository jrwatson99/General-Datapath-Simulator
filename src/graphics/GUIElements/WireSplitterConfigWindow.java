package graphics.GUIElements;

import graphics.ComponentGraphics.WireSplitterGraphic;
import javafx.scene.control.TextField;

public class WireSplitterConfigWindow extends ConfigWindow {
	
	private WireSplitterGraphic wireg;
	
	public WireSplitterConfigWindow(String title, WireSplitterGraphic wireg) {
		super(title);
		this.addInputLine(new InputLine("Input Bit Width"));
		this.addInputLine(new InputLine("Output Bit Width"));
		this.addInputLine(new InputLine("Start Bit (included)"));
		this.addInputLine(new InputLine("Stop Bit (included)"));
		this.wireg = wireg;
		
		
	}

	@Override
	public void updateComponent() throws Exception {
		wireg.getComponent().setStartBit(getStartBit());
		wireg.getComponent().setInWidth(getInWidth());
		wireg.getComponent().setOutWidth(getOutWidth());
		wireg.getComponent().setStopBit(getStopBit());
		if(getInWidth() < getOutWidth() || getStopBit() > getInWidth() || getStopBit()-getStartBit() != getOutWidth()-1 ) {
			throw new Exception("Bad input values");
		}
	}

	private int getOutWidth() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(3)).getText());
	}

	private int getInWidth() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(1)).getText());
	}

	private int getStopBit() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(7)).getText());
	}

	private int getStartBit() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(5)).getText());
	}
	
	

}
