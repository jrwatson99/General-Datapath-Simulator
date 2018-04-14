package logic.components;

import logic.DataValue;
import logic.Wire;
import logic.components.Component.basicListener;

public class BitExtender extends Component {
	
	private Wire input;
	private Wire output;
	private int inputBitLength;
	private int outputBitLength;
	
	public void setInput(Wire newInput) {
		input = newInput;
		input.addWireListener(new basicListener());
	}
	public Wire getInput() {return input;}
	
	public void setOutput(Wire newOutput) {output = newOutput;}
	public Wire getOutput() {return output;}
	
	public void setInputBitLength(int newInputBitLength) {inputBitLength = newInputBitLength;}
	public int getInputBitLength() {return inputBitLength;}
	
	public void setOutputBitLength(int newOutputBitLength) {outputBitLength = newOutputBitLength;}
	public int getOutputBitLength() {return outputBitLength;}
	
	
	/**BitExtender Constructor
	 * @author Jonathan Watson
	 * @version 0.1
	 */
	public BitExtender() {
		setInputBitLength(32);
		setOutputBitLength(32);
	}
	
	/**
	 * Checks bit validity for both input and output, then passes on the value from input to output
	 * @author Jonathan Watson
	 * @version 0.1
	 * @throws Exception
	 * @return void
	 */
	public void Update() throws Exception {
		if(output!= null) {
			DataValue inputVal = input.getValue();
			if (determineBitValidity(inputVal, getInputBitLength()) == false) {
				throw new Exception("ERROR: Invalid bit length for input in BitExtender");
			}
			if (determineBitValidity(inputVal, getOutputBitLength()) == false) {
				throw new Exception("ERROR: Invalid bit length for output in BitExtender");
			}
			getOutput().setValue(inputVal);
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
