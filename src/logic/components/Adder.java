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
	
	public void setInputA(Wire newInput) {
		inputA = newInput;
		//inputA.addWireListener(new basicListener());
	}
	public Wire getInputA() {return inputA;}
	
	public void setInputB(Wire newInput) {
		inputB = newInput;
		//inputB.addWireListener(new basicListener());
	}
	public Wire getInputB() {return inputB;}
	
	public void setOutput(Wire newOutput) {output = newOutput;}
	public Wire getOutput() {return output;}
	

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
		if(inputA !=null && inputB !=null && output!=null) {
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
			
			//System.out.println(inputAVal.toString() + " " + inputBVal.toString() + " " + (inputAVal.add(inputBVal)).toString());
			output.setValue(new DataValue(inputAVal.add(inputBVal)));
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
	@SuppressWarnings("unused")
	private static class Tester32Bit {
		
		private static Wire inputAWire;
		private static Wire inputBWire;
		private static Wire outputWire;
		private static Adder testAdder;
		
		public static void main(String args[]) throws Exception {
			
			//initiate wires and adder, connect wires to adder
			setupAdder();

			//run tests with all value from 0 to 2^31 - should test many valid values, NOT invalid values
			int i = 0;
			int j = 0;
			final int maxValToTest = (int)Math.pow(2, 30);
			final int incrementAmount = (int)Math.pow(2, 18);
			System.out.println("Beginning tests for 32 bit Adder.");
			for (i = 0; i < maxValToTest; i += incrementAmount) {
				
				if (i % (incrementAmount * 10) == 0) {
					int numTenthsFinished = i / (maxValToTest / 100);
					System.out.println((numTenthsFinished) + "% Completed");
				}
				for (j = 0; j < maxValToTest; j += incrementAmount) {
					//System.out.println(i + " " + j);
					runTest(new DataValue(Integer.toString(i)), new DataValue(Integer.toString(j)));
				}
			}
			System.out.println("Finished tests for 32 bit Adder. Congratulations :D!");
			
			
			
		}
		
		private static void setupAdder() {
			testAdder = new Adder();
			
			inputAWire = new Wire();
			testAdder.setInputA(inputAWire);
			
			inputBWire = new Wire();
			testAdder.setInputB(inputBWire);
			
			outputWire = new Wire();
			testAdder.setOutput(outputWire);
		}
		
		private static void runTest(DataValue inputAVal, DataValue inputBVal) throws Exception {
			inputAWire.setValue(inputAVal);
			inputBWire.setValue(inputBVal);
			testAdder.Update();
			
			DataValue expectedOutputValue = new DataValue("0");
			expectedOutputValue.add(inputAVal);
			expectedOutputValue.add(inputBVal);
			
			assert(expectedOutputValue.equals(outputWire.getValue()));
		}
	}
}
