package logic;

import java.math.BigInteger;

public class WireSplitter {
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

	public Wire getInput() {
		return input;
	}

	public void setInput(Wire input) {
		this.input = input;
		this.input.addWireListener(new Updater());
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

	private class Updater implements WireListener{

		@Override
		public void onValueChange() {
			BigInteger x;
			String y =input.getValue().toString(2);
			x = new BigInteger(y.substring(startBit, stopBit+1),2);
			output.setValue(new DataValue(x));
		}
		
	}
//	private class tester{
//		
//	}
	
	
	
	
	
	
}
