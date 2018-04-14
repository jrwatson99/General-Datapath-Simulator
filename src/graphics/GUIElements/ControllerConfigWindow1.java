package graphics.GUIElements;

import graphics.ComponentGraphics.ControllerGraphic;
import javafx.scene.control.TextField;
import logic.components.Controller;

public class ControllerConfigWindow1 extends ConfigWindow {
	private ControllerGraphic controlg;
	public ControllerGraphic getConrtollerGraphic() {return controlg;}
	public ControllerConfigWindow1(String title, ControllerGraphic g) {
		super(title);
		this.addInputLine(new InputLine("Name"));
		this.addInputLine(new InputLine("Input Bit Width"));
		this.addInputLine(new InputLine("Number of outputs"));
		controlg = g;
	}

	@Override
	public void updateComponent() throws Exception {
		controlg.setNumOutputs(getNumOutputs());
		((Controller)controlg.getComponent()).setNumOutputs(getNumOutputs());
		((Controller)controlg.getComponent()).setInputWidth(geInputWidth());
		((Controller)controlg.getComponent()).initArrayLists();
		controlg.setName(getName());
		controlg.createOutputNode();
		
	}

	private String getName() {
		return ((TextField)getLayout().getChildren().get(1)).getText();		
	}

	private int geInputWidth() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(3)).getText());
	}

	private int getNumOutputs() {
		return Integer.parseInt(((TextField)getLayout().getChildren().get(5)).getText());
	}

}
