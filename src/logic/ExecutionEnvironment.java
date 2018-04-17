package logic;

import graphics.ComponentGraphics.ComponentOutputWireNode;
import graphics.GUIElements.DatapathWindow;

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
	private DatapathWindow dpWindow;
	private boolean placingWireStatus;
	private boolean wireSelectedStatus;
	private ComponentOutputWireNode currentlySelectedOutputNode;

	public void setRadix(int rad) {radix = rad;}
	public int getRadix() {return radix;}

	public void startPlacingWire() {
		placingWireStatus = true;
	}
	public void stopPlacingWire() {
		placingWireStatus = false;
	}
	public void togglePlacingWire() {
		if (placingWireStatus) {
			stopPlacingWire();
		}
		else {
			startPlacingWire();
		}
	}
    public boolean getPlacingWireStatus() { return placingWireStatus;}

	public void setWireSelectedStatus(boolean isWireSelected) { wireSelectedStatus = isWireSelected;}
	public boolean getWireSelectedStatus() { return wireSelectedStatus;}

    public void setCurrentlySelectedOutputNode(ComponentOutputWireNode outputNode) { currentlySelectedOutputNode = outputNode;}
    public ComponentOutputWireNode getCurrentlySelectedOutputNode() { return currentlySelectedOutputNode;}

	//Do not instantiate any instance other than EESingleton
	private ExecutionEnvironment() {
		placingWireStatus = false;
		wireSelectedStatus = false;
		currentlySelectedOutputNode = null;
	}
	
	public static ExecutionEnvironment getExecutionEnvironment() { return EESingleton;}
	public DatapathWindow getDataPathWindow() {
		return dpWindow;
	}
	public void setDataPathWindow(DatapathWindow newDPWindow) {this.dpWindow = newDPWindow;}
	

}
