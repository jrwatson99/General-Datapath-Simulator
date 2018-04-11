package graphics.GUIElements;

import graphics.ComponentGraphics.MemoryGraphic;
import javafx.scene.control.TextField;

public class MemoryConfigWindow extends ConfigWindow{
	MemoryGraphic memg;
	public MemoryConfigWindow(String title, MemoryGraphic memg) {
		super(title);
		if(memg.getName()=="") {
			this.addInputLine(new InputLine("Name"));
			this.addInputLine(new InputLine("Address Bit Width"));
			this.addInputLine(new InputLine("Data Bit Width"));
			this.addInputLine(new InputLine("Memory Size/ Number of registers"));
		}
		else {
			this.addInputLine(new InputLine("Name",memg.getName()));
			this.addInputLine(new InputLine("Address Bit Width",Integer.toString(memg.getComponent().getAddressWidth())));
			this.addInputLine(new InputLine("Data Bit Width",Integer.toString(memg.getComponent().getDataWidth())));
			this.addInputLine(new InputLine("Memory Size/ Number of registers",Integer.toString(memg.getComponent().getSize())));
		}
		this.memg=memg;
	}
	
	public String getName() {
		return ((TextField)getLayout().getChildren().get(1)).getText();		
	}
	public int getAddressWidth() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(3)).getText());
	}
	public int getDataWidth() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(5)).getText());
	}
	public int getSize() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(7)).getText());
	}


	@Override
	public void updateComponent() throws Exception {
		memg.setName(getName());
		if(Math.pow(2, getAddressWidth()) < getSize()) {
			throw new Exception(" Address too small");
		}
		memg.getComponent().setAddressWidth(getAddressWidth());
		memg.getComponent().setDataWidth(getDataWidth());
		memg.getComponent().resize(getSize());
		
	}
	
}
