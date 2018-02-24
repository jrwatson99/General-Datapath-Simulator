package logic.components;

import logic.DataValue;
import logic.Wire;

/*
 * Adder class. Performs simple mathematical addition.
 * 2 inputs, 1 output
 * out = in1 + in2
 */


public class Adder extends Component {
	
	private Wire inputA;
	private Wire inputB;
	private Wire output;
	private int bitLength;
	private Signedness signedness;
	
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
		signedness = Signedness.SIGNED;
		bitLength = 32;
	}
	
	/**
	 * Adds the two input values and sets the output equal to the sum
	 * @author Jonathan Watson
	 * @throws Exception
	 * @return void
	 */
	public void Update() throws Exception {
		
		DataValue inputAVal = inputA.getValue();
		boolean isValidBitLengthInputA = determineBitValidity(inputAVal);
		if (!isValidBitLengthInputA) {
			throw new Exception("ERROR: Invalid bit length for inputA in Adder");
		}
		
		DataValue inputBVal = inputB.getValue();
		boolean isValidBitLengthInputB = determineBitValidity(inputBVal);
		if (!isValidBitLengthInputB) {
			throw new Exception("ERROR: Invalid bit length for inputB in Adder");
		}
		
		output.setValue((DataValue)inputAVal.add(inputBVal));
		
	}
	
	/**
	 * Determines the validity of the data's ability to be represented by the given number of bits.
	 * Considers signedness
	 * @param dataVal
	 * @return validity
	 * @throws Exception
	 */
	private boolean determineBitValidity(DataValue dataVal) throws Exception {
		boolean validity = false;
		if (signedness == Signedness.SIGNED) {
			validity = isValidSignedBitLength(dataVal, bitLength);
		}
		else if (signedness == Signedness.UNSIGNED) {
			validity = isValidUnsignedBitLength(dataVal, bitLength);
		}
		else {
			throw new Exception("ERROR: Invalid enum Signedness value");
		}
		return validity;
	}
}
