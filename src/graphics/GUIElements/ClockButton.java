package graphics.GUIElements;

import graphics.ComponentGraphics.ClockGraphic;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class ClockButton extends Button {

    private ArrayList<ClockGraphic> clockList;
    private boolean isHigh;

    public ClockButton() {
        super("Toggle Clock");
        isHigh = false;
        clockList = new ArrayList<ClockGraphic>();
        setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                runCycle();
            }
        });
    }

    private void runCycle() {
        if (isHigh) {
            for (int i = 0; i < clockList.size(); i++) {
                clockList.get(i).setLow();
            }
            isHigh = false;
        }
        else {
            for (int i = 0; i < clockList.size(); i++) {
                clockList.get(i).setHigh();
            }
            isHigh = true;
        }
    }

    public void addClockToList(ClockGraphic clockGraphic) {
        clockList.add(clockGraphic);
    }
}
