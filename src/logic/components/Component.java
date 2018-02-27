package logic.components;

import logic.DataValue;

/*
 * The super class for all the logical components. 
 */

public abstract class Component {
	
	
	protected Signedness signedness;
	
	public void setSignedness(Signedness newSignedness) {signedness = newSignedness;}
	public Signedness getSignedness() {return signedness;}
	
	/**
	 * A value can be considered as signed or unsigned
	 * @author Jonathan Watson
	 *
	 */
	protected enum Signedness {
		UNSIGNED,
		SIGNED
	}
	
	public Component() {
		setSignedness(Signedness.SIGNED);
	}
	
	/**
	 * Virtual routine wherein each component subclass computes the appropriate output values based on it's input values.
	 * @author Jonathan Watson
	 * @version 0.1
	 * @return void
	 * @throws Exception Invalid enum Signedness value
	 */
	public abstract void Update() throws Exception;	
	
	
	/**
	 * Checks to see if the given bit length is large enough to contain the given data value.<br>
	 * Only useful for unsigned values. For signed values, see {@link #isValidSignedBitLength(int data, int numBits)}
	 * @author Jonathan Watson
	 * @param data value to be represented in decimal form
	 * @param numBits number of bits available
	 * @version 0.1
	 * @return isValid
	 */
	protected boolean isValidUnsignedBitLength(DataValue data, int numBits) {
		
		//Data should not be less than zero.
		assert (data.compareTo(DataValue.ZERO) >= 0) : "method \"isValidUnsignedBitLength\": Param data cannot be less than zero.";
		
		//is valid if data/pow(2, numBits) < 1
		boolean isValid = ((data.divide(((new DataValue("2")).pow(numBits)).subtract(DataValue.ONE))).compareTo(DataValue.ONE) < 0);
		return isValid;
	}
	
	/**
	 * Checks to see if the given bit length is large enough to contain the given data value.<br>
	 * Only useful for signed values. For unsigned values, see {@link #isValidUnsignedBitLength(int data, int numBits)}
	 * @author Jonathan Watson
	 * @param data value to be represented in decimal form
	 * @param numBits number of bits available
	 * @version 0.1
	 * @return isValid
	 */
	protected boolean isValidSignedBitLength(DataValue data, int numBits) {
		boolean isValid = false;
		if (data.compareTo(DataValue.ZERO) >= 0) {
			//isValid = ((data / (java.lang.Math.pow(2,  numBits - 1) - 1)) < 1);
			isValid = ((data.divide(((new DataValue("2")).pow(numBits - 1)).subtract(DataValue.ONE))).compareTo(DataValue.ONE) < 0);
		}
		else {
			//isValid = ((data / (java.lang.Math.pow(2,  numBits - 1))) > -1);
			isValid = (data.divide((new DataValue("2")).pow(numBits - 1)).compareTo(new DataValue("-1")) > 0);
		}
		return isValid;
	}
	
	/**
	 * Determines the validity of the data's ability to be represented by the given number of bits.
	 * Considers signedness
	 * @param dataVal
	 * @param bitLength
	 * @return validity
	 * @throws Exception
	 */
	protected boolean determineBitValidity(DataValue dataVal, int bitLength) throws Exception {
		boolean validity = false;
		if (getSignedness() == Signedness.SIGNED) {
			validity = isValidSignedBitLength(dataVal, bitLength);
		}
		else if (getSignedness() == Signedness.UNSIGNED) {
			validity = isValidUnsignedBitLength(dataVal, bitLength);
		}
		else {
			throw new Exception("ERROR: Invalid enum Signedness value");
		}
		return validity;
	}
	
}
