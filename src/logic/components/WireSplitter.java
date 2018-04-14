package logic.components;

import java.math.BigInteger;

import logic.DataValue;
import logic.Wire;
import logic.WireListener;
import logic.components.Component.basicListener;

public class WireSplitter extends Component{
	private Wire input;
	private Wire output;
	private int inWidth;
	private int outWidth;
	private int startBit;   //this is the number of the bit not its value for ex: if we want bits 5-8 start bit is 5, stopbit is 8
	private int stopBit;   //all bits from startbit to stopbit (inclusive) will be part of the output wire
	
	public WireSplitter(int inWidth,int outWidth, int startBit, int stopBit) {
		this.inWidth=inWidth;
		this.outWidth=outWidth;
		this.startBit=startBit;
		this.stopBit=stopBit;
		
	}

	public WireSplitter() {
		this.inWidth=0;
		this.outWidth=0;
		this.startBit=0;
		this.stopBit=0;
	}

	public Wire getInput() {
		return input;
	}

	public void setInput(Wire input) {
		this.input = input;
		this.input.addWireListener(new basicListener());
	}

	public Wire getOutput() {
		return output;
	}

	public void setOutput(Wire output) {
		this.output = output;
	}

	public int getInWidth() {
		return inWidth;
	}

	public void setInWidth(int inWidth) {
		this.inWidth = inWidth;
	}

	public int getOutWidth() {
		return outWidth;
	}

	public void setOutWidth(int outWidth) {
		this.outWidth = outWidth;
	}

	public int getStartBit() {
		return startBit;
	}

	public void setStartBit(int startBit) {
		this.startBit = startBit;
	}

	public int getStopBit() {
		return stopBit;
	}

	public void setStopBit(int stopBit) {
		this.stopBit = stopBit;
	}


	@Override
	public void Update() throws Exception {
		if(output != null) {
			BigInteger x;
			String y =input.getValue().toString(2);
			x = new BigInteger(y.substring(startBit, stopBit+1),2);
			output.setValue(new DataValue(x));
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
			case "output":
				setOutput(connectingWire);
				break;
			default:
				System.out.println("ERROR: invalid output name");
		}
	}
}
