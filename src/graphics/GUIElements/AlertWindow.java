package graphics.GUIElements;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertWindow extends Stage {
	
	public AlertWindow(String message) {
		super();
		this.setTitle("WARNING!");
		VBox layout = new VBox();
		layout.getChildren().add(new Label(message));
		Button ok = new Button("OK");
		ok.setOnAction(e->this.close());
		layout.getChildren().add(ok);
		layout.setPrefSize(250, 70);
		layout.setAlignment(Pos.CENTER);
		this.setScene(new Scene(layout));
		this.show();
	}
}
