package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import domain.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUIRegisterController {
    @FXML
    private AnchorPane myAnchor;
    @FXML
    private Pane pM;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfMail;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhone;
    @FXML
    private Button btnRegister;
    @FXML
    private Label setMessage;

    private ClientFunction clientF;

    @FXML
    public void register(ActionEvent event) {
        if (!validateFields()) {
            notify("Error: Todos los campos son obligatorios.", "errorLabel");
            return;
        }
        
        try {
            String fullName = tfFullName.getText();
            int id = Integer.parseInt(tfId.getText());
            String mail = tfMail.getText();
            String password = tfPassword.getText();
            String address = tfAddress.getText();
            int phone = Integer.parseInt(tfPhone.getText());

            User user = new User(fullName, id, mail, password, address, phone);
            clientF.sendRecord(user, setMessage);
        } catch (NumberFormatException e) {
            notify("Error: ID y Tel�fono deben ser n�meros v�lidos.", "lblError");
        }
    }

    private boolean validateFields() {
        return !tfFullName.getText().isEmpty() &&
               !tfId.getText().isEmpty() &&
               !tfMail.getText().isEmpty() &&
               !tfPassword.getText().isEmpty() &&
               !tfAddress.getText().isEmpty() &&
               !tfPhone.getText().isEmpty();
    }

    public void loadData(ClientFunction clientF) {
        this.clientF = clientF;
    }

    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUILogin.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.show();

            GUILoginController controller = loader.getController();
            controller.loadData(clientF);

            Stage temp = (Stage) this.btnRegister.getScene().getWindow();
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

    public void clearFields() {
        tfFullName.clear();
        tfId.clear();
        tfMail.clear();
        tfPassword.clear();
        tfAddress.clear();
        tfPhone.clear();
    }
}
