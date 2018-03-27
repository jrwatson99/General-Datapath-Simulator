package graphics.GUIElements;

import graphics.ComponentGraphics.*;
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
import logic.ExecutionEnvironment;
import logic.Wire;
import logic.components.Adder;

import java.awt.*;

public class ComponentListElement extends HBox {

    public ComponentListElement(String cName, DatapathWindow datapathWindow) {
        Label componentName = new Label(cName);

        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override public void handle (ActionEvent e){

                //FIXME: ADD TO EXECUTION ENVIRONMENT

//                ComponentListElement thisComponentListElement = ((ComponentListElement)((Button)e.getSource()).getParent());
//                String componentName = ((Label)thisComponentListElement.getChildren().get(0)).getText();

                ComponentGraphic newComponentGraphic;
                switch (componentName.getText()) { //much simpler
                    case "Adder":
                        newComponentGraphic = new AdderGraphic();
                        break;
                    case "Multiplexer":
                        newComponentGraphic = new MUXGraphic();
                        break;
                    case "ALU":
                        newComponentGraphic = new ALUGraphic();
                        break;
                    case "Comparator":
                        newComponentGraphic = new ComparatorGraphic();
                        break;
                    default:
                        newComponentGraphic = null;
                        break;
                }
                if (newComponentGraphic != null) {
//NOTE THIS
                	((Pane)datapathWindow.getContent()).getChildren().addAll(newComponentGraphic.getGraphics());    // no loop                

                    final ComponentGraphic finalNewComponentGraphic = newComponentGraphic;

                    datapathWindow.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            if (e.getEventType() == MouseEvent.MOUSE_MOVED) {
                                finalNewComponentGraphic.updateLoc(e.getX(), e.getY());
                            } else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                                datapathWindow.removeEventHandler(MouseEvent.ANY, this);
                            }
                        }
                    });
                }
            }

        });

        this.getChildren().addAll(componentName, addButton);
    }
}
