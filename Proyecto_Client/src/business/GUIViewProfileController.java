package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import domain.Utils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUIViewProfileController {
	@FXML
	private Pane pM;
	@FXML
	private TextField tfFullName;
	@FXML
	private TextField tfId;
	@FXML
	private TextField tfMail;
	@FXML
	private TextField tfPhone;
	@FXML
	private TextField tfAddress;
	@FXML
	private PasswordField pfPassword;
	@FXML
	private TextField tfVisiblePassword;
	@FXML
	private ImageView imgPassword;
	@FXML
	private Button btnSave;
	@FXML
	private Label lblMessage;

	

	@FXML
	public void initialize() {
		loadData();
	}

	@FXML
	public void showPassword(MouseEvent event) {
		if (tfVisiblePassword.isVisible()) {
			tfVisiblePassword.setVisible(false);
			pfPassword.setText(tfVisiblePassword.getText());
			pfPassword.setVisible(true);
		} else {
			tfVisiblePassword.setText(pfPassword.getText());
			tfVisiblePassword.setVisible(true);
			pfPassword.setVisible(false);
		}
	}

	// Event Listener on Button[#btnSave].onAction
	@FXML
	public void saveChanges(ActionEvent event) {
	    if (!validateFields()) {
	        notify("Error: Todos los campos son obligatorios.", "lblError");
	        return;
	    }

	    String fullName = tfFullName.getText();
	    String address = tfAddress.getText();
	    String phone = tfPhone.getText();
	    String password = pfPassword.isVisible() ? pfPassword.getText() : tfVisiblePassword.getText();

	    String message = Utils.clientF.updateProfile(fullName, address, phone, password);
	    notify(message, "lblNotification");

	    updateUser();
	}

	public void loadData() {
		

		// Asegurarse de que el usuario no sea null antes de asignar datos
		if (Utils.clientF != null && Utils.clientF.getUser() != null) {
			tfFullName.setText(Utils.clientF.getUser().getFullName());
			System.out.println(Utils.clientF.getUser().getFullName());
			tfId.setText(Utils.clientF.getUser().getId() + "");
			tfMail.setText(Utils.clientF.getUser().getMail());
			tfAddress.setText(Utils.clientF.getUser().getAddress());
			tfPhone.setText(Utils.clientF.getUser().getPhone() + "");
			pfPassword.setText(Utils.clientF.getUser().getPassword());
		} else {
			System.out.println("Advertencia: clientF o clientF.getUser() es null.");
		}
	}

	public void closeWindows() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIMainWindow.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.show();

			GUIMainWindowController controller = loader.getController();
		

			Stage temp = (Stage) this.btnSave.getScene().getWindow();
			temp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void notify(String message, String colorLabel) {
		lblMessage.getStyleClass().clear();
		lblMessage.setText(message);
		lblMessage.getStyleClass().add(colorLabel);

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> lblMessage.setText("")));
		timeline.setCycleCount(1);
		timeline.play();
	}

	private void updateUser() {
		Utils.clientF.getUser().setFullName(tfFullName.getText());
		Utils.clientF.getUser().setAddress(tfAddress.getText());
		Utils.clientF.getUser().setPassword(tfVisiblePassword.getText());
		
		Utils.clientF.getUser().setPhone(Integer.parseInt(tfPhone.getText()));

		tfFullName.setText(Utils.clientF.getUser().getFullName());
		tfId.setText(Utils.clientF.getUser().getId() + "");
		tfMail.setText(Utils.clientF.getUser().getMail());
		tfAddress.setText(Utils.clientF.getUser().getAddress());
		tfPhone.setText(Utils.clientF.getUser().getPhone() + "");
		pfPassword.setText(Utils.clientF.getUser().getPassword());
	}
	private boolean validateFields() {
	    if (tfFullName.getText().trim().isEmpty() ||
	        tfAddress.getText().trim().isEmpty() ||
	        tfPhone.getText().trim().isEmpty() ||
	        (pfPassword.isVisible() && pfPassword.getText().trim().isEmpty()) ||
	        (tfVisiblePassword.isVisible() && tfVisiblePassword.getText().trim().isEmpty())) {

	        return false;
	    }
	    return true;
	}


}
