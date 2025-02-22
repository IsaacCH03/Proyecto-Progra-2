package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Date;

import data.DataBill;
import domain.Bill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TableView;

public class GUIBillingController {
	@FXML
	private Label lblBilling;
	@FXML
	private TableView<Bill> tvShowBill;
	@FXML
	private TableColumn<Bill, Integer> tcIdBill;
	@FXML
	private TableColumn<Bill, String> tcNameBuyer;
	@FXML
	private TableColumn<Bill, String> tcAddressBuyer;
	@FXML
	private TableColumn<Bill, Integer> tcPhoneBuyer;
	@FXML
	private TableColumn<Bill, String> tcProducts;
	@FXML
	private TableColumn<Bill, Double> tcDesc;
	@FXML
	private TableColumn<Bill, Double> tcTAX;
	
	@FXML
	private TableColumn<Bill, Double> tcTotal;
	@FXML
	private TableColumn<Bill, Date> tcDateTransaction;
	
	
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
	
	public void initialize() {
		initTable();
		removeBill();
	}
	
	
	private void initTable() {
		tcIdBill = new TableColumn<>("Id Factura");
		tcIdBill.setCellValueFactory(new PropertyValueFactory<>("idBill"));
		
		tcNameBuyer = new TableColumn<>("Nombre Comprador");
		tcNameBuyer.setCellValueFactory(new PropertyValueFactory<>("nameBuyer"));
		
		tcAddressBuyer = new TableColumn<>("Direccion Comprador");
		tcAddressBuyer.setCellValueFactory(new PropertyValueFactory<>("addressBuyer"));
		
		tcPhoneBuyer = new TableColumn<>("Telefono Comprador");
		tcPhoneBuyer.setCellValueFactory(new PropertyValueFactory<>("phoneBuyer"));
		
		tcProducts = new TableColumn<>("Productos");
		tcProducts.setCellValueFactory(new PropertyValueFactory<>("products"));
		
		tcDesc = new TableColumn<>("Descuento");
		tcDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
		
		tcTAX = new TableColumn<>("Impuesto");
		tcTAX.setCellValueFactory(new PropertyValueFactory<>("TAX"));
		
		tcTotal = new TableColumn<>("Total");
		tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		tcDateTransaction = new TableColumn<>("Fecha Transaccion");
		tcDateTransaction.setCellValueFactory(new PropertyValueFactory<>("dateTransaction"));
		
	
		
		tvShowBill.getColumns().addAll(tcIdBill, tcNameBuyer, tcAddressBuyer, tcPhoneBuyer, tcProducts, tcDesc,tcTAX, tcTotal, tcDateTransaction);
		
		DataBill dataBill = new DataBill();
		
		if(dataBill.getList() == null) {
			System.out.println("No hay facturas");
			tvShowBill.setItems(null);
            }
		else {
            System.out.println("Hay facturas");
		ObservableList<Bill> bills = FXCollections.observableArrayList(dataBill.getList());
		tvShowBill.setItems(bills);
		
		}

	}
	
	@FXML
	public void removeBill() {
		tvShowBill.setOnMouseClicked(e -> {
			Bill temp = tvShowBill.getSelectionModel().getSelectedItem();
			if(temp !=null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Eliminar Factura");	
				alert.setHeaderText("Desea eliminar la factura?");
				alert.setContentText("");
				
				ButtonType buttonTypeYes = new ButtonType("Si");
				ButtonType buttonTypeNo = new ButtonType("No");
				alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
				ButtonType result = alert.showAndWait().get();
				
				if(result == buttonTypeYes) {
					DataBill dataBill = new DataBill();
					dataBill.deleteBill(temp.getIdBill());
					tvShowBill.getItems().remove(temp);
				} else {
					alert.close();
				}
			}
		});
	}
	
	
	
	

	@FXML
	public void backWindow(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIStartServer.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
//		stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("Img/icono.png")));
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
