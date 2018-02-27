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
	private int bitLength;
	private int shiftAmount;
	private Direction direction;
	
	
	public void setInput(Wire newInput) {input = newInput;}
	public Wire getInput() {return input;}
	
	public void setOutput(Wire newOutput) {output = newOutput;}
	public Wire getOutput() {return output;}
	
	public void setBitLength(int newBitLength) {bitLength = newBitLength;}
	public int getBitLength() {return bitLength;}
	
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
		DataValue inputVal = getInput().getValue();
		if (determineBitValidity(inputVal, getBitLength()) == false) {
			throw new Exception("ERROR: Invalid bit length in BitShifter");
		}
		
		DataValue newOutput = shiftData(inputVal);
		
		getOutput().setValue(fitDataToBitLength(newOutput));
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
			newOutput = (DataValue)dataToShift.shiftRight(getShiftAmount());
		}
		else if (direction == Direction.LEFT) {
			newOutput = (DataValue)dataToShift.shiftLeft(getShiftAmount());
		}
		else {
			throw new Exception("ERROR: Invalid Signedness value in Bitshifter");
		}
		
		return newOutput;
	}
	
	/**
	 * removes any part of input that can not exist within the given bitLength
	 * @author Jonathan Watson
	 * @version 0.1
	 * @param dataToFit
	 * @return newOutput
	 * @throws Exception
	 */
	private DataValue fitDataToBitLength(DataValue dataToFit) throws Exception {
		DataValue newOutput = dataToFit;
		if (getSignedness() == Signedness.SIGNED) {
			//if newOutput > 0
			if (newOutput.compareTo(DataValue.ZERO) > 0) {
				newOutput = (DataValue)newOutput.mod(new DataValue(Integer.toString((int)Math.pow(2, getBitLength() - 1) - 1)));
			}
			//if newOutput < 0
			else if (newOutput.compareTo(DataValue.ZERO) < 0) {
				newOutput = (DataValue)(newOutput.multiply(new DataValue("-1")).mod(new DataValue(Integer.toString((int)Math.pow(2, getBitLength() - 1))))).multiply(new DataValue("-1"));
			}
		}
		else if (getSignedness() == Signedness.UNSIGNED) {
			newOutput = (DataValue)newOutput.mod(new DataValue(Integer.toString((int)Math.pow(2, getBitLength()))));
		}
		else {
			throw new Exception("ERROR: Invalid signedness value in Bitshifter");
		}
		return newOutput;
	}
}
