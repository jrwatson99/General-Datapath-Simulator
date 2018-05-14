package graphics.GUIElements;

import graphics.ComponentGraphics.ComponentOutputWireNode;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import logic.ExecutionEnvironment;

public class DatapathWindow extends ScrollPane {
	  Pane datapathPane;
    public DatapathWindow() {
        super();

        this.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setPrefViewportWidth(Screen.getPrimary().getVisualBounds().getWidth() * 6 / 7);

        datapathPane = new Pane();

        this.setContent(datapathPane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        ExecutionEnvironment.get().setDataPathWindow(this);
    }
    
    public Pane getPane() {return datapathPane;}
    public void updateText() {
    	for(Node cp : datapathPane.getChildren()) {
    		if(cp.getClass()==ComponentOutputWireNode.class) {
    			((ComponentOutputWireNode)cp).updateText();
    		}
    	}
    }
}
