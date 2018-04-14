package logic.components.memories;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import logic.DataValue;
import logic.Wire;

public class DataMemory extends Memory{
	
	private Wire readEnable;
	private byte[] mem;
	private int size; // in bytes, memories are limited to 2 GB for this simulator
	
	public Wire getReadEnable() {return readEnable;	}
	public void setReadEnable(Wire readEnable) {this.readEnable = readEnable;}
	public int getSize() { return size;}


	public DataMemory(){
		this(1024);
	}
	public DataMemory(int size) {
		this(size,32,"Data Memory");
	}
	public DataMemory(int size,int width) {
		this(size,width,"Data Memory");
	}
 	public DataMemory(int size,int width,String name){
		super(name);
		setDataWidth(width);
//		mem=new ArrayList<Byte>();
//		for(int i=0;i<size;i++) {
//			mem.add(new Byte((byte)0));
//		}
		mem=new byte[size];
		this.size=size;
	}
	
	
	public void resize(int newSize) {
		mem=java.util.Arrays.copyOf(mem, newSize);
		this.size=newSize;
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
		int address = getAddress().getValue().intValue();
		int bits = getWriteData().getValue().bitLength();
		if(getWriteEnable().getValue().intValue()==1) {
			if(determineBitValidity(getWriteData().getValue(),getDataWidth())) {
				byte[] data = getWriteData().getValue().toByteArray();
				for(int i=0; i <= (bits-1)/8;i++) {
					index=address+i;
					if(index < 0 || index >= size) {
						throw new Exception("Error: Out of bounds memory write in "+ getName()+address);
					}
					//mem.set(index,data[bits/8-i]); 
					mem[index]=data[bits/8-i];
				}
				for(int i=bits/8+1; i<getDataWidth()/8;i++) {
					index=address+i;
					//mem.set(index,new Byte((byte) 0));
					mem[index]=0;
				}
			}
			else {
				throw new Exception("Error: Invalid bit width for write data in "+ getName()+" "+getWriteData().getValue().toString());
			}
		}
		else if(getWriteEnable().getValue().intValue()!=0) {
			throw new Exception("Error: Invalid value for write enable in "+ getName());
		}
	}
	
	
	/**
	 * updates output wire with <datawidth> bits from memory at <address>
	 * @author Matthew Johnson
	 * @version 0.1
	 * @throws Exception
	 * @return void
	 */
	@Override
	public void onNegEdgeClk() throws Exception {
		int address=getAddress().getValue().intValue();
		int index;
		if(getReadEnable().getValue().intValue()==1) {
			byte[] data = new byte[getDataWidth()/8];
			for(int i=0; i < (getDataWidth()/8); i++) {
				index = address+i;
				data[getDataWidth()/8-i-1]=mem[index];//mem.get(index);
				if(index >= size || index < 0) {
					throw new Exception("Error: Out of Bounds memory read in "+ getName());
				}
			}
			getReadData().setValue(new DataValue(data));
		}
		else if(getReadEnable().getValue().intValue()!=0) {
			throw new Exception("Error: Invalid value for read enable in "+ getName());
		}
	}

	@Override
	public void Update() throws Exception { // i didnt want this here but it kept giving me an error
		super.update();
	}
	
	/**
	 * used for testing memory read/write functionality. tested for 8-64 bitwidth and up to 2 GB of memory
	 * @author Matthew Johnson
	 * @version 0.1
	 * @throws Exception
	 * @return void
	 */
	@SuppressWarnings("unused")
	private static class tester{
		private static Wire clk;
		private static Wire readData;
		private static Wire readEn;
		private static Wire writeData;
		private static Wire writeEn;
		private static Wire address;
		private static DataMemory memory;
		
