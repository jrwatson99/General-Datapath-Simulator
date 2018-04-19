package logic.components;

import java.util.ArrayList;

import logic.Wire;
/* This class implements a clocked register for or or more inputs. may be used as a pipeline register or a data buffer
 * each input has its own bit width and corresponding output with the same bitwidth.
 * 
 * Author: Matthew Johnson
 * Version: 0.1
 *  
 */
public class Register extends Component {
	private ArrayList<Wire> inputs;
	private ArrayList<Wire> outputs;
	private ArrayList<Integer> bitWidths;
	private Wire clk;
	private int numConnections;
	
//	public void setBitWidths(ArrayList<Integer> widths) {bitWidths = widths;}
	public void setBitWidth(int i, int width) {bitWidths.set(i, width);}
	public int getBitWidth(int i) {	return bitWidths.get(i);}
	public void setInput(int i, Wire inWire) {inputs.set(i, inWire);	}
	public Wire getInput(int i) {return inputs.get(i);	}	
	public void setOutput(int i, Wire outWire) {outputs.set(i, outWire);	}
	public Wire getOutput(int i) {	return outputs.get(i);}
	public void setNumConnections(int num) { numConnections = num;}
	public int getNumConnections() {return numConnections;}
	public Wire getClk() { return clk;}
	public void setClk(Wire clk) {
		this.clk = clk;
		this.clk.addWireListener(new basicListener());
	}
	
	
	public Register() {
		this(0);
	}
	
	public Register(int numInputs) {
		numConnections = numInputs;
		inputs = new ArrayList<Wire>();
		outputs = new ArrayList<Wire>();
		bitWidths = new ArrayList<Integer>();
	}
	
    private void initArrayLists() {
		for(int i=0;i<numConnections;i++) {
			inputs.add(null);
			outputs.add(null);
			bitWidths.add(0);
		}
		
	}
	public void Update() throws Exception {
		if(allConnected()) {
			if(getClk().getValue().intValue()==1) {
		    	for(int i=0;i<numConnections;i++) {
		    		//TODO add bit width check
		    		if(inputs.get(i)!=null) {
		    			outputs.get(i).setValue(inputs.get(i).getValue());
		    		}
		    	}
			}
			else if(getClk().getValue().intValue()==0) {
			}
			else {
				throw new Exception("Invalid clock value in Register");
			}
		}
		else {
			throw new Exception("Disconnected wire in register");
		}
    }

	private boolean allConnected() {
		for(int i=0;i<numConnections;i++) {
			if(outputs.get(i)==null || inputs.get(i)==null) {
				return false;
			}
		}
		return true;
	}
	@Override
	public void connectInputWire(Wire connectingWire, String inputName) {
		for(int i=0;i<numConnections;i++) {
			if(inputName.equals("input"+i)) {
				inputs.set(i, connectingWire);
			}
		}
	}

	@Override
	public void connectOutputWire(Wire connectingWire, String outputName) {
		for(int i=0;i<numConnections;i++) {
			if(outputName.equals("output"+i)) {
				outputs.set(i, connectingWire);
			}
		}
	}
}
