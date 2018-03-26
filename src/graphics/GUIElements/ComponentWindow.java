package graphics.GUIElements;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.util.ArrayList;

public class ComponentWindow extends ScrollPane {

    private static final String componentNames[] = {"Adder", "Multiplexer"};

    public ComponentWindow() {
        super();

        this.setFitToWidth(true);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setPrefViewportWidth(Screen.getPrimary().getVisualBounds().getWidth() / 5);

        VBox componentList = new VBox();
        int i = 0;
        for (i = 0; i < componentNames.length; i++) {
            componentList.getChildren().add(new ComponentListElement(componentNames[i]));
        }

        this.setContent(componentList);
    }
}
