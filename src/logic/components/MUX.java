package logic.components;
/*
 * Mux Class
 * 
 * 3 inputs, 1 output
 * 
 * if in3 is 1, out = in1
 * else out = in2
 */
public class MUX extends Component{
	public MUX() {
		super();
	}
	
	public void update() {
		if(inputs.get(3)==1) {
			output=inputs.get(1);
		}
		else {
			output=inputs.get(2);
		}
	}
}
