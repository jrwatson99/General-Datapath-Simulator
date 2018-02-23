package logic.components;

import java.math.BigInteger;

import logic.dataValue;

/*
 * The super class for all the logical components. 
 */

public abstract class Component {
	
	public Component() {
	}
	
	/**
	 * Virtual routine wherein each component subclass computes the appropriate output values based on it's input values.
	 * @author Jonathan Watson
	 * @version 0.1
	 * @return void
	 */
	public abstract void Update();	
	
	
	/**
	 * Checks to see if the given bit length is large enough to contain the given data value.<br>
	 * Only useful for unsigned values. For signed values, see {@link #isValidSignedBitLength(int data, int numBits)}
	 * @author Jonathan Watson
	 * @param data value to be represented in decimal form
	 * @param numBits number of bits available
	 * @version 0.1
	 * @return isValid
	 */
	protected boolean isValidUnsignedBitLength(dataValue data, int numBits) {
		
		//Data should not be less than zero.
		assert (data.compareTo(BigInteger.ZERO) >= 0) : "method \"isValidUnsignedBitLength\": Param data cannot be less than zero.";
		
		//is valid if data/pow(2, numBits) < 1
		boolean isValid = ((data.divide(((new BigInteger("2")).pow(numBits)).subtract(BigInteger.ONE))).compareTo(BigInteger.ONE) < 0);
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
	protected boolean isValidSignedBitLength(dataValue data, int numBits) {
		boolean isValid = false;
		if (data.compareTo(BigInteger.ZERO) >= 0) {
			//isValid = ((data / (java.lang.Math.pow(2,  numBits - 1) - 1)) < 1);
			isValid = ((data.divide(((new BigInteger("2")).pow(numBits - 1)).subtract(BigInteger.ONE))).compareTo(BigInteger.ONE) < 0);
		}
		else {
			//isValid = ((data / (java.lang.Math.pow(2,  numBits - 1))) > -1);
			isValid = (data.divide((new BigInteger("2")).pow(numBits - 1)).compareTo(new BigInteger("-1")) > 0);
		}
		return isValid;
	}
	
}
