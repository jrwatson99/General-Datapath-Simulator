package logic.components;

import logic.DataValue;
import logic.Wire;

public class Comparator extends Component {

	private Wire inputA;
	private Wire inputB;
	
	private Wire outputLessThan;
	private Wire outputEqualTo;
	private Wire outputGreaterThan;
	
	public void setInputA(Wire newInput) {
		inputA = newInput;
		inputA.addWireListener(new basicListener());
	}
	public Wire getInputA() {return inputA;}
	public void setInputB(Wire newInput) {
		inputB = newInput;
		inputB.addWireListener(new basicListener());
	}
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

		if(inputA !=null && inputB !=null) {
			DataValue inputAVal = inputA.getValue();
			DataValue inputBVal = inputB.getValue();
			if(outputLessThan != null) {
				if (inputAVal.compareTo(inputBVal) > 0) {
					getOutputLessThan().setValue((DataValue)DataValue.ZERO);
					
				}
				else if (inputAVal.compareTo(inputBVal) < 0) {
					getOutputLessThan().setValue((DataValue)DataValue.ONE);
					
				}
				else {
					getOutputLessThan().setValue((DataValue)DataValue.ZERO);
				}
			}
			if(outputGreaterThan != null) {
				if (inputAVal.compareTo(inputBVal) > 0) {
					getOutputGreaterThan().setValue((DataValue)DataValue.ONE);
					
				}
				else if (inputAVal.compareTo(inputBVal) < 0) {
					getOutputGreaterThan().setValue((DataValue)DataValue.ZERO);
					
				}
				else {
					getOutputGreaterThan().setValue((DataValue)DataValue.ZERO);
				}
			}
			if(outputEqualTo != null) {
				if (inputAVal.compareTo(inputBVal) > 0) {
					getOutputEqualTo().setValue((DataValue)DataValue.ZERO);
					
				}
				else if (inputAVal.compareTo(inputBVal) < 0) {
					getOutputEqualTo().setValue((DataValue)DataValue.ZERO);
					
				}
				else {
					getOutputEqualTo().setValue((DataValue)DataValue.ONE);
				}
			}
		}
	}

	@Override
	public void connectInputWire(Wire connectingWire, String inputName) {
		switch (inputName) {
			case "inputA":
				setInputA(connectingWire);
				break;
			case "inputB":
				setInputB(connectingWire);
				break;
			default:
				System.out.println("ERROR: Invalid input name");
		}
	}

	@Override
	public void connectOutputWire(Wire connectingWire, String outputName) {
		switch (outputName) {
			case "outputLT":
				setOutputLessThan(connectingWire);
				break;
			case "outputEQ":
				setOutputEqualTo(connectingWire);
				break;
			case "outputGT":
				setOutputGreaterThan(connectingWire);
				break;
			default:
				System.out.println("ERROR: invalid output name");
		}
	}
	
}
