package graphics;

import graphics.ComponentGraphics.ALUGraphic;
import graphics.GUIElements.ALUConfigWindow;
import graphics.GUIElements.ComponentWindow;
import graphics.GUIElements.DatapathWindow;
import javafx.geometry.Rectangle2D;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GUI extends Application {

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
        final Menu fileMenu = new Menu("File");
        final Menu optionsMenu = new Menu("Options");
        final Menu helpMenu = new Menu("Help");

        BorderPane root = new BorderPane();
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, optionsMenu, helpMenu);

        DatapathWindow datapathWindow = new DatapathWindow();
        ComponentWindow componentWindow = new ComponentWindow(datapathWindow);



        //root.getChildren().addAll(menuBar,datapathWindow,componentWindow);
        root.setCenter(datapathWindow);
        root.setTop(menuBar);
        root.setRight(componentWindow);

        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
