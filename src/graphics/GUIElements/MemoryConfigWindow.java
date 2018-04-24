package graphics.GUIElements;

import graphics.ComponentGraphics.MemoryGraphic;
import graphics.GUIElements.ConfigWindow.InputLine;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.components.memories.DataMemory;
import logic.components.memories.Memory;

public class MemoryConfigWindow extends ConfigWindow{
	MemoryGraphic memg;
	CheckBox cb;
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
			this.addInputLine(new InputLine("Address Bit Width",Integer.toString(((Memory)memg.getComponent()).getAddressWidth())));
			this.addInputLine(new InputLine("Data Bit Width",Integer.toString(((Memory)memg.getComponent()).getDataWidth())));
			this.addInputLine(new InputLine("Memory Size/ Number of registers",Integer.toString(((Memory)memg.getComponent()).getSize())));
		}
		if(memg.getComponent().getClass()==DataMemory.class) {

			cb = new CheckBox();
			getLayout().addRow(4,new Label("Do you want to initialize Data Memory from file?") ,cb);
			this.addInputLine(new InputLine("Filename"));
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
		((Memory)memg.getComponent()).setAddressWidth(getAddressWidth());
		((Memory)memg.getComponent()).setDataWidth(getDataWidth());
		((Memory)memg.getComponent()).resize(getSize());
		if(memg.getComponent().getClass()==DataMemory.class && cb.isSelected()) {
			((DataMemory)memg.getComponent()).initFromFile(((TextField)getLayout().getChildren().get(11)).getText(), 10);
		}
	}
	
}
