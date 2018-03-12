package logic;

import java.math.BigInteger;

/**ignore for now
 * 
 * @author Jonathan Watson
 *
 */
public class DataValue extends BigInteger {

	public DataValue(String value) {
		super(value);
	}
	public DataValue(BigInteger bigInt) {
		super(bigInt.toByteArray());
	}
}
