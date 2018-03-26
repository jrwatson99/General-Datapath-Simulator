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

        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override public void handle (ActionEvent e){

                //FIXME: ADD TO EXECUTION ENVIRONMENT
                AdderGraphic newAdderGraphic = new AdderGraphic();

                ObservableList<Node> rootChildren = ((HBox)((Button)e.getSource()).getParent().getParent().getParent().getParent().getParent().getParent()).getChildren();

                int i;
                for (i = 0; i < rootChildren.size(); i++) {
                    if (rootChildren.get(i) instanceof DatapathWindow) {
                        ((Pane)((DatapathWindow)rootChildren.get(i)).getContent()).getChildren().addAll(newAdderGraphic.getGraphics());
                        newAdderGraphic.updateLoc(100, 100);
                    }
                }

                datapathWindow.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (e.getEventType() == MouseEvent.MOUSE_MOVED) {
                            newAdderGraphic.updateLoc(e.getX(), e.getY());
                        }
                        else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                            datapathWindow.removeEventHandler(MouseEvent.ANY, this);
                        }
                    }
                });
            }

        });

        this.getChildren().addAll(componentName, addButton);
    }
}
