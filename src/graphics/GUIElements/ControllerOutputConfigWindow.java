package graphics.GUIElements;

import java.util.ArrayList;

import graphics.ComponentGraphics.ControllerGraphic;
import javafx.scene.control.TextField;
import logic.DataValue;
import logic.components.Controller;

public class ControllerOutputConfigWindow extends ConfigWindow {
	ControllerGraphic g;
	int start,stop,i;
	public ControllerOutputConfigWindow(String title,int i, int startBit,int stopBit, ControllerGraphic g) {
		super(title);
		this.g = g;
		int size = (int) Math.pow(2, startBit-stopBit+1);
		((Controller)this.g.getComponent()).initValuesArray(i,size);
		for(int j=0;j< size;j++) {
			this.addInputLine(new InputLine(Integer.toString(j, 2)));
			((TextField)getLayout().getChildren().get(j*2 +1)).setText(((Controller)g.getComponent()).getOutputValues(i).get(j).toString());
		}
		this.start = startBit;
		this.stop = stopBit;
		this.i=i;
	}

	@Override
	public void updateComponent() throws Exception {
		ArrayList<DataValue> values = new ArrayList<DataValue>();
		for(int j=0;j< Math.pow(2, start-stop+1);j++) {
			values.add(new DataValue(getValue(j),2));
		}
		((Controller)g.getComponent()).addValues(i,values);
	}

	private String getValue(int j) {
		return ((TextField)getLayout().getChildren().get(j*2 +1)).getText();
	}

}
