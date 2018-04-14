package graphics.GUIElements;

import graphics.ComponentGraphics.AdderGraphic;
import graphics.ComponentGraphics.ComponentGraphic;
import graphics.ComponentGraphics.ComponentOutputWireNode;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Screen;

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
    }
    public void updateText() {
    	for(Node cp : datapathPane.getChildren()) {
    		if(cp.getClass()==ComponentOutputWireNode.class) {
    			((ComponentOutputWireNode)cp).updateText();
    		}
    	}
    }
}
