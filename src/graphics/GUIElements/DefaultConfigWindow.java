package graphics.GUIElements;

import graphics.ComponentGraphics.ComponentGraphic;
import javafx.scene.control.TextField;

public class DefaultConfigWindow extends ConfigWindow {
	private ComponentGraphic adderg;
	
	
	public DefaultConfigWindow(String title, ComponentGraphic adderg) {
		super(title);
		this.adderg=adderg;
		this.addInputLine(new InputLine("Name"));
		this.addInputLine(new InputLine("Bit Width"));
		
	}

	@Override
	public void updateComponent() throws Exception {
		adderg.getComponent().setBitLength(getInOutWidth());
		adderg.setName(getName());
		
	}
	

	public String getName() {
		return ((TextField)getLayout().getChildren().get(1)).getText();		
	}
	public int getInOutWidth() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(3)).getText());
	}
}
