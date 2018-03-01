package logic.components;

import java.util.ArrayList;

import logic.DataValue;
import logic.Wire;

public class ALU extends Component {
	
	private Wire inputA;
	private Wire inputB;
	private Wire aluOP;
	private Wire output;
	private Wire zero;
	private int bitLength;
	private int opBitLength;
	private ArrayList<Operation> opOrder;
	
	public void setInputA(Wire newInput) {inputA = newInput;}
	public Wire getInputA() {return inputA;}
	
	public void setInputB(Wire newInput) {inputB = newInput;}
	public Wire getInputB() {return inputB;}
	
	public void setALUOP(Wire newInput) {aluOP = newInput;}
	public Wire getALUOP() {return aluOP;}
	
	public void setOutput(Wire newOutput) {output = newOutput;}
	public Wire getOutput() {return output;}

	public void setZero(Wire newZero) {zero = newZero;}
	public Wire getZero() {return zero;}
		
	public void setBitLength(int newBitLength) {bitLength = newBitLength;}
	public int getBitLength() {return bitLength;}
	
	public void setOPBitLength(int newBitLength) { opBitLength = newBitLength;}
	public int getOPBitLength() { return opBitLength;}
	
	/**
	 * ALU constructor
	 * @author Matthew Johnson
	 * @version 0.1
	 */
	public ALU() {
		super();
		setBitLength(32);
		setOPBitLength(6);
		opOrder=new ArrayList<Operation>();
		initOpOrder();
	}

	private enum Operation{
		ADD,
		SUBTRACT,
		MULTIPLY,
		SLL,
		SRL,
	}
	
	
	/**
	 * initializes the relationship between opCode and operation. The opCode is the index of the desired operation.
	 * @author Matthew Johnson
	 * @version 0.1
	 * @throws Exception
	 * @return void
	 */
	private void initOpOrder() {
		opOrder.add(Operation.ADD);
		opOrder.add(Operation.SUBTRACT);
		opOrder.add(Operation.MULTIPLY);
		opOrder.add(Operation.SLL);
		opOrder.add(Operation.SRL);		
	}	
	
	/**
	 * Updates output based on selected opCode, and sets the zero flag high if the result is 0;
	 * @author Matthew Johnson
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
		
		DataValue result;
		Operation op=opOrder.get(aluOP.getValue().intValue());
		
		result=performOperation(inputAVal,inputBVal, op);


		if(result.intValue()==0) {
			zero.setValue(new DataValue("1"));
		}
		else {
			zero.setValue(new DataValue("0"));
		}
		
		 
		getOutput().setValue(result);
	}
	
	/**
	 * Performs the selected operation on the two inputs and sets the zero flag high if the result is 0;
	 * @author Matthew Johnson
	 * @version 0.1
	 * @throws Exception
	 * @return void
	 */	
	private DataValue performOperation(DataValue inputAVal, DataValue inputBVal, Operation operation) throws Exception {
		DataValue result;
		switch(operation) {
		case ADD: // add
			result=(DataValue)inputAVal.add(inputBVal);
			break;
		case SUBTRACT: // sub
			result=(DataValue)inputAVal.subtract(inputBVal);
			break;
		case MULTIPLY: // mul
			result=(DataValue)inputAVal.multiply(inputBVal);
			break;
		case SRL: // srl
			result=(DataValue)inputAVal.shiftRight(inputBVal.intValue());
			break;
		case SLL: // sll
			result=(DataValue)inputAVal.shiftLeft(inputBVal.intValue());
			break;
		default:
			throw new Exception("ERROR: Invalid ALU OP code");		
		}
		return fitDataToBitLength(result); //FIXME
	}
	
	private void changeOpOrder(Operation op, int index) { 
		//TODO not sure how we want to implement this yet.
	}
}
