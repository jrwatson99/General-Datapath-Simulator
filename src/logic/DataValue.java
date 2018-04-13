package logic;

import java.math.BigInteger;

/**ignore for now
 * 
 * @author Jonathan Watson
 *
 */
public class DataValue extends BigInteger {
	public static DataValue ONE = new DataValue(BigInteger.ONE);
	public static DataValue ZERO= new DataValue(BigInteger.ZERO);
	public static DataValue TEN = new DataValue(BigInteger.TEN);
	public DataValue(String value) {
		super(value);
	}
	public DataValue(BigInteger bigInt) {
		super(bigInt.toByteArray());
	}
	public DataValue(byte[] byteData) {
		super(byteData);
	}
	public DataValue() {
		this(0);
	}
	public DataValue(int num) {
		this(BigInteger.valueOf(num));
	}
	public DataValue(String line, int radix) {
		super(line,radix);
	}
}
