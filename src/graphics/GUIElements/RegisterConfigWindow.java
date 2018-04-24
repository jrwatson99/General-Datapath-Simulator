package graphics.GUIElements;

import graphics.ComponentGraphics.RegisterGraphic;
import graphics.GUIElements.ConfigWindow.InputLine;

public class RegisterConfigWindow extends ConfigWindow {

	private RegisterGraphic rg;
	
	public RegisterConfigWindow(String title, RegisterGraphic rg) {
		super(title);
		this.rg=rg;

		this.addInputLine(new InputLine("Name"));
		this.addInputLine(new InputLine("Number of outputs"));
		
	}

	@Override
	public void updateComponent() throws Exception {
		// TODO Auto-generated method stub

	}

}
