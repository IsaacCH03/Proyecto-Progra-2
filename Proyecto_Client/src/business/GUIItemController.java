package business;

import domain.Product;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GUIItemController {
	@FXML
	private Label lblNameProduct;
	@FXML
	private Label lblPrice;
	@FXML
	private ImageView ivProduct;
	
	
	
	
	private Product product;
	private MyListener myListener;
	@FXML
	public void onClick(MouseEvent event) {
        myListener.onClickListener(product);
        }
	
	
	//metodo que recibe un producto y un listener para poder mostrar los datos del producto en la GUI, serian los cuadros de productos
	public void setData(Product product, MyListener myListener) {
		this.product = product;
		this.myListener = myListener;
		lblNameProduct.setText(product.getName());
		lblPrice.setText("₡"+ product.getPrice());
		String imagePath = product.getUrlImage();
		if (!imagePath.startsWith("file:")) {//si la imagen no esta en el archivo
		    imagePath = "file:" + imagePath;//se le agrega el file: para que se pueda leer
		}
		Image image = new Image(imagePath);
		ivProduct.setImage(image);

		
	}

}
