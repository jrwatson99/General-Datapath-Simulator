package graphics.GUIElements;

import graphics.ComponentGraphics.AdderGraphic;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Screen;

public class DatapathWindow extends ScrollPane {
    public DatapathWindow() {
        super();

        this.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setPrefViewportWidth(Screen.getPrimary().getVisualBounds().getWidth() * 4 / 5);

        Pane datapathPane = new Pane();

        this.setContent(datapathPane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
    }
}
