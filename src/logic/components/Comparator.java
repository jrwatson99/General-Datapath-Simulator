package logic.components;

import logic.DataValue;
import logic.Wire;

public class Comparator extends Component {

	private Wire inputA;
	private Wire inputB;
	
	private Wire outputLessThan;
	private Wire outputEqualTo;
	private Wire outputGreaterThan;
	
	public void setInputA(Wire newInput) {inputA = newInput;}
	public Wire getInputA() {return inputA;}
	public void setInputB(Wire newInput) {inputB = newInput;}
	public Wire getInputB() {return inputB;}
	
	public void setOutputLessThan(Wire newOutput) {outputLessThan = newOutput;}
	public Wire getOutputLessThan() {return outputLessThan;}
	public void setOutputEqualTo(Wire newOutput) {outputEqualTo = newOutput;}
	public Wire getOutputEqualTo() {return outputEqualTo;}
	public void setOutputGreaterThan(Wire newOutput) {outputGreaterThan = newOutput;}
	public Wire getOutputGreaterThan() {return outputGreaterThan;}
	
	public Comparator() {
		super();
	}
	
	/**
	 * Compares input values and sets comparison output bits to appropriate values
	 * @author Jonathan Watson
	 * @version 0.1
	 */
	public void Update() {
		DataValue inputAVal = inputA.getValue();
		DataValue inputBVal = inputB.getValue();
		
		if (inputAVal.compareTo(inputBVal) > 0) {
			getOutputGreaterThan().setValue((DataValue)DataValue.ONE);
			getOutputEqualTo().setValue((DataValue)DataValue.ZERO);
			getOutputLessThan().setValue((DataValue)DataValue.ZERO);
			
		}
		else if (inputAVal.compareTo(inputBVal) < 0) {
			getOutputGreaterThan().setValue((DataValue)DataValue.ZERO);
			getOutputEqualTo().setValue((DataValue)DataValue.ZERO);
			getOutputLessThan().setValue((DataValue)DataValue.ONE);
			
		}
		else {
			getOutputGreaterThan().setValue((DataValue)DataValue.ZERO);
			getOutputEqualTo().setValue((DataValue)DataValue.ONE);
			getOutputLessThan().setValue((DataValue)DataValue.ZERO);
		}
	}
	
}
