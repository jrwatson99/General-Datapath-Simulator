package graphics.GUIElements;

import graphics.ComponentGraphics.AdderGraphic;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import logic.components.Adder;

import java.awt.*;

public class ComponentListElement extends HBox {

    public ComponentListElement(String cName, DatapathWindow datapathWindow) {
        Label componentName = new Label(cName);

        Pane pane;
        if (datapathWindow.getContent() instanceof Pane) {
            pane = (Pane)datapathWindow.getContent();
            ObservableList<Node> paneChildren = pane.getChildren();
        }


        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            //FIXME: ADD TO EXECUTION ENVIRONMENT
            AdderGraphic newAdderGraphic = new AdderGraphic();



            paneChildren.add(newAdderGraphic);

            @Override public void handle(ActionEvent e) {
                datapathWindow.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        newAdderGraphic.updateLoc(e.getX(), e.getY());
                    };
                });
            }
        });

        this.getChildren().addAll(componentName, addButton);
    }
}
