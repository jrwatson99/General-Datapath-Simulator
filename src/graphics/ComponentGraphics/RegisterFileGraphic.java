package graphics.ComponentGraphics;

import javafx.scene.shape.Shape;
import logic.components.memories.RegisterFile;

public class RegisterFileGraphic extends MemoryGraphic {

	private ComponentInputWireNode readAddress1;
	private ComponentInputWireNode readAddress2;
	private ComponentInputWireNode writeAddress;
	private ComponentInputWireNode writeData;
	private ComponentInputWireNode writeEn;
	private ComponentOutputWireNode readData1;
	private ComponentOutputWireNode readData2;
	
	public RegisterFileGraphic() {
		super();
		setComponent(new RegisterFile());
		readAddress1 = new ComponentInputWireNode(this);
		readAddress2 = new ComponentInputWireNode(this);
		writeAddress = new ComponentInputWireNode(this);
		writeData = new ComponentInputWireNode(this);
		writeEn = new ComponentInputWireNode(this);
		readData1 = new ComponentOutputWireNode(this);
		readData2 = new ComponentOutputWireNode(this);
	}
	
	@Override 
	public void updateLoc(double x, double y) {
		super.updateLoc(x, y);
		
		readAddress1.setStartX(x - readAddress1.getLength());
		readAddress1.setStartY(y + 15);
		readAddress1.setEndX(x);
		readAddress1.setEndY(y + 15);

		readAddress2.setStartX(x - readAddress2.getLength());
		readAddress2.setStartY(y + 30);
		readAddress2.setEndX(x);
		readAddress2.setEndY(y + 30);
                
		writeAddress.setStartX(x - writeAddress.getLength());
		writeAddress.setStartY(y + HEIGHT - 30);
		writeAddress.setEndX(x);
		writeAddress.setEndY(y + HEIGHT - 30);
        
        writeData.setStartX(x - writeData.getLength());
        writeData.setStartY(y + HEIGHT - 15);
        writeData.setEndX(x);
        writeData.setEndY(y + HEIGHT - 15);

        readData1.setStartX(x + WIDTH);
        readData1.setStartY(y + 15);
        readData1.setEndX(x + WIDTH + readData1.getLength());
        readData1.setEndY(y + 15);

        readData2.setStartX(x + WIDTH);
        readData2.setStartY(y + 30);
        readData2.setEndX(x + WIDTH + readData2.getLength());
        readData2.setEndY(y + 30);
        
        writeEn.setStartX(x + WIDTH/2);
        writeEn.setStartY(y);
        writeEn.setEndX(x + WIDTH/2);
        writeEn.setEndY(y - writeEn.getLength());
        

	}

	@Override
	public Shape[] getGraphics() {
		Shape[] graphics = {getRect(),
				readAddress1,
				readAddress2,
				writeAddress,
				writeData,
				writeEn,
				readData1,
				readData2,
		};
		return graphics;
	}
}
