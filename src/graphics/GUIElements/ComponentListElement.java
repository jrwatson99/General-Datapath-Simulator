package graphics.GUIElements;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ComponentListElement extends HBox {

    public ComponentListElement(String cName) {
        Label componentName = new Label(cName);
        Button addButton = new Button("Add");
        this.getChildren().addAll(componentName, addButton);
    }
}
