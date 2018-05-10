package logic.components;

import logic.DataValue;
import logic.Wire;

public class BitShifter extends Component {

	
	/**
	 * BitShifter can shift input either to the left or to the right
	 * @author Jonathan Watson
	 * @version 0.1
	 */
	private enum Direction {
		LEFT,
		RIGHT
	}
	
	private Wire input;
	private Wire output;
	private int shiftAmount;
	private Direction direction;
	
	
	public void setInput(Wire newInput) {
		input = newInput;
		//input.addWireListener(new basicListener());
	}
	public Wire getInput() {return input;}
	
	public void setOutput(Wire newOutput) {output = newOutput;}
	public Wire getOutput() {return output;}
	
	public void setShiftAmount(int newShiftAmount) {shiftAmount = newShiftAmount;}
	public int getShiftAmount() {return shiftAmount;}
	
	public void setDirection(Direction newDirection) {direction = newDirection;}
	public Direction getDirection() {return direction;}
	
	public BitShifter() {
		setBitLength(32);
		setShiftAmount(0);
		setDirection(Direction.RIGHT);
	}
	
	/**
	 * Shifts input value the appropriate number of bits in appropriate direction and remove extra bits
	 * @author Jonathan Watson
	 * @version 0.1
	 * @throws Exception
	 * @return void
	 */
	public void Update() throws Exception {
		if(output != null) {
			DataValue inputVal = getInput().getValue();
			if (determineBitValidity(inputVal, getBitLength()) == false) {
				throw new Exception("ERROR: Invalid bit length in BitShifter");
			}
			
			DataValue newOutput = shiftData(inputVal);
			
			getOutput().setValue(fitDataToBitLength(newOutput, getBitLength()));
		}
	}
	
	/**
	 * shifts input data the appropriate number of bits in the appropriate direction
	 * @author Jonathan Watson
	 * @version 0.1
	 * @param dataToShift
	 * @return
	 * @throws Exception 
	 */
	private DataValue shiftData(DataValue dataToShift) throws Exception {
		DataValue newOutput = dataToShift;
		if (direction == Direction.RIGHT) {
			newOutput = new DataValue(dataToShift.shiftRight(getShiftAmount()));
		}
		else if (direction == Direction.LEFT) {
			newOutput = new DataValue(dataToShift.shiftLeft(getShiftAmount()));
		}
		else {
			throw new Exception("ERROR: Invalid Signedness value in Bitshifter");
		}
		
		return newOutput;
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

	private static class Tester32Bit {
		
		private static BitShifter testBitShifter;
		private static Wire inputWire;
		private static Wire outputWire;
		
		
		private static void main(String args[]) {
			setupBitShifter();
			
			
			/*
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
			*/
			System.out.println("Finished tests for 32 bit Adder. Congratulations :D!");
		}
		
		private static void setupBitShifter() {
			testBitShifter = new BitShifter();
			
			inputWire = new Wire();
			testBitShifter.setInput(inputWire);
			
			outputWire = new Wire();
			testBitShifter.setOutput(outputWire);
		}
		
		private static void runTest(DataValue inputVal, int shiftAmount, Direction direction) throws Exception {
			inputWire.setValue(inputVal);
			testBitShifter.setShiftAmount(shiftAmount);
			testBitShifter.setDirection(direction);
			testBitShifter.Update();
			
			DataValue expectedOutputValue = new DataValue("0");
			expectedOutputValue.add(inputVal);
			if (direction == Direction.RIGHT) {
				expectedOutputValue.shiftRight(shiftAmount);
			}
			else if (direction == Direction.LEFT) {
				expectedOutputValue.shiftLeft(shiftAmount);
			}
			else {
				throw new Exception("ERROR: invalid direction");
			}
			
			assert (expectedOutputValue.equals(outputWire.getValue()));
		}
		
	}
	
}
