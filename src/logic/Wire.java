package logic;



public class Wire {
	private DataValue value;
	private WireListener listener;
	
	public Wire(){
		this(null);
	}
	
	public Wire(WireListener listener){
		this.listener=listener;
		value=new DataValue();
	}
	
	public void setValue(DataValue newValue) {
		if(this.value!=newValue && listener != null) {
			value = newValue;
			listener.onValueChange();
		}
		else {
			value = newValue;
		}
	}
	
	public DataValue getValue() {return value;}

	public void addWireListener(WireListener wireListener) {
		listener=wireListener;
	}
}
