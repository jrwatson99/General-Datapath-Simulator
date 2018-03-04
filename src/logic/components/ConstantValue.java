package logic.components;

import logic.DataValue;

public class ConstantValue extends Component {

	private DataValue value;
	
	public void setValue(DataValue newValue) {value = newValue;}
	public DataValue getValue() {return value;}
	
	/**
	 * Default constructor sets the value to 0
	 * @author Jonathan Watson
	 * @version 0.1
	 */
	public ConstantValue() {
		setValue(new DataValue("0"));
	}
	
	/**
	 * Constructor, given an initial value
	 * @author Jonathan Watson
	 * @version 0.1
	 * @param initialValue
	 */
	public ConstantValue(DataValue initialValue) {
		setValue(initialValue);
	}
	
	public void Update() {
		
	}
}
