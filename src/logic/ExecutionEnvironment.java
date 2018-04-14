package logic;

/*
 * ExecutionEnvironment is the class which contains references to all components within the datapath. There can
 * only be one ExecutionEnvironment, and thus it is represented by a singleton. This class simulates the clock
 * signal in the datapath and initiates data flow. It also transmits all data to and recieves user input from the LogicInterface
 * 
 * 
 */

public class ExecutionEnvironment {
	private static ExecutionEnvironment EESingleton = new ExecutionEnvironment();
	private int radix;
	private boolean placingWire;
	public void setRadix(int rad) {radix=rad;}
	public int getRadix() {return radix;}
	public void startPlacingWire() {
		placingWire = true;
	}
	public void stopPlacingWire() {
		placingWire = false;
	}
	public void togglePlacingWire() {
		if (placingWire) {
			stopPlacingWire();
		}
		else {
			startPlacingWire();
		}
	}

	public boolean getPlacingWireStatus() {
		return placingWire;
	}

	//Do not instantiate any instance other than EESingleton
	private ExecutionEnvironment() {
		placingWire = false;
	}
	
	public static ExecutionEnvironment getExecutionEnvironment() { return EESingleton;}
	

}
