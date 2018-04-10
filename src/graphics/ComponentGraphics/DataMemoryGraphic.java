package graphics.ComponentGraphics;

import javafx.scene.shape.Shape;
import logic.components.memories.*;


public class DataMemoryGraphic extends MemoryGraphic {
	private ComponentInputWireNode address;
	private ComponentInputWireNode writeData;
	private ComponentInputWireNode writeEn;
	private ComponentInputWireNode readEn;
	private ComponentOutputWireNode readData;

	public DataMemoryGraphic() {
		super();
		setComponent(new DataMemory());
		address = new ComponentInputWireNode(this);
		writeData = new ComponentInputWireNode(this);
		writeEn = new ComponentInputWireNode(this);
		readEn = new ComponentInputWireNode(this);
		readData = new ComponentOutputWireNode(this);
	}
	
	@Override 
	public void updateLoc(double x, double y) {
		super.updateLoc(x, y);

        address.setStartX(x - address.getLength());
        address.setStartY(y + 15);
        address.setEndX(x);
        address.setEndY(y + 15);
                
        writeData.setStartX(x - writeData.getLength());
        writeData.setStartY(y + 30);
        writeData.setEndX(x);
        writeData.setEndY(y + 30);

        readData.setStartX(x + WIDTH);
        readData.setStartY(y + 30);
        readData.setEndX(x + WIDTH + readData.getLength());
        readData.setEndY(y + 30);
        
        writeEn.setStartX(x + WIDTH/2);
        writeEn.setStartY(y);
        writeEn.setEndX(x + WIDTH/2);
        writeEn.setEndY(y - writeEn.getLength());
        
        readEn.setStartX(x + WIDTH/2);
        readEn.setStartY(y+HEIGHT);
        readEn.setEndX(x + WIDTH/2);
        readEn.setEndY(y + HEIGHT+ writeEn.getLength());

	}

	@Override
	public Shape[] getGraphics() {
		Shape[] graphics = {getRect(),
				address,
				writeData,
				writeEn,
				readEn,
				readData,
		};
		return graphics;
	}
}
