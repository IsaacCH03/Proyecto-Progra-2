package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ToolBar;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TableView;

public class GUIBillingController {
	@FXML
	private Label lblBilling;
	@FXML
	private TableView tvShowBill;
	@FXML
	private Button btnBack;
	@FXML
	private ToolBar toolBAR;
	@FXML
	private Button btnMinimize;
	@FXML
	private Button btnClose;
	
	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	public void backWindow(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIStartServer.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/img/icono.png")));
		stage.setScene(scene);
		stage.show();
		Stage temp = (Stage) this.btnBack.getScene().getWindow();
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
