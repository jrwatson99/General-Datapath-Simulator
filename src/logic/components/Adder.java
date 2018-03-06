package logic.components;

import logic.DataValue;
import logic.Wire;

/**
 * Adder class. Performs simple mathematical addition.
 * 2 inputs, 1 output
 * out = in1 + in2
 */


public class Adder extends Component {
	
	private Wire inputA;
	private Wire inputB;
	private Wire output;
	private int bitLength;
	
	public void setInputA(Wire newInput) {inputA = newInput;}
	public Wire getInputA() {return inputA;}
	
	public void setInputB(Wire newInput) {inputB = newInput;}
	public Wire getInputB() {return inputB;}
	
	public void setOutput(Wire newOutput) {output = newOutput;}
	public Wire getOutput() {return output;}
	
	public void setBitLength(int newBitLength) {bitLength = newBitLength;}
	public int getBitLength() {return bitLength;}

	/**Adder Constructor
	 * @author Jonathan Watson
	 * @version 0.1
	 */
	public Adder() {
		super();
		setBitLength(32);
	}
	
	/**
	 * Adds the two input values and sets the output equal to the sum
	 * @author Jonathan Watson
	 * @version 0.1
	 * @throws Exception
	 * @return void
	 */
	public void Update() throws Exception {
		
		DataValue inputAVal = getInputA().getValue();
		boolean isValidBitLengthInputA = determineBitValidity(inputAVal, getBitLength());
		if (!isValidBitLengthInputA) {
			throw new Exception("ERROR: Invalid bit length for inputA in Adder");
		}
		
		DataValue inputBVal = getInputB().getValue();
		boolean isValidBitLengthInputB = determineBitValidity(inputBVal, getBitLength());
		if (!isValidBitLengthInputB) {
			throw new Exception("ERROR: Invalid bit length for inputB in Adder");
		}
		
		output.setValue((DataValue)inputAVal.add(inputBVal));
		
	}
}
