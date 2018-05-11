package graphics.GUIElements;

import graphics.ComponentGraphics.BitShifterGraphic;
import graphics.ComponentGraphics.ComponentGraphic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import logic.components.BitShifter;

import java.util.ArrayList;

public class BitShifterConfigWindow extends ConfigWindow{
    private BitShifterGraphic componentGraphic;


    public BitShifterConfigWindow(String title, BitShifterGraphic componentGraphic) {
        super(title);
        this.componentGraphic = componentGraphic;
        this.addInputLine(new ConfigWindow.InputLine("Name"));
        this.addShiftDirChoiceLine(new ShiftDirChoiceLine());
        this.addInputLine(new ConfigWindow.InputLine("Shift Amount"));
    }

    @Override
    public void updateComponent() throws Exception {
        ((BitShifter)componentGraphic.getComponent()).setShiftDirection(getShiftDir());
        componentGraphic.setName(getName());
        ((BitShifter)componentGraphic.getComponent()).setShiftAmount(getShiftAmount());
    }

    public GridPane getLayout() {
        return layout;
    }

    private void addShiftDirChoiceLine(ShiftDirChoiceLine input) {
        layout.addRow(layout.getChildren().size()/2,input.getName(), input.getChoiceBox());
    }

    protected class ShiftDirChoiceLine {
        private Label name;
        private ChoiceBox<String> choiceBox;

        public ShiftDirChoiceLine() {
            this.name =new Label("Direction: ");
            choiceBox = new ChoiceBox(FXCollections.observableArrayList("Left", "Right"));
        }

        public Label getName() {
            return name;
        }

        public void setName(Label name) {
            this.name = name;
        }

        public ChoiceBox getChoiceBox() { return choiceBox;}
    }

    public String getName() {
        return ((TextField)getLayout().getChildren().get(1)).getText();
    }
    public String getShiftDir() {
        return (String)((ChoiceBox)getLayout().getChildren().get(3)).getValue();
    }
    public int getShiftAmount() {
        return Integer.parseInt(((TextField)getLayout().getChildren().get(5)).getText());
    }
}