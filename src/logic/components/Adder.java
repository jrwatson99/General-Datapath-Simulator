package logic.components;

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
	
	public void setInputA(Wire newInput) {inputA = newInput;}
	public Wire getInputA() {return inputA;}
	
	public void setInputB(Wire newInput) {inputB = newInput;}
	public Wire getInputB() {return inputB;}
	
	public void setOutput(Wire newOutput) {output = newOutput;}
	public Wire getOutput() {return output;}
	
	
	public Adder() {
		super();
	}
	
	/**
	 * Adds the two input values and sets the output equal to the sum
	 */
	public void Update() {
		
	}
}
