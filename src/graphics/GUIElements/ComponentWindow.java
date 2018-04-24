package graphics.GUIElements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import logic.ExecutionEnvironment;


public class ComponentWindow extends ScrollPane {

    private static final String componentNames[] = {
            "Adder",
            "Multiplexer",
            "ALU",
            "Comparator",
            "Data Memory",
            "Register File",
            "Bit Shifter",
            "Bit Extender",
            "Wire Junction",
            "Wire Splitter",
            "Constant Value",
            "Controller",
            "Clock",
            "Register"
            };

    public ComponentWindow(DatapathWindow datapathWindow) {
        super();

        this.setFitToWidth(true);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setPrefViewportWidth(Screen.getPrimary().getVisualBounds().getWidth() / 7);

        Button placingWireButton = new Button("Place Wire");
        placingWireButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
            public void handle(ActionEvent e) {
               ExecutionEnvironment.getExecutionEnvironment().togglePlacingWire();
           }
        });

        VBox componentList = new VBox();
        componentList.getChildren().add(placingWireButton);

        int i = 0;
        for (i = 0; i < componentNames.length; i++) {
            componentList.getChildren().add(new ComponentListElement(componentNames[i], datapathWindow));
        }

        this.setContent(componentList);
    }
}
