package logic.components.memories;

import java.math.BigInteger;
import java.util.ArrayList;

import logic.Wire;

public class DataMemory extends Memory{
	
	private Wire readEnable;
	private ArrayList<Byte> mem;
	private int size; // in bytes, memories are limited to 2 GB for this simulator
	
	public Wire getReadEnable() {return readEnable;	}
	public void setReadEnable(Wire readEnable) {this.readEnable = readEnable;}



	DataMemory(){
		this(1024,"Data Memory");
	}
	
	DataMemory(int size,String name){
		super(name);
		mem=new ArrayList<Byte>();
		mem.ensureCapacity(size);
		this.size=size;
	}
	
	
	
	
	@Override
	/**
	 * updates data memory from the value on the wire. may be updated in the future to allow user control of when read/write happens.
	 * @author Matthew Johnson
	 * @version 0.1
	 * @throws Exception
	 * @return void
	 */
	public void onPosEdgeClk() throws Exception {
		int index;
		if(getWriteEnable().getValue().intValue()==1) {
			if(determineBitValidity(getWriteData().getValue(),getDataWidth())) {
				byte[] data = getWriteData().getValue().toByteArray();
				for(int i=0; i < (getDataWidth()/8);i++) {
					index=getAddress().getValue().intValue()+i;
					if(index < 0 || index >= size) {
						throw new Exception("Error: Out of bounds memory write in "+ getName());
					}
					mem.add(index,data[i]);
					
				}
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
	public void onNegEdgeClk() {
		
	}

	@Override
	public void Update() throws Exception { // i didnt want this here but it kept giving me an error
		super.update();
	}


}
