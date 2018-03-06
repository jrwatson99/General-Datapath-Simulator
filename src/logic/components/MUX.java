package logic.components;

import logic.Wire;

/**
 * Mux Class
 * 
 * 3 inputs, 1 output
 * 
 * if in3 is 1, out = in1
 * else out = in2
 */
public class MUX extends Component{
	
	private Wire inputA;
	private Wire inputB;
	private Wire select;
	private Wire output;
	private int bitLength;
	
	public void setInputA(Wire newInput) {inputA = newInput;}
	public Wire getInputA() {return inputA;}
	public void setInputB(Wire newInput) {inputB = newInput;}
	public Wire getInputB() {return inputB;}
	public void setSelect(Wire newInput) {select = newInput;}
	public Wire getSelect() {return select;}
	public void setoutput(Wire newInput) {output= newInput;}
	public Wire getOutput() {return output;}
	
	public void setBitLength(int newBitLength) {bitLength = newBitLength;}
	public int getBitLength() {return bitLength;}

	
	public MUX() {
		super();
		bitLength=32;
	}

	/**
	 * Updates the output of the mux based on the select signal.
	 * @author Matthew Johnson
	 * @version 0.1
	 * @throws Exception
	 * @return void
	 */
	@Override
	public void Update() throws Exception {
		if(select.getValue().intValue()==0) {
			if(inputA.getValue().bitLength()!=this.bitLength) {
				throw new Exception("Invalid bit length in Mux inputA");
			}
			output.setValue(inputA.getValue());
		}
		else if(select.getValue().intValue()==1) {
			if(inputB.getValue().bitLength()!=this.bitLength) {
				throw new Exception("Invalid bit length in Mux inputB");
			}
			output.setValue(inputB.getValue());
		}
		else {
			throw new Exception("Invalid select input to Mux");
		}
	}
	
	
}
