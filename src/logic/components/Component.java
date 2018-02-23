package logic.components;

import java.util.ArrayList;

/*
 * The super class for all the logical components. 
 */

public abstract class Component {
	protected ArrayList<Integer> inputs;
	protected int output;
	
	public Component() {
		inputs= new ArrayList<Integer>();
		output=0;
		
	}
	public abstract void update();
	public void setInput(int index, int value) {
		inputs.set(index, value);
	}
	public int getOutput() {
		return output;
	}
	
	
}
