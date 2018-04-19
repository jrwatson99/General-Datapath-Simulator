package graphics.GUIElements;

import graphics.ComponentGraphics.MemoryGraphic;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import logic.DataValue;
import logic.components.memories.DataMemory;

public class DataMemoryConfigWindow extends MemoryConfigWindow {
	GridPane layout;
	int offset;
	public DataMemoryConfigWindow(String title, MemoryGraphic memg) {
		super(title, memg);
		layout = this.getLayout();
		offset = layout.getChildren().size();
		try {
			addData(memg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Button confirm = new Button("Apply");
		confirm.setOnAction(e -> this.closeWindow());
				
		VBox layout1 = new VBox();
		layout1.getChildren().addAll(new ScrollPane(layout),confirm);
		layout1.setAlignment(Pos.BOTTOM_CENTER);
		Scene scene = new Scene(layout1);
		this.setScene(scene);
	}

	private void addData(MemoryGraphic memg) throws Exception {
		DataMemory d = ((DataMemory)memg.getComponent());
		int n =4;
		for(int i=0;i< (d.getSize()*8)/d.getDataWidth();i++) {
			for(int j = 0; j<n;j++ ) {
				if(i*n +j < (8*d.getSize())/d.getDataWidth()) {
					layout.addRow(i+offset/2,new Label("Word "+ (i*n+j)),new TextField(d.getWord(i*n +j).toString()));
				}
			}
		}
		
	}

	@Override
	public void updateComponent() throws Exception {
		DataMemory r = ((DataMemory)memg.getComponent());
		for(int i=0; i < (8*r.getSize())/r.getDataWidth();i++) {
			r.setWord(i,getWordValue(i));
		}
		super.updateComponent();
	}

	private DataValue getWordValue(int i) {
		return new DataValue(((TextField)getLayout().getChildren().get(i*2 + 1 + offset)).getText());
	}

}
