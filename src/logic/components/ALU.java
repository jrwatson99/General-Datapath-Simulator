package logic.components;

import java.util.ArrayList;

import logic.DataValue;
import logic.Wire;
import logic.components.Component.basicListener;

public class ALU extends Component {
	
	private Wire inputA;
	private Wire inputB;
	private Wire aluOP;
	private Wire output;
	private Wire zero;
	private int opBitLength;
	private ArrayList<Operation> opOrder;
	
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
	
	public void setALUOP(Wire newInput) {
		aluOP = newInput;
		aluOP.addWireListener(new basicListener());
	}
	public Wire getALUOP() {return aluOP;}
	
	public void setOutput(Wire newOutput) {output = newOutput;}
	public Wire getOutput() {return output;}

	public void setZero(Wire newZero) {zero = newZero;}
	public Wire getZero() {return zero;}
	
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

	public enum Operation{
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
		if(inputA !=null && inputB !=null && output!=null && aluOP != null) {
			DataValue inputAVal = getInputA().getValue();
			boolean isValidBitLengthInputA = determineBitValidity(inputAVal, getBitLength());
			if (!isValidBitLengthInputA) {
				throw new Exception("ERROR: Invalid bit length for inputA in ALU");
			}
			
			DataValue inputBVal = getInputB().getValue();
			boolean isValidBitLengthInputB = determineBitValidity(inputBVal, getBitLength());
			if (!isValidBitLengthInputB) {
				throw new Exception("ERROR: Invalid bit length for inputB in ALU");
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
			result = new DataValue(inputAVal.add(inputBVal));
			break;
		case SUBTRACT: // sub
			result = new DataValue(inputAVal.subtract(inputBVal));
			break;
		case MULTIPLY: // mul
			result = new DataValue(inputAVal.multiply(inputBVal));
			break;
		case SRL: // srl
			result = new DataValue(inputAVal.shiftRight(inputBVal.intValue()));
			break;
		case SLL: // sll
			result = new DataValue(inputAVal.shiftLeft(inputBVal.intValue()));
			break;
		default:
			throw new Exception("ERROR: Invalid ALU OP code");		
		}
		return fitDataToBitLength(result, getBitLength()); //FIXME
	}
	
