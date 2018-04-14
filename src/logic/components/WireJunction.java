package logic.components;

import logic.Wire;
import logic.WireListener;
import logic.components.Component.basicListener;

public class WireJunction extends Component{
	private Wire input;
	private Wire out1;
	private Wire out2;

	public Wire getInput() {
		return input;
	}
	public void setInput(Wire input) {
		this.input = input;
		this.input.addWireListener(new basicListener());
	}
	public Wire getOutputA() {
		return out1;
	}
	public void setOutputA(Wire out1) {
		this.out1 = out1;
	}
	public Wire getOutputB() {
		return out2;
	}
	public void setOutputB(Wire out2) {
		this.out2 = out2;
	}
	
	public WireJunction() {
		
	}

	@Override
	public void Update() throws Exception {
		if(out1!= null) {
			out1.setValue(input.getValue());
		}
		if(out2 != null) {
			out2.setValue(input.getValue());
		}
	}

	@Override
	public void connectInputWire(Wire connectingWire, String inputName) {
		switch (inputName) {
			case "input":
				setInput(connectingWire);
				break;
			default:
				System.out.println("ERROR: Invalid input name");
		}
	}

	@Override
	public void connectOutputWire(Wire connectingWire, String outputName) {
		switch (outputName) {
			case "outputA":
				setOutputA(connectingWire);
				break;
			case "outputB":
				setOutputB(connectingWire);
				break;
			default:
				System.out.println("ERROR: invalid output name");
		}
	}
}
