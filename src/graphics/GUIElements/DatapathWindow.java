package graphics.GUIElements;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Screen;

public class DatapathWindow extends ScrollPane {
    public DatapathWindow() {
        super();

        this.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setPrefViewportWidth(Screen.getPrimary().getVisualBounds().getWidth() * 4 / 5);

        this.getChildren().add(new Label("Datapath Box"));
    }
}
