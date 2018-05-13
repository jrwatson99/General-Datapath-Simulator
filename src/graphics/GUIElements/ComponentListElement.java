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
import javafx.scene.text.Text;
import logic.ExecutionEnvironment;
import logic.Wire;
import logic.components.Adder;

import java.awt.*;
import java.util.Arrays;

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
                switch (cName) { //much simpler
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
                    case "Data Memory":
                    	newComponentGraphic = new DataMemoryGraphic();
                    	break;
                    case "Register File":
                    	newComponentGraphic = new RegisterFileGraphic();
                    	break;
                    case "Bit Shifter":
                    	newComponentGraphic = new BitShifterGraphic();
                    	break;
                    case "Bit Extender":
                    	newComponentGraphic = new BitExtenderGraphic();
                    	break;
                    case "Wire Junction":
                    	newComponentGraphic = new WireJunctionGraphic();
                    	break;
                    case "Wire Splitter":
                    	newComponentGraphic = new WireSplitterGraphic();
                    	break;
                    case "Constant Value":
                    	newComponentGraphic = new ConstantValueGraphic();
                    	break;
                    case "Controller":
                    	newComponentGraphic = new ControllerGraphic();
                    	break;
                    case "Clock":
                    	newComponentGraphic = new ClockGraphic();
                    	break;
                    case "Register":
                    	newComponentGraphic = new RegisterGraphic();
                    	break;
                    default:
                        newComponentGraphic = null;
                        break;
                }
                if (newComponentGraphic != null) {

                    if (ExecutionEnvironment.getExecutionEnvironment().getPlacingWireStatus()) {
                        ExecutionEnvironment.getExecutionEnvironment().stopPlacingWire();
                    }

                	((Pane)datapathWindow.getContent()).getChildren().addAll(newComponentGraphic.getGraphics());    
                	((Pane)datapathWindow.getContent()).getChildren().add(newComponentGraphic.getText());
                	((Pane)datapathWindow.getContent()).getChildren().addAll(newComponentGraphic.getValueText());
                    final ComponentGraphic finalNewComponentGraphic = newComponentGraphic;

                    datapathWindow.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            if (e.getEventType() == MouseEvent.MOUSE_MOVED) {
                                finalNewComponentGraphic.updateLoc(e.getX(), e.getY());
                            }
                            else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                                boolean collisionDetected = false;
                                int i = 0;
                                while (i < ((Pane)datapathWindow.getContent()).getChildren().size() && !collisionDetected) {
                                    if (((Pane)datapathWindow.getContent()).getChildren().get(i) instanceof javafx.scene.shape.Shape) {
                                        Node potentiallyCollidingShape = ((Pane)datapathWindow.getContent()).getChildren().get(i);
                                        javafx.scene.shape.Shape[] newComponentGraphicList = newComponentGraphic.getGraphics();
                                        if (!(Arrays.asList(newComponentGraphicList).contains(potentiallyCollidingShape) || potentiallyCollidingShape instanceof Text)) {
                                            for (int j = 0; j < newComponentGraphicList.length; j++) {
                                                if (newComponentGraphicList[j].intersects(potentiallyCollidingShape.getBoundsInParent())) {
                                                    collisionDetected = true;
                                                }
                                            }
                                        }
                                    }
                                    i++;
                                }
                                if (!collisionDetected) {
                                    datapathWindow.removeEventHandler(MouseEvent.ANY, this);
                                    finalNewComponentGraphic.config();
                                    if (finalNewComponentGraphic instanceof MUXGraphic) {
                                        ((MUXGraphic)finalNewComponentGraphic).changeTextLoc(e.getX(), e.getY());
                                    }
                                }
                            }
                        }
                    });
                }
            }

        });

        this.getChildren().addAll(componentName, addButton);
    }
}
