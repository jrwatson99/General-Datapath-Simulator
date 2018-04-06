package logic.components;

import logic.Wire;
import logic.WireListener;

public class WireJunction extends Component{
	private Wire input;
	private Wire out1;
	private Wire out2;

	public Wire getInput() {
		return input;
	}
	public void setInput(Wire input) {
		this.input = input;
		this.input.addWireListener(new Updater());
	}
	public Wire getOut1() {
		return out1;
	}
	public void setOut1(Wire out1) {
		this.out1 = out1;
	}
	public Wire getOut2() {
		return out2;
	}
	public void setOut2(Wire out2) {
		this.out2 = out2;
	}
	
	public WireJunction() {
		
	}
	private class Updater implements WireListener{

		@Override
		public void onValueChange() {
			out1.setValue(input.getValue());
			out2.setValue(input.getValue());
		}
		
	}
	@Override
	public void Update() throws Exception {
		
	}
}
