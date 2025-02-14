package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Product;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class GUIMarketController {
	@FXML
	private TextField tfSearch;
	@FXML
	private Button btnSearch;
	@FXML
	private VBox chosenProductCard;
	@FXML
	private Label lblNameProduct;
	@FXML
	private Label lblPriceProduct;
	@FXML
	private ImageView ivProduct;
	@FXML
	private Label lblIDproduct;
	@FXML
	private Label lblDescription;
	@FXML
	private Label lblStock;
	@FXML
	private Label lblCategory;
	@FXML
	private Label lblValue;
	@FXML
	private TextArea taComments;
	@FXML
	private Button btnAddCart;
	@FXML
	private Button btnMyAccount;
	@FXML
	private GridPane gridPane;
	@FXML
	private Button btnAddFav;
	
	
	
	@FXML
	private ScrollPane scrollPane;
	private Image image;
	
	private MyListener myListener;

	private List<Product> products = new ArrayList<>();

	public void initialize() {
	    ClientFunction clientFunction = new ClientFunction("localhost", 5000); // Ajusta la IP y puerto - aqui debe de configurarlo a como usted lo esta manejando
	    products = clientFunction.getProducts(); // Obtiene la lista desde el servidor

	    if (products.size() > 0) {//Si hay productos en la lista entonces se le asigna el primer producto a la tarjeta de producto seleccionada
	        setChosenProduct(products.get(0));
	        myListener = new MyListener() {//Se crea un objeto de la clase MyListener para poder asignarle un producto a la tarjeta de producto seleccionada
	            @Override
	            public void onClickListener(Product product) {
	                setChosenProduct(product);
	            }
	        };
	    }

	    int column = 0;//
	    int row = 1;

	    try {
	        for (Product product : products) {//Recorre la lista de productos y por cada producto crea una tarjeta de producto
	            FXMLLoader fxmlLoader = new FXMLLoader();//Se crea un objeto de la clase FXMLLoader para cargar la interfaz de usuario
	            fxmlLoader.setLocation(getClass().getResource("/presentation/GUIItem.fxml"));
	            AnchorPane anchorPane = fxmlLoader.load();//Se carga la interfaz de usuario

	            GUIItemController itemController = fxmlLoader.getController();//Se crea un objeto de la clase GUIItemController para poder asignarle los datos del producto
	            itemController.setData(product, myListener);//Se le asigna el producto y el listener

	            gridPane.add(anchorPane, column++, row);//Se agrega la tarjeta de producto al gridPane en la columna y fila correspondiente
	            if (column == 3) { // Máximo 3 por fila
	                column = 0;
	                row++;
	            }

	            GridPane.setMargin(anchorPane, new Insets(10));//Se le asigna un margen a la tarjeta de producto
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	// este metodo es para poder agregar un producto al catalogo en tiempo real pero no esta implementado (no pude)
//	public void addNewProductToCatalog(Product product) {
//		 int column = 0;
//		    int row = 1;
//	    try {
//	        FXMLLoader fxmlLoader = new FXMLLoader();
//	        fxmlLoader.setLocation(getClass().getResource("/presentation/GUIItem.fxml"));
//	        AnchorPane anchorPane = fxmlLoader.load();
//
//	        GUIItemController itemController = fxmlLoader.getController();
//	        itemController.setData(product, myListener);
//
//	        gridPane.add(anchorPane, column++, row);
//	        if (column == 3) { // Máximo 3 por fila
//	            column = 0;
//	            row++;
//	        }
//
//	        GridPane.setMargin(anchorPane, new Insets(10));
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}


	
	private void setChosenProduct(Product product) {// metodo para asignar un producto a la tarjeta de producto seleccionada
		lblNameProduct.setText(product.getName());
		lblPriceProduct.setText("₡" + product.getPrice());
		String imagePath = product.getUrlImage();
		if (!imagePath.startsWith("file:")) {//Si no es una ruta de archivo local entonces se le agrega el "file:" para que la imagen se pueda cargar 
		    imagePath = "file:" + imagePath;//Se le agrega el "file:" a la ruta de la imagen
		}
		Image image = new Image(imagePath);
		ivProduct.setImage(image);
		lblIDproduct.setText(product.getIdProduct());
		lblDescription.setText(product.getDescription());
		lblStock.setText(""+product.getStock());
		lblCategory.setText(product.getCategory());
		lblValue.setText(""+product.getValue());
		
	}
	

	// Event Listener on Button[#btnSearch].onAction
	@FXML
	public void searchProduct(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on Button[#btnAddCart].onAction
	@FXML
	public void addCart(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on Button[#btnMyAccount].onAction
	@FXML
	public void windowMyAccount(ActionEvent event) {
		// TODO Autogenerated
	}
	

    @FXML
    public void addFav(ActionEvent event) {

    }
}
