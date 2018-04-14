package logic.components;

import logic.DataValue;
import logic.Wire;

public class ConstantValue extends Component {

	private DataValue value;
	private Wire output;
	
	public void setValue(DataValue newValue) {value = newValue;}
	public DataValue getValue() {return value;}

	public void setOutput(Wire wire) { output = wire;}
    public Wire getOutput() { return output;}
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
		output.setValue(value);
	}

	@Override
	public void connectInputWire(Wire connectingWire, String inputName) {
		switch (inputName) {
			default:
				System.out.println("ERROR: Invalid input name");
		}
	}

	@Override
	public void connectOutputWire(Wire connectingWire, String outputName) {
		switch (outputName) {
			case "output":
				setOutput(connectingWire);
				connectingWire.setValue(getValue());
				break;
			default:
				System.out.println("ERROR: invalid output name");
		}
	}
}
