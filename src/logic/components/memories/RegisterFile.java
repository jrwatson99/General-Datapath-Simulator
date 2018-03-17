package logic.components.memories;

import java.util.ArrayList;

import logic.*;

public class RegisterFile extends Memory{
	private Wire readAddress1;
	private Wire readAddress2;
	private Wire readData2;
	private ArrayList<DataValue> registers;
	private int numRegisters;
	
	public void setReadAddress1(Wire newAddress) {readAddress1=newAddress;}
	public Wire getReadAddress1() {return readAddress1;}
	public void setReadAddress2(Wire newAddress) {readAddress2=newAddress;}
	public Wire getReadAddress2() {return readAddress2;}
	public void setReadData2(Wire newAddress) {readData2=newAddress;}
	public Wire getReadData2() {return readData2;}
	public int getNumRegisters() {return numRegisters;}
	public void setNumRegisters(int numRegisters) {this.numRegisters = numRegisters;}
	
	
	public RegisterFile() {
		this(32,32,"Register File");
	}
	public RegisterFile(int numRegisters, int registerWidth) {
		this(numRegisters,registerWidth,"Register File");
	}
	public RegisterFile(int numRegisters, int registerWidth,String name) {
		super(name);
		this.numRegisters=numRegisters;
		setDataWidth(registerWidth);
		registers = new ArrayList<DataValue>();
		registers.ensureCapacity(numRegisters);
	}
	
	@Override
	public void onPosEdgeClk() throws Exception {
		int address = getAddress().getValue().intValue();
		if(getWriteEnable().getValue().intValue()==1) {
			if(determineBitValidity(getWriteData().getValue(),getDataWidth())) {
				if(address >= numRegisters) {
					throw new Exception("Error: Invalid register write address in "+ getName());
				}
				registers.set(address, getWriteData().getValue());
			}
			else {
				throw new Exception("Error: Invalid bit width for write data in "+ getName());
			}
		}
		else if(getWriteEnable().getValue().intValue()!=0) {
			throw new Exception("Error: Invalid value for write enable in "+ getName());
		}
	}
	@Override
	public void onNegEdgeClk() throws Exception {
		int ad1=getReadAddress1().getValue().intValue();
		int ad2=getReadAddress2().getValue().intValue();
		if(ad1 >= numRegisters || ad1 < 0) {
			throw new Exception("Error: Invalid read address 1 in "+ getName());
		}
		if(ad2 >= numRegisters || ad2 < 0) {
			throw new Exception("Error: Invalid read address 2 in "+ getName());
		}
		getReadData().setValue(registers.get(ad1));
		getReadData2().setValue(registers.get(ad2));
	}
	@Override
	public void Update() throws Exception {
		super.update();
	}
	//TODO write a tester class
	
}
