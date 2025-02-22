package business;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import domain.Bill;
import domain.Product;
import domain.Utils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableView;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUICartController {
	@FXML
	private Pane panInfo;
	@FXML
	private Label lblExit;
	@FXML
	private Pane panCenter;
	@FXML
	private TableView<Product> tvProducts;
	@FXML
	private Pane panRight;
	@FXML
	private TextField tfName;
	@FXML
	private TextField tfPrice;
	@FXML
	private TextField tfAmount;
	@FXML
	private Button btnChangeAmount;
	@FXML
	private TextField tfTotal;
	@FXML
	private TextField tfSubTotal;
	
	@FXML
	private Button btnPay;
	@FXML
	private Button btnAddDesc;
	
	@FXML
	private TableColumn<Product, String> tcName;

	@FXML
	private TableColumn<Product, Double> tcPrice;

	@FXML
	private TableColumn<Product, Integer> tcAmount;

	@FXML
	private Label lblMessage;

	@FXML
	private Label lblName;

	Product productEdit;
	
	private double desc = 0.0;
	private double total= 0.0;
	private final double TAX = 0.13;

	// Event Listener on Label[#lblExit].onMouseClicked
	@FXML
	public void exit(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIMainWindow.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.show();

			GUIMainWindowController controller = loader.getController();

			Stage temp = (Stage) this.btnChangeAmount.getScene().getWindow();
			temp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initTableView() {

		tcName = new TableColumn<Product, String>("Nombre");
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcName.setReorderable(false);
		tcName.setResizable(false);

		tcPrice = new TableColumn<Product, Double>("Precio");
		tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		tcPrice.setReorderable(false);
		tcPrice.setResizable(false);

		tcAmount = new TableColumn<Product, Integer>("Cantidad");
		tcAmount.setCellValueFactory(new PropertyValueFactory<>("stock"));
		tcAmount.setReorderable(false);
		tcAmount.setResizable(false);

		tvProducts.getColumns().addAll(tcName, tcPrice, tcAmount);
	}

	@FXML
	private void initialize() {
		initTableView();
		tvProducts.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

		// Ajustar tamaño de columnas cuando la tabla cambia de tamaño
		tvProducts.widthProperty().addListener((obs, oldWidth, newWidth) -> adjustColumnWidths());

		tvProducts.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // Asegurar que
																										// carga el CSS
		loadData();
		tfSubTotal.clear();
		tfTotal.clear();

	}

// Método para ajustar columnas dinámicamente
	private void adjustColumnWidths() {
		double totalWidth = tvProducts.getWidth();
		double columnWidth = totalWidth / tvProducts.getColumns().size();

		for (TableColumn<Product, ?> column : tvProducts.getColumns()) {
			column.setPrefWidth(columnWidth);
		}
	}

	public void loadData() {

		if (Utils.clientF.getUser().getCart() != null) {
			refresh();
		}
		updateAndDeleteCart();
		String name = getName();
		lblName.setText(name);

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

			Stage temp = (Stage) this.btnChangeAmount.getScene().getWindow();
			temp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void updateAndDeleteCart() {
		tvProducts.setOnMouseClicked(e -> {
			Product temp = tvProducts.getSelectionModel().getSelectedItem();
			if (temp != null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmacion");
				alert.setHeaderText("Desea eliminar o seleccionar un producto?");
				alert.setContentText("");

				ButtonType btnEdit = new ButtonType("Editar");
				ButtonType btnDelete = new ButtonType("Eliminar");
				ButtonType btnCancel = new ButtonType("Cancelar");
				alert.getButtonTypes().setAll(btnEdit, btnDelete, btnCancel);
				ButtonType result = alert.showAndWait().orElse(btnCancel);

				if (result == btnEdit) {
					tfName.setText(temp.getName());
					tfPrice.setText("" + temp.getPrice());
					tfAmount.setText("" + temp.getStock());
					productEdit = temp;

				} else if (result == btnDelete) {
					Utils.clientF.removeProductFromCart(Utils.clientF.getUser().getId(), temp.getIdProduct(),
							lblMessage);
					Utils.clientF.openCart();
					tvProducts.getItems().remove(temp);
					tfTotal.setText("" + LogicCart.price(Utils.clientF.getUser().getCart()));

				} else {
					alert.close();
				}
			}

		});

	}

	@FXML
	public void changeProduct(ActionEvent event) {
		int newAmount = Integer.parseInt(tfAmount.getText());

		// Obtener el stock disponible desde la lista de productos en ClientFunction
		Product mainProduct = Utils.clientF.findProductById(productEdit.getIdProduct());

		if (mainProduct == null) {
			notify("Error: Producto no encontrado en la lista", lblMessage);
			return;
		}

		if (newAmount > mainProduct.getStock()) {
			notify("Stock insuficiente. Máximo disponible: " + mainProduct.getStock(), lblMessage);
			return;
		}

		// Actualizar cantidad en el carrito
		productEdit.setStock(newAmount);
		Utils.clientF.editProductInCart(Utils.clientF.getUser().getId(), productEdit, lblMessage);

		// Restar la cantidad del stock principal
		int newStock = mainProduct.getStock() - newAmount;
		mainProduct.setStock(newStock);

		// Actualizar el producto en el servidor
		Utils.clientF.updateProductInServer(mainProduct);

		Utils.clientF.openCart();
		refresh();
	}

	private void refresh() {
		ObservableList<Product> data = FXCollections.observableArrayList(Utils.clientF.getUser().getCart().getList());
		tvProducts.setItems(data);
		tvProducts.refresh();
		tfSubTotal.setText("" + LogicCart.price(Utils.clientF.getUser().getCart()));
	}
	

@FXML
public void addDesc(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Descuento");
    alert.setHeaderText("¿Desea aplicar un descuento?");
    alert.setContentText("Ingrese el porcentaje de descuento (0-100):");

    // Botones Sí y No
    ButtonType btnYes = new ButtonType("Sí");
    ButtonType btnNo = new ButtonType("No");
    alert.getButtonTypes().setAll(btnYes, btnNo);

    ButtonType result = alert.showAndWait().orElse(btnNo);

    if (result == btnYes) {
        String input = JOptionPane.showInputDialog(null, "Ingrese el porcentaje de descuento (0-100):",
                "Descuento", JOptionPane.QUESTION_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            try {
                desc = Double.parseDouble(input.trim());
                if (desc < 0 || desc > 100) {
                    throw new NumberFormatException("Porcentaje fuera de rango");
                }
                 total = LogicCart.calculateDiscount(LogicCart.price(Utils.clientF.getUser().getCart()), desc);
                tfTotal.setText("" + total * TAX);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. No se aplicará ningún descuento.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
	} else {
		total = LogicCart.calculateDiscount(LogicCart.price(Utils.clientF.getUser().getCart()), 0.0);
		tfTotal.setText("" + LogicCart.price(Utils.clientF.getUser().getCart()) * TAX);
		alert.close();
	}
}


	@FXML
	public void pay(ActionEvent event) {

		if (Utils.clientF.getUser().getCart().getList().size() == 0) {
			notify("No hay productos en el carrito", lblMessage);
			return;
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmacion");
		alert.setHeaderText("Desea realizar el pago?");
		alert.setContentText("");

		ButtonType btnPay = new ButtonType("Pagar");
		ButtonType btnCancel = new ButtonType("Cancelar");
		alert.getButtonTypes().setAll(btnPay, btnCancel);
		ButtonType result = alert.showAndWait().orElse(btnCancel);

		if (result == btnPay) {
			Random random = new Random();
			int idBill = random.nextInt(1000) + 1;
			String nameBuyer = Utils.clientF.getUser().getFullName();
			String addressBuyer = Utils.clientF.getUser().getAddress();
			int phoneBuyer = Utils.clientF.getUser().getPhone();
			List<Product> list = Utils.clientF.getUser().getCart().getList();
			
			total = LogicCart.calculateDiscount(LogicCart.price(Utils.clientF.getUser().getCart())* TAX, desc);

			
			Date dateTransaction = new Date();

			Bill bill = new Bill(idBill, nameBuyer, addressBuyer, phoneBuyer, list, desc, TAX , total,
					dateTransaction);
			
			tvProducts.refresh();

			System.out.println(bill);

			 Utils.clientF.sendBill(bill);

		} else {
			alert.close();
		}

	}

	private String getName() {
		String parts[] = Utils.clientF.getUser().getFullName().split(" ");
		String name = parts[0];
		return name;

	}

	private void notify(String message, Label lblMessage) {
		Platform.runLater(() -> {
			lblMessage.getStyleClass().clear();
			lblMessage.setText(message);

			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> lblMessage.setText("")));
			timeline.setCycleCount(1);
			timeline.play();
		});
	}
}
