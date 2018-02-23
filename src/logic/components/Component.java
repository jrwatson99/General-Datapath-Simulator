package logic.components;

/*
 * The super class for all the logical components. 
 */

public abstract class Component {
	
	public Component() {
	}
	
	/**Update
	 * Virtual routine wherein each component subclass computes the appropriate output values based on it's input values.
	 * @author Jonathan Watson
	 * @version 0.1
	 * @return void
	 */
	public abstract void Update();	
	
	
	/**isValidUnsignedBitLength
	 * Checks to see if the given bit length is large enough to contain the given data value.<br>
	 * Only useful for unsigned values. For signed values, see {@link #isValidSignedBitLength(int data, int numBits)}
	 * @author Jonathan Watson
	 * @param data value to be represented in decimal form
	 * @param numBits number of bits available
	 * @version 0.1
	 * @return isValid
	 */
	protected boolean isValidUnsignedBitLength(int data, int numBits) {
		
		//Data should not be less than zero.
		assert (data >= 0) : "method \"isValidUnsignedBitLength\": Param data cannot be less than zero.";
		
		boolean isValid = ((data / (java.lang.Math.pow(2, numBits) - 1)) < 1);
		return isValid;
	}
	
	/** isValidSignedBitLength
	 * Checks to see if the given bit length is large enough to contain the given data value.<br>
	 * Only useful for signed values. For unsigned values, see {@link #isValidUnsignedBitLength(int data, int numBits)}
	 * @author Jonathan Watson
	 * @param data value to be represented in decimal form
	 * @param numBits number of bits available
	 * @version 0.1
	 * @return isValid
	 */
	protected boolean isValidSignedBitLength(int data, int numBits) {
		boolean isValid = false;
		if (data >= 0) {
			isValid = ((data / (java.lang.Math.pow(2,  numBits - 1) - 1)) < 1);
		}
		else {
			isValid = ((data / (java.lang.Math.pow(2,  numBits - 1))) > -1);
		}
		return isValid;
	}
	
}
