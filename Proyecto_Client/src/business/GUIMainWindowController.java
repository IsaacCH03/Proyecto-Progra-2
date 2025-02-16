package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class GUIMainWindowController {
	@FXML
	private AnchorPane apMain;
	@FXML
	private Label lblTicoTech;
	@FXML
	private Label lblView;
	@FXML
	private Label lblFavorite;
	@FXML
	private Label lblCart;
	@FXML
	private Label lblcatalog;
	
	ClientFunction clientF;

	@FXML
	public void openCatalog(MouseEvent event) {
	    System.out.println("Abriendo cat�logo...");

	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIMarket.fxml"));
	        Parent root = loader.load();

	        // Obtiene el controlador despu�s de cargar la vista
	        GUIMarketController controller = loader.getController();
	        
	        // PASA el clientF ANTES de mostrar la ventana
	        controller.loadData(clientF);

	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        stage.show();

	        // Evento para cerrar correctamente la ventana
	        stage.setOnCloseRequest(e -> controller.closeWindows());

	        // Cierra la ventana actual
	        Stage temp = (Stage) this.lblcatalog.getScene().getWindow();
	        temp.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void openCart(MouseEvent event) {
		// TODO Autogenerated
		System.out.println("Carro");
	}
	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void openFavorites(MouseEvent event) {
		// TODO Autogenerated
		System.out.println("Favorito");
	}
	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void viewProfile(MouseEvent event) {
		System.out.println("Perfil");
		 try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIViewProfile.fxml"));
	            Parent root = loader.load();

	            Scene scene = new Scene(root);
	            Stage stage = new Stage();
	            stage.setScene(scene);
	            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	            stage.show();

	            GUIViewProfileController controller = loader.getController();
	            controller.loadData(clientF);
	            
	            stage.setOnCloseRequest(e -> controller.closeWindows());

	            Stage temp = (Stage) this.lblcatalog.getScene().getWindow();
	            temp.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		
	}
	public void loadData(ClientFunction clientF) {
		// TODO Auto-generated method stub
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

	            Stage temp = (Stage) this.lblcatalog.getScene().getWindow();
	            temp.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
	}
}
