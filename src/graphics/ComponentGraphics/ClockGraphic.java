package graphics.ComponentGraphics;

import logic.DataValue;
import logic.ExecutionEnvironment;
import logic.components.ConstantValue;

public class ClockGraphic extends ConstantValueGraphic {
	
	public ClockGraphic() {
		super();
		setName("CLK");
        ExecutionEnvironment.addClockGraphic(this);
	}

	@Override
	public void addMouseHandler() {
	    //No functionality - clock is operated via control interface
	}

	@Override
	public void config() {
		((ConstantValue)getComponent()).setValue(DataValue.ZERO);
		((ConstantValue)getComponent()).Update();
	}

	public void setLow() {
		getOutput().getWire().setValue(DataValue.ZERO);
	}
	public void setHigh() {
		getOutput().getWire().setValue(DataValue.ONE);
	}
    public void toggle() {
	    if (getOutput().getWire().getValue().equals(DataValue.ZERO)) {
	        setHigh();
        }
        else {
	        setLow();
        }
    }

}
