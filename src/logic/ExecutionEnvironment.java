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

	public static void setRadix(int rad) {
	    EESingleton.radix = rad;
	}

	public static int getRadix() {
	    return EESingleton.radix;
	}

	private static void startPlacingWire() {
	    EESingleton.placingWireStatus = true;
	}

	public static void stopPlacingWire() {
	    EESingleton.placingWireStatus = false;
	}

	public static void togglePlacingWire() {
		if (EESingleton.placingWireStatus) {
			stopPlacingWire();
		}
		else {
			startPlacingWire();
		}
	}

    public static boolean getPlacingWireStatus() {
	    return EESingleton.placingWireStatus;
	}

	public static void setWireSelectedStatus(boolean isWireSelected) {
	    EESingleton.wireSelectedStatus = isWireSelected;
	}

	public static boolean getWireSelectedStatus() {
	    return EESingleton.wireSelectedStatus;
	}

    public static void setCurrentlySelectedOutputNode(ComponentOutputWireNode outputNode) {
	    EESingleton.currentlySelectedOutputNode = outputNode;
	}

    public static ComponentOutputWireNode getCurrentlySelectedOutputNode() {
	    return EESingleton.currentlySelectedOutputNode;
	}

	//Do not instantiate any instance other than EESingleton
	private ExecutionEnvironment() {
		placingWireStatus = false;
		wireSelectedStatus = false;
		currentlySelectedOutputNode = null;
	}
	
	public static ExecutionEnvironment get() {
	    return EESingleton;
	}

	public static DatapathWindow getDataPathWindow() {
		return EESingleton.dpWindow;
	}

	public static void setDataPathWindow(DatapathWindow newDPWindow) {
	    EESingleton.dpWindow = newDPWindow;
	}
}
