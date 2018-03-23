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
		for(int i=0;i<numRegisters;i++) {
			registers.add(DataValue.ZERO);
		}
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
	@SuppressWarnings("unused")
	private static class Tester {
		private static Wire clk;
		private static Wire readData1;
		private static Wire readData2;
		private static Wire writeData;
		private static Wire writeEn;
		private static Wire writeAddress;
		private static Wire readAddress1;
		private static Wire readAddress2;
		private static RegisterFile regs;
		
		public static void main(String args[]) {
			regs = new RegisterFile(256,8);
			clk = new Wire();
			clk.setValue(DataValue.ZERO);
			regs.setCLK(clk);
			readData1 = new Wire();
			regs.setReadData(readData1);
			readData2 = new Wire();
			regs.setReadData2(readData2);
			readAddress1 = new Wire();
			regs.setReadAddress1(readAddress1);
			readAddress2 = new Wire();
			regs.setReadAddress2(readAddress2);
			writeData = new Wire();
			regs.setWriteData(writeData);
			writeEn = new Wire();
			regs.setWriteEnable(writeEn);
			writeAddress = new Wire();
			regs.setAddress(writeAddress);
			
			initMemTest();
			printMem();
			
		}

		private static void printMem() {
			for(int i=0; i<regs.numRegisters;i+=2) {
				readAddress1.setValue(new DataValue(i));
				readAddress2.setValue(new DataValue(i+1));
				clk.setValue(DataValue.ONE);
				clk.setValue(DataValue.ZERO);
				System.out.println("Reg "+ i + ": " + readData1.getValue().toString() + "\nReg " + (i+1) +  ": " + readData2.getValue().toString() );
			}
		}

		private static void initMemTest() {
			DataValue x;
			writeEn.setValue(DataValue.ONE);
			for(int i=0; i<regs.numRegisters;i++) {
				x=new DataValue(i);
				writeAddress.setValue(x);
				writeData.setValue(new DataValue(i));
				clk.setValue(DataValue.ONE);
				clk.setValue(DataValue.ZERO);
			}
			writeEn.setValue(DataValue.ZERO);
		}
		
	}
	
}
