package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUILoginController {
    @FXML
    private Pane pMajor;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private Pane pTfs;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfMail;
    @FXML
    private Label setMessage;

    private ClientFunction clientF;

    @FXML
    public void login(ActionEvent event) {
        if (!validateFields()) {
            notify("Error: Todos los campos son obligatorios.", "lblError");
            return;
        }

        String mail = tfMail.getText();
        String pass = pfPassword.getText();
        btnLogin.setDisable(true);
        clientF.setUser(null);
        clientF.login(mail, pass, setMessage, btnLogin);
        
      
        while(btnLogin.isDisable()) {
        	System.out.println("hola");
        }
        System.out.println("salio");
        
        if(clientF.getUser() != null) {
        	viewProfile();
        }
    }

    private boolean validateFields() {
        return !tfMail.getText().isEmpty() && !pfPassword.getText().isEmpty();
    }

    @FXML
    public void register(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIRegister.fxml"));
            Parent root = loader.load();
            GUIRegisterController controller = loader.getController();
            controller.loadData(clientF);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.show();
            
            stage.setOnCloseRequest(e -> controller.closeWindows());
            
            Stage temp = (Stage) this.btnLogin.getScene().getWindow();
            temp.close();
        } catch (IOException e) {
            System.out.println("Error al ir a la vista");
        }
    }

    public void loadData(ClientFunction clientF) {
        this.clientF = clientF;
    }

    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIConnection.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.show();
            
            GUIConnectionController controller = loader.getController();
            
            Stage temp = (Stage) this.btnLogin.getScene().getWindow();
            temp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notify(String message, String colorLabel) {
        setMessage.getStyleClass().clear();
        setMessage.setText(message);
        setMessage.getStyleClass().add(colorLabel);
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> setMessage.setText("")));
        timeline.setCycleCount(1);
        timeline.play();
    }
    private void viewProfile() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIViewProfile.fxml"));
            Parent root = loader.load();
            GUIViewProfileController controller = loader.getController();
            
            controller.loadData(clientF);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.show();
            
            stage.setOnCloseRequest(e -> controller.closeWindows());
            
            Stage temp = (Stage) this.btnLogin.getScene().getWindow();
            temp.close();
        } catch (IOException e) {
            System.out.println("Error al ir a la vista");
        }
    }
    public  void waitLogin() {
    	while (true) {
    		if(clientF != null) {
    			return;
    		}
    	}
    }
}