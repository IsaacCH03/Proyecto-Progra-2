package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIStartServerController {
	@FXML
	private Pane paneContain;
	@FXML
	private Button btnCRUDProduct;
	@FXML
	private Button btnShowFact;
	@FXML
	private Button btnMinimize;
	@FXML
	private Button btnClose;
	
	@FXML
	private ToolBar toolBAR;
	
	private double xOffset = 0;
	private double yOffset = 0;

	private LaunchServer server;
	
	
	public void initialize() {
		new Thread(() -> {
			server = new LaunchServer();
		}).start();
		
		
	}
	
	@FXML
	public void windowCRUDProduct(ActionEvent event) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUICrudProduct.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/img/icono.png")));
		stage.setScene(scene);
		stage.show();
		Stage temp = (Stage) this.btnCRUDProduct.getScene().getWindow();
		temp.close();
		
	}
	// Event Listener on Button[#btnShowFact].onAction
	@FXML
	public void windowFact(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIBilling.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/img/icono.png")));
		stage.setScene(scene);
		stage.show();
		Stage temp = (Stage) this.btnCRUDProduct.getScene().getWindow();
		temp.close();
	}
	
	
	
	@FXML
	private void minimizeWindow() {
		Stage stage = (Stage) toolBAR.getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	private void closeWindow() {
		Stage stage = (Stage) toolBAR.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleMousePressed(MouseEvent event) {
		xOffset = event.getSceneX();
		yOffset = event.getSceneY();
	}

	@FXML
	private void handleMouseDragged(MouseEvent event) {
		Stage stage = (Stage) toolBAR.getScene().getWindow();
		stage.setX(event.getScreenX() - xOffset);
		stage.setY(event.getScreenY() - yOffset);
	}

}