		public static void main(String args[]) {
			memory = new DataMemory(32,32);
			
			clk = new Wire();
			clk.setValue(DataValue.ZERO);
			memory.setCLK(clk);
			readData = new Wire();
			memory.setReadData(readData);
			writeData = new Wire();
			memory.setWriteData(writeData);
			readEn = new Wire();
			memory.setReadEnable(readEn);
			writeEn = new Wire();
			memory.setWriteEnable(writeEn);
			address = new Wire();
			memory.setAddress(address);
			
			initMemTest();
			printMem();
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(memory.mem);
			
		}
		/**
		 * prints contents of memory to console for inspection, used to test read/write functionality.
		 * @author Matthew Johnson
		 * @version 0.1
		 * @throws Exception
		 * @return void
		 */
		private static void printMem() {
			readEn.setValue(DataValue.ONE);
			address.setValue(DataValue.ZERO);
			clk.setValue(DataValue.ONE);
			clk.setValue(DataValue.ZERO);				
			int bytes=memory.getDataWidth()/8;
			for(int i=0;i<=memory.size-bytes;i+=bytes) {
				System.out.println(address.getValue().intValue()+" "+readData.getValue().toString());
				address.setValue(new DataValue(i));
				clk.setValue(DataValue.ONE);
				clk.setValue(DataValue.ZERO);				
			}
			System.out.println(address.getValue().intValue()+" "+readData.getValue().toString());
		}
		
		/**basic initialization of memory. sets each cell to its address, will crash if address is larger than suported datawidth.
		 * @author Matthew Johnson
		 * @version 0.1
		 * @throws Exception
		 * @return void
		 */
		private static void initMemTest() {
			writeEn.setValue(DataValue.ONE);
			DataValue i;
			final DataValue UPPER_BOUNDS = new DataValue(memory.size-memory.getDataWidth()/8);
			final DataValue INCREMENT = new DataValue(memory.getDataWidth()/8);
			for(i = DataValue.ZERO; i.compareTo(UPPER_BOUNDS) < 0; i.add(INCREMENT)) {
				writeData.setValue(new DataValue(i));
				address.setValue(new DataValue(i));
				clk.setValue(DataValue.ONE);
				clk.setValue(DataValue.ZERO);
			}
			writeEn.setValue(DataValue.ZERO);
		}
	}

	public void setWord(int wordNum, DataValue wordValue) throws Exception {
		int bits = wordValue.bitLength();
		byte[] data = wordValue.toByteArray();
		for(int i=0; i <= (bits-1)/8;i++) {
			mem[wordNum*getDataWidth()/8 + i]=data[bits/8-i];
		}
		for(int i=bits/8+1; i<getDataWidth()/8;i++) {
			mem[wordNum*getDataWidth()/8+i]=0;
		}
		
	}
	public DataValue getWord(int wordNum) throws Exception {
		byte[] data = new byte[getDataWidth()/8];
		for(int i=0; i < (getDataWidth()/8); i++) {
			
			data[getDataWidth()/8-i-1]=mem[(getDataWidth()/8)*wordNum+i];//mem.get(index);
		}
		return new DataValue(data);
	}
	@Override
	public void connectInputWire(Wire connectingWire, String inputName) {
		switch(inputName) {
		case "CLK":   /// are we doing this?
			this.setCLK(connectingWire);
			break;
		case "address":
			setAddress(connectingWire);
			break;
		case "readEn":
			setReadEnable(connectingWire);
			break;
		case "writeEn":
			setWriteEnable(connectingWire);
			break;
		case "writeData":
			setWriteData(connectingWire);
			break;
		default:
			System.out.println("ERROR: Invalid Input name" + inputName);
		
		}
		
	}
	@Override
	public void connectOutputWire(Wire connectingWire, String outputName) {
		switch(outputName) {
		case "readData":
			setReadData(connectingWire);
			break;
		default:
			System.out.println("ERROR: Invalid Output name" + outputName);
		
		}
		
	}
	
	public void initFromFile(String fileName, int radix) throws Exception {
		File file = new File(fileName);
		//file.createNewFile();
		if(file.canRead()) {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line;
			int i=0;
			while((line = in.readLine()) != null) {
				try {
					this.setWord(i, new DataValue(line,radix));
					i++;
				}
				catch (Exception e) {
					throw new Exception("File to Long?");
				}
			}
			in.close();
		}
		else {
			throw new Exception("Invalid File");
		}
		
	}
	
}
