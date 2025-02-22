package business;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
	    try {
	    	primaryStage.initStyle(StageStyle.UNDECORATED);
	        Parent root = FXMLLoader.load(getClass().getResource("/presentation/GUIStartServer.fxml"));
	        Scene scene = new Scene(root);
	        primaryStage.setTitle("Server");
//	        primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/Img/icono.png")));
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
    public static void main(String[] args) {
        launch(args);
    }
}
