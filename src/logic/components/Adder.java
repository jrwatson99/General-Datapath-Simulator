package logic.components;

/*
 * Adder class. Performs simple mathematical addition.
 * 2 inputs, 1 output
 * out = in1 + in2
 */


public class Adder extends Component {
	public Adder() {
		super();
	}
	
	public void update() {
		output=inputs.get(1)+inputs.get(2);
	}
}
