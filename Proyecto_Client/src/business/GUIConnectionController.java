package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import domain.Utils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class GUIConnectionController {
    @FXML
    private TextField tfIp;
    @FXML
    private TextField tfPort;
    @FXML
    private Label lblsetMessage;
    @FXML
    private Button btnConnection;
    @FXML
    private Button btnEnter;



    @FXML
    public void connection(ActionEvent event) {
        if (!validateFields()) {
            notify("Error: Todos los campos son obligatorios.", "lblError");
            return;
        }
        
        try {
            String ip = tfIp.getText();
            int port = Integer.parseInt(tfPort.getText());
       
            Utils utils = new Utils(ip,port);
            if (Utils.clientF.getSocket().isConnected()) {
                btnEnter.setDisable(false);
                btnConnection.setDisable(true);
                notify("Conexion exitosa", "lblSucess");
            } else {
                notify("Error en la conexion", "lblError");
            }
        } catch (NumberFormatException e) {
            notify("Error: El puerto debe ser un número válido.", "lblError");
        }
    }

    private boolean validateFields() {
        return !tfIp.getText().isEmpty() && !tfPort.getText().isEmpty();
    }

    @FXML
    public void enter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUILogin.fxml"));
            Parent root = loader.load();
            GUILoginController controller = loader.getController();
            
  
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.show();
            
            stage.setOnCloseRequest(e -> controller.closeWindows());
            
            Stage temp = (Stage) this.btnConnection.getScene().getWindow();
            temp.close();
        } catch (IOException e) {
            System.out.println("Error al ir a la vista");
        }
    }

    @FXML
    private void initialize() {
        btnEnter.setDisable(true);
    }

    private void notify(String message, String colorLabel) {
        lblsetMessage.getStyleClass().clear();
        lblsetMessage.setText(message);
        lblsetMessage.getStyleClass().add(colorLabel);
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> lblsetMessage.setText("")));
        timeline.setCycleCount(1);
        timeline.play();
    }
}