	/**
	 * @author Jonathan Watson
	 * @version 0.1
	 * @param op
	 * @param index
	 * @throws Exception
	 */
	public void changeOpOrder(Operation op, int index) throws Exception { 
		if (opOrder.contains(op)) {
			opOrder.remove(op);
		}
		if (index > opOrder.size()) {
			throw new Exception("ERROR: insertion index greater than opOrder size");
		}
		opOrder.add(index, op);
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
			case "opCode":
				setALUOP(connectingWire);
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
	
	/**
	 * Test class
	 * @author Jonathan Watson
	 * @version 0.1
	 *
	 */
	public static class Tester32Bit {
		
		private static Wire inputAWire;
		private static Wire inputBWire;
		private static ALU testALU;
		private static Wire outputWire;
		private static Wire opCodeWire;
		private static Wire zeroWire;
		
		public static void main(String args[]) throws Exception {
			
			setupALU();
			
			//run tests with all value from 0 to 2^31 - should test many valid values, NOT invalid values
			int i = 0;
			int j = 0;
			final int maxValToTest = (int)Math.pow(2, 15);
			final int incrementAmount = (int)Math.pow(2, 3);
			System.out.println("Beginning tests for 32 bit Adder.");
			for (i = 0; i < maxValToTest; i += incrementAmount) {
				
				if (i % (incrementAmount * 10) == 0) {
					int numTenthsFinished = i / (maxValToTest / 100);
					System.out.println((numTenthsFinished) + "% Completed");
				}
				for (j = 0; j < maxValToTest; j += incrementAmount) {
					//System.out.println(i + " " + j);
					runAllTests(new DataValue(Integer.toString(i)), new DataValue(Integer.toString(j)));
				}
			}
			System.out.println("Finished tests for 32 bit ALU. Congratulations :D!");
			
			
		}
		
		private static void setupALU() {
			testALU = new ALU();
			
			inputAWire = new Wire();
			testALU.setInputA(inputAWire);
			
			inputBWire = new Wire();
			testALU.setInputB(inputBWire);
			
			outputWire = new Wire();
			testALU.setOutput(outputWire);
			
			opCodeWire = new Wire();
			testALU.setALUOP(opCodeWire);
			
			zeroWire = new Wire();
			testALU.setZero(zeroWire);
			
		}
		
		private static void runAllTests(DataValue inputAVal, DataValue inputBVal) throws Exception {
			runAddTest(inputAVal, inputBVal);
			runSubtractTest(inputAVal, inputBVal);
			runMultiplyTest(inputAVal, inputBVal);
			runShiftRightTest(inputAVal, inputBVal);
			runShiftLeftTest(inputAVal, inputBVal);
		}
		
		
		private static void runAddTest(DataValue inputAVal, DataValue inputBVal) throws Exception {
			inputAWire.setValue(inputAVal);
			inputBWire.setValue(inputBVal);
			opCodeWire.setValue(new DataValue("0"));
			testALU.Update();
			
			DataValue expectedOutputValue = new DataValue("0");
			expectedOutputValue.add(inputAVal);
			expectedOutputValue.add(inputBVal);
			
			assert(expectedOutputValue.equals(outputWire.getValue()));
		}
		
		private static void runSubtractTest(DataValue inputAVal, DataValue inputBVal) throws Exception {
			inputAWire.setValue(inputAVal);
			inputBWire.setValue(inputBVal);
			opCodeWire.setValue(new DataValue("1"));
			testALU.Update();
			
			DataValue expectedOutputValue = new DataValue("0");
			expectedOutputValue.add(inputAVal);
			expectedOutputValue.subtract(inputBVal);
			
			assert(expectedOutputValue.equals(outputWire.getValue()));
		}
		
		private static void runMultiplyTest(DataValue inputAVal, DataValue inputBVal) throws Exception {
			inputAWire.setValue(inputAVal);
			inputBWire.setValue(inputBVal);
			opCodeWire.setValue(new DataValue("2"));
			testALU.Update();
			
			DataValue expectedOutputValue = new DataValue("0");
			expectedOutputValue.add(inputAVal);
			expectedOutputValue.multiply(inputBVal);
			
			assert(expectedOutputValue.equals(outputWire.getValue()));
		}
		
		private static void runShiftRightTest(DataValue inputAVal, DataValue inputBVal) throws Exception {
			inputAWire.setValue(inputAVal);
			inputBWire.setValue(inputBVal);
			opCodeWire.setValue(new DataValue("4"));
			testALU.Update();
			
			DataValue expectedOutputValue = new DataValue("0");
			expectedOutputValue.add(inputAVal);
			if (inputBVal.compareTo(new DataValue(Integer.toString(Integer.MAX_VALUE))) <= 0) {
				expectedOutputValue.shiftRight(inputBVal.intValue());
				
				assert(expectedOutputValue.equals(outputWire.getValue()));
			}
			else {
				throw new Exception("ERROR: ALU - Cannot shift right by more than " + Integer.MAX_VALUE);
			}
		}
		
		private static void runShiftLeftTest(DataValue inputAVal, DataValue inputBVal) throws Exception {
			inputAWire.setValue(inputAVal);
			inputBWire.setValue(inputBVal);
			opCodeWire.setValue(new DataValue("3"));
			testALU.Update();
			
			DataValue expectedOutputValue = new DataValue("0");
			expectedOutputValue.add(inputAVal);
			if (inputBVal.compareTo(new DataValue(Integer.toString(Integer.MAX_VALUE))) <= 0) {
				expectedOutputValue.shiftLeft(inputBVal.intValue());
				
				assert(expectedOutputValue.equals(outputWire.getValue()));
			}
			else {
				throw new Exception("ERROR: ALU - Cannot shift left by more than " + Integer.MAX_VALUE);
			}
		}
	}
	
}
