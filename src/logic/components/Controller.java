package logic.components;

import java.util.ArrayList;
import logic.DataValue;
import logic.Wire;

public class Controller extends Component {
	private ArrayList<Wire> outputs;
	private ArrayList<Integer> outputWidths;
	private ArrayList<Integer> startBits; // which input bits drive output
	private ArrayList<Integer> stopBits; // which input bits drive output
	private Wire input;
	private int numOutputs;
	private int inputWidth;
	private ArrayList<ArrayList<DataValue>> outputValues;
	
	
	
	
	public Controller() {
		this(0,0);
	}
	
	public Controller(int numOutputs, int inputWidth) {
		outputs = new ArrayList<Wire>();
		outputWidths = new ArrayList<Integer>();
		startBits = new ArrayList<Integer>();
		stopBits = new ArrayList<Integer>();
		outputValues = new ArrayList<ArrayList<DataValue>>();
		this.setNumOutputs(numOutputs);
		this.setInputWidth(inputWidth);
	}
	
	@Override
	public void Update() throws Exception {
		for(int i=0;i<numOutputs;i++) {
			String drivingBits = input.getValue().toString(2).substring(startBits.get(i),stopBits.get(i));
			int x = Integer.parseInt(drivingBits);
			DataValue val = new DataValue(outputValues.get(i).get(x));
			outputs.get(i).setValue(val);
		}

	}

	@Override
	public void connectInputWire(Wire connectingWire, String inputName) {
		switch(inputName) {
		case "input":
			setInput(connectingWire);
		}
	}

	@Override
	public void connectOutputWire(Wire connectingWire, String outputName) {
		for(int i=0;i<numOutputs;i++) {
			if(outputName.equals("output"+i)) {
				outputs.set(i, connectingWire);
			}
		}
	}

	public Wire getInput() {
		return input;
	}

	public void setInput(Wire input) {
		this.input = input;
		this.input.addWireListener(new basicListener());
	}

	public int getNumOutputs() {
		return numOutputs;
	}

	public void setNumOutputs(int numOutputs) {
		this.numOutputs = numOutputs;
	}

	public int getInputWidth() {
		return inputWidth;
	}

	public void setInputWidth(int inputWidth) {
		this.inputWidth = inputWidth;
	}

	public void setStartStopBits(int i, int startBit, int stopBit) {
		this.startBits.set(i,startBit);
		this.stopBits.set(i,stopBit);
	}

	public void setOutputBitWidth(int i, int j) {
		
		this.outputWidths.set(i,j);		
	}

	public void addValues(int i, ArrayList<DataValue> values) {	
		outputValues.set(i, values);		
	}

	public ArrayList<Integer> getStartBits() {
		return startBits;
	}
	public ArrayList<Integer> getStopBits() {
		return stopBits;
	}

	public ArrayList<DataValue> getOutputValues(int i) {
		return outputValues.get(i);
	}

	public void initArrayLists() {
		for(int i=0;i<numOutputs;i++) {
			startBits.add(0);
			stopBits.add(0);
			outputWidths.add(0);
			outputValues.add(null);
			outputs.add(null);
		}
		
	}

	public void initValuesArray(int i,int size) {
		outputValues.set(i,new ArrayList<DataValue>());
		for(int j=0;j<size;j++) {
			outputValues.get(i).add(DataValue.ZERO);
		}
		
	}

}
