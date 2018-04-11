package graphics.GUIElements;

import graphics.ComponentGraphics.MemoryGraphic;
import javafx.scene.control.TextField;
import logic.DataValue;
import logic.components.memories.RegisterFile;

public class RegisterFileConfigWindow extends MemoryConfigWindow{

	public RegisterFileConfigWindow(String title, MemoryGraphic memg) {
		super(title, memg);
		addRegBoxes(memg);
	}

	private void addRegBoxes(MemoryGraphic memg) {
		for(int i=0; i<((RegisterFile)memg.getComponent()).getNumRegisters();i++) {
			this.addInputLine(new InputLine("Reg #"+i,((RegisterFile)memg.getComponent()).getReg(i).toString()));
		}
	}
	
	@Override
	public void updateComponent() throws Exception{
		RegisterFile r = ((RegisterFile)memg.getComponent());
		for(int i=0; i<r.getNumRegisters();i++) {
			r.setReg(i,getRegValue(i));
		}
		super.updateComponent();
	}

	private DataValue getRegValue(int i) {
		return new DataValue(((TextField)getLayout().getChildren().get(i*2 + 1 +8)).getText());
	}
}
