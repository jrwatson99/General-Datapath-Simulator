package graphics;

import graphics.GUIElements.ComponentWindow;
import graphics.GUIElements.DatapathWindow;
import logic.ExecutionEnvironment;
import javafx.geometry.Rectangle2D;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GUI extends Application {
	DatapathWindow datapathWindow;
	ComponentWindow componentWindow;
    @Override
    public void start(Stage primaryStage) throws Exception {
        final String sceneTitle = new String("Datapath Simulator");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        double sceneHeight = primaryScreenBounds.getHeight() / 2;
        double sceneWidth = primaryScreenBounds.getWidth() / 2;

        BorderPane root = getDefaultRootAsBorderPane();
        primaryStage.setTitle(sceneTitle);
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private BorderPane getDefaultRootAsBorderPane() {

        datapathWindow = new DatapathWindow();
        componentWindow = new ComponentWindow(datapathWindow);
    	
        final Menu fileMenu = new Menu("File");
        final Menu optionsMenu = new Menu("Options");
        final Menu helpMenu = new Menu("Help");

        BorderPane root = new BorderPane();
        MenuItem bin = new MenuItem("binary");
        MenuItem dec = new MenuItem("decimal");
        MenuItem hex = new MenuItem("hexadecimal");
        bin.setOnAction(e -> {
        	ExecutionEnvironment.getExecutionEnvironment().setRadix(2);
        	datapathWindow.updateText();
        }); 
        dec.setOnAction( e -> {
        	ExecutionEnvironment.getExecutionEnvironment().setRadix(10);
        	datapathWindow.updateText();
        }); 
        hex.setOnAction(e -> {
    		ExecutionEnvironment.getExecutionEnvironment().setRadix(16);
        	datapathWindow.updateText();
        });
        optionsMenu.getItems().addAll(bin,dec,hex);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, optionsMenu, helpMenu);




        root.setCenter(datapathWindow);
        root.setTop(menuBar);
        root.setRight(componentWindow);

        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
