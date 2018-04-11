package logic.components.memories;

import logic.Wire;
import logic.WireListener;
import logic.components.Component;

public abstract class Memory extends Component{
	private String name;
	private Wire clk;
	private Wire writeEnable;
	private Wire readData;
	private Wire writeData;
	private Wire address; // for register FIle this will be write address.
	private int addressWidth;
	private int dataWidth;
	
	public void setName(String newName) { name=newName;}
	public String getName() {return name;}
	public void setCLK(Wire newCLK) {
		clk=newCLK;
		clk.addWireListener(new ClkListener());
		}
	public Wire getCLK() { return clk;}
	public void setWriteEnable(Wire newEnable) {writeEnable=newEnable;}
	public Wire getWriteEnable() {return writeEnable;}
	public void setWriteData(Wire newData) {writeData=newData;}
	public Wire getWriteData() {return writeData;}
	public void setReadData(Wire newData) {readData=newData;}
	public Wire getReadData() {return readData;}
	public void setAddress(Wire newAddress) {address=newAddress;}
	public Wire getAddress() {return address;}
	public int getAddressWidth() {return addressWidth;}
	public void setAddressWidth(int addressWidth) {this.addressWidth = addressWidth;}
	public int getDataWidth() {return dataWidth;}
	public void setDataWidth(int dataWidth) {this.dataWidth = dataWidth;}
	public int getSize() {
		if(this.getClass()==RegisterFile.class) {
			return ((RegisterFile)this).getNumRegisters();
		}
		else {
			return ((DataMemory)this).getSize();
		}
	}
	
	public Memory(){
		this("invalid");
	}
	
	public Memory(String name) {
		this.name=name;
		setSignedness(Signedness.UNSIGNED);
	}
	
	public abstract void resize(int newSize);
	
	/**
	 * determines which clock edge has occurred and calls the appropriate function
	 * @author Matthew Johnson
	 * @version 0.1
	 * @throws Exception
	 * @return void
	 */
	public void update() throws Exception{
		if(clk.getValue().intValue()==1) {
			this.onPosEdgeClk();
		}
		else if(clk.getValue().intValue()==0) {
			this.onNegEdgeClk();
		}
		else {
			throw new Exception("Invalid clock value in memory "+this.name);
		}
	}
	
	public abstract void onPosEdgeClk() throws Exception;
	public abstract void onNegEdgeClk() throws Exception;
	/**
	 * listens for clock edge and calls update
	 * @author Matthew Johnson
	 * @version 0.1
	 */
	private class ClkListener implements WireListener {

		@Override
		public void onValueChange(){
			
			try {
				update();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}			
		}
		
	}
}
