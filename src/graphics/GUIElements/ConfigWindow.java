package graphics.GUIElements;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class ConfigWindow extends Stage {
	private GridPane layout;
	private Button confirm;
	
	public ConfigWindow(String title) {
		this.setTitle(title);
		
		this.initModality(Modality.APPLICATION_MODAL);
		
		layout = new GridPane();
		layout.setVgap(10);
		layout.setHgap(10);
		layout.setPadding(new Insets(10,10,10,10));
		
		confirm = new Button("Apply");
		confirm.setOnAction(e -> closeWindow());
		
		VBox layout1 = new VBox();
		layout1.getChildren().addAll(new ScrollPane(layout),confirm);
		layout1.setAlignment(Pos.BOTTOM_CENTER);
		this.setScene(new Scene(layout1));
		
		this.setOnCloseRequest(e -> {
			e.consume();
			closeWindow();
			
		});
		this.setMaxHeight(800);
		
	}
	
	protected void closeWindow() {
		try {
			updateComponent();
			close();
		} catch (NumberFormatException e1) {
			new AlertWindow("Non Integer Number Detected");
		}
		catch( Exception e2) {
			new AlertWindow("Invalid Bit Data:  " + e2.getMessage());
			e2.printStackTrace();
		}
	}
	
	public abstract void updateComponent() throws Exception;
	
	public void addInputLine(InputLine input) {
		layout.addRow(layout.getChildren().size()/2,input.getName(), input.getText());
	}
	
	public GridPane getLayout() {
		return layout;
	}
	
	protected class InputLine{
		private Label name;
		private TextField text;
		
		public InputLine(String name){
			this.name =new Label(name+": ");
			text = new TextField();
			text.setPromptText("Enter value for "+name);
		}
		
		public InputLine(String name,String defualtText) {
			this.name =new Label(name+": ");
			text = new TextField(defualtText);
		}

		public Label getName() {
			return name;
		}

		public void setName(Label name) {
			this.name = name;
		}

		public TextField getText() {
			return text;
		}

		public void setText(TextField text) {
			this.text = text;
		}
		
	}
	
}
