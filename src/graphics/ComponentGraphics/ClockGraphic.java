package graphics.ComponentGraphics;

import logic.DataValue;
import logic.components.ConstantValue;

public class ClockGraphic extends ConstantValueGraphic {
	
	public ClockGraphic() {
		super();
		setName("CLK");
	}
	@Override
	public void addMouseHandler() {
		getRect().setOnMouseClicked(e -> {
			if(getOutput().getWire() != null) {
				if(getOutput().getWire().getValue().compareTo(DataValue.ONE)==0) {
					getOutput().getWire().setValue(DataValue.ZERO);
				}
				else {
					getOutput().getWire().setValue(DataValue.ONE);
				}
				updateWireText();
			}
		});
	}
	@Override
	public void config() {
		((ConstantValue)getComponent()).setValue(DataValue.ZERO);
		((ConstantValue)getComponent()).Update();
	}
	
}
