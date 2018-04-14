package graphics.GUIElements;

import java.util.ArrayList;

import graphics.ComponentGraphics.ControllerGraphic;
import javafx.scene.control.*;
import logic.components.Controller;

public class ControllerConfigWindow2 extends ControllerConfigWindow1{
	private ArrayList<Button> buttons;
	int i;
	public ControllerConfigWindow2(String title, ControllerGraphic g) {
		super(title,g);
		
		((TextField)getLayout().getChildren().get(1)).setText(g.getName());
		((TextField)getLayout().getChildren().get(3)).setText(String.valueOf(((Controller)g.getComponent()).getInputWidth()));
		((TextField)getLayout().getChildren().get(5)).setText(String.valueOf(g.getNumOutputs()));
		
		
		buttons = new ArrayList<Button>();
		for(i=0; i<g.getNumOutputs();i++) {
			Button b = new Button("Edit output "+i);
			b.setOnAction(e->{
				int j = buttons.indexOf(e.getSource());
				ControllerOutputConfigWindow cfg = new ControllerOutputConfigWindow("config",j,getStartBit(j),getStopBit(j),g);
				cfg.showAndWait();
			});
			buttons.add(b);
			getLayout().addRow(i+3, new Label("driving bits"),new TextField(((Controller)g.getComponent()).getStartBits().get(i)+"-"+((Controller)g.getComponent()).getStopBits().get(i)),b);
		}
	}

	@Override
	public void updateComponent() throws Exception {
		int start,stop;
		for(i =0; i< this.getConrtollerGraphic().getNumOutputs();i++) {
			start = getStartBit(i);
			stop = getStopBit(i);
			((Controller)this.getConrtollerGraphic().getComponent()).setStartStopBits(i,start,stop);
			((Controller)this.getConrtollerGraphic().getComponent()).setOutputBitWidth(i,start-stop);
		}
		super.updateComponent();		
	}
	
	public int getStartBit(int j) {
		String[] x =((TextField)getLayout().getChildren().get(5+j*3+2)).getText().split("-");
		return Integer.parseInt(x[0]);
		
	}
	public int getStopBit(int j) {
		String[] x =((TextField)getLayout().getChildren().get(5+j*3+2)).getText().split("-");
		return Integer.parseInt(x[1]);
	}
	
}
