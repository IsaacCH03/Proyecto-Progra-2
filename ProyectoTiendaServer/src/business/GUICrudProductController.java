package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import data.DataProduct;
import domain.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUICrudProductController {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Pane paneData;
	@FXML
	private Label lblTittle;
	@FXML
	private TextField tfNameProduct;
	@FXML
	private TextArea taDescriptionProduct;
	@FXML
	private TextField tfPrice;
	@FXML
	private TextField tfStock;
	@FXML
	private TextField tfCategory;
	@FXML
	private Button btnSelectImage;
	@FXML
	private Button btnMinimize;

	@FXML
	private Button btnClose;

	@FXML
	private ImageView ivImageProduct;
	@FXML
	private Button btnSaveProduct;
	@FXML
	private Pane paneShow;
	@FXML
	private TextField tfSearchProduct;
	@FXML
	private TableView<Product> tvShowProduct;
	@FXML
	private TableColumn<Product, String> tcIdProduct;

	@FXML
	private TableColumn<Product, String> tcNameProduct;

	@FXML
	private TableColumn<Product, String> tcDescriptionProduct;
	@FXML
	private TableColumn<Product, Double> tcPrice;
	@FXML
	private TableColumn<Product, Integer> tcStock;
	@FXML
	private TableColumn<Product, String> tcCategory;
	
	 @FXML
	 private Label lblMessage;

	private Product editProduct;

	@FXML
	private ToolBar toolBAR;
	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	private Button btnBack;

	public void initialize() {
		loadProductsTable();
		initTable();
		updateDeleteProduct();
	}

	private void initTable() {
		tcIdProduct = new TableColumn<Product, String>("ID del producto");
		tcIdProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("idProduct"));

		tcNameProduct = new TableColumn<Product, String>("Nombre del producto");
		tcNameProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));

		tcDescriptionProduct = new TableColumn<Product, String>("Descripción del producto");
		tcDescriptionProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));

		tcPrice = new TableColumn<Product, Double>("Precio");
		tcPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

		tcStock = new TableColumn<Product, Integer>("Stock");
		tcStock.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));

		tcCategory = new TableColumn<Product, String>("Categoría");
		tcCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));

		tvShowProduct.getColumns().addAll(tcIdProduct, tcNameProduct, tcDescriptionProduct, tcPrice, tcStock,
				tcCategory);

	}

	// Event Listener on Button[#btnSelectImage].onAction
	@FXML
	public void selectImage(ActionEvent event) {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Seleccionar Imagen");

	    fileChooser.getExtensionFilters()
	            .addAll(new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg"));

	    File selectedFile = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());

	    if (selectedFile != null) {
	        // Obtener la ruta absoluta del archivo
	        String fullPath = selectedFile.getAbsolutePath(); 

	        // Buscar la carpeta "Img/" dentro de la ruta
	        int index = fullPath.lastIndexOf("Img" + File.separator);
	        String relativePath;// Ruta relativa de la imagen
	        if (index != -1) {// Si encuentra la carpeta "Img/" en la ruta absoluta del archivo
	            relativePath = fullPath.substring(index); // Extrae solo desde "Img/"
	        } else {
	            relativePath = "Img/" + selectedFile.getName(); // Si no encuentra la carpeta, usa el nombre del archivo
	        }

	        // Configura la imagen seleccionada en el ImageView con la ruta RELATIVA
	        ivImageProduct.setImage(new Image("file:" + relativePath));

	        // Guardar solo la ruta relativa
	        System.out.println("Imagen seleccionada: " + relativePath);
	    }
	}



	// Event Listener on Button[#btnSaveProduct].onAction
	@FXML
	public void saveProduct(ActionEvent event) {
		String name = tfNameProduct.getText();
		String description = taDescriptionProduct.getText();
		double price = Double.parseDouble(tfPrice.getText());
		int stock = Integer.parseInt(tfStock.getText());
		String category = tfCategory.getText();
		String imagePath = ivImageProduct.getImage() != null ? ivImageProduct.getImage().getUrl() : "";

		Product product = new Product(name, description, price, stock, category, imagePath);
		DataProduct dataProduct = new DataProduct();
		dataProduct.save(product, editProduct);
		
		tvShowProduct.getItems().add(product);

		clearFields();

	}
	
	
	@FXML
	public void searchProduct( KeyEvent event) {
		String searchText = tfSearchProduct.getText().toLowerCase();
		DataProduct dataProduct = new DataProduct();
		List<Product> products = dataProduct.getList();
		
		List<Product> filterProduct = new ArrayList<>();
		for (Product product : products) {
			if (product.getName().toLowerCase().contains(searchText)) {
				filterProduct.add(product);
			}
		}
		
		ObservableList<Product> observableListProduct = FXCollections.observableArrayList(filterProduct);
		tvShowProduct.setItems(observableListProduct);
	
		
		
	}
	
	
	

	@FXML
	public void updateDeleteProduct() {
		tvShowProduct.setOnMouseClicked(e -> {
			Product temp = tvShowProduct.getSelectionModel().getSelectedItem();
			if (temp != null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmacion");
				alert.setHeaderText("Desea eliminar o actualizar el producto?");
				alert.setContentText("");

				ButtonType btnEdit = new ButtonType("Editar");
				ButtonType btnDelete = new ButtonType("Eliminar");
				ButtonType btnCancel = new ButtonType("Cancelar");
				alert.getButtonTypes().setAll(btnEdit, btnDelete, btnCancel);
				ButtonType result = alert.showAndWait().orElse(btnCancel);

				if (result == btnEdit) {
					editProduct = temp;
					tfNameProduct.setText(temp.getName());
					taDescriptionProduct.setText(temp.getDescription());
					tfPrice.setText(String.valueOf(temp.getPrice()));
					tfStock.setText(String.valueOf(temp.getStock()));
					tfCategory.setText(temp.getCategory());
					ivImageProduct.setImage(new Image(temp.getUrlImage()));
					
					tvShowProduct.getItems().remove(temp);
					tvShowProduct.refresh();

				} else if (result == btnDelete) {
					DataProduct dataProduct = new DataProduct();
					dataProduct.remove(temp.toString());
					tvShowProduct.getItems().remove(temp);
				} else {
					alert.close();
				}
			}
		});
	}

	private void clearFields() {
		tfNameProduct.clear();
		taDescriptionProduct.clear();
		tfPrice.clear();
		tfStock.clear();
		tfCategory.clear();
		ivImageProduct.setImage(null);
		editProduct = null;
	}

	private void loadProductsTable() {
		DataProduct dataProduct = new DataProduct();
		List<Product> products = dataProduct.getList();
		
		ObservableList<Product> observableListProduct = FXCollections.observableArrayList(products);
		tvShowProduct.setItems(observableListProduct);
	}
	

	@FXML
	public void backWindow(ActionEvent event)throws Exception {
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
	
	private boolean validForm() {
		
		if (tfNameProduct.getText().isEmpty() || taDescriptionProduct.getText().isEmpty() || tfPrice.getText().isEmpty()
				|| tfStock.getText().isEmpty() || tfCategory.getText().isEmpty()) {
			lblMessage.setText("Faltan campos por llenar");
			
	
			return false;
		}
		return true;
		
		
		
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
