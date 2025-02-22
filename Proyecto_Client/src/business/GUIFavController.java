package business;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import domain.FavProduct;
import domain.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.util.List;

public class GUIFavController {

    @FXML
    private TableView<FavProduct> tvShowFav;
    @FXML
    private TableColumn<FavProduct, String> tcProductId;
    @FXML
    private TableColumn<FavProduct, String> tcProductName;
    @FXML
    private TableColumn<FavProduct, String> tcDateAdded;
    @FXML
    private Button btnBack;

    private ObservableList<FavProduct> favProductList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        initTable();

        if (Utils.clientF.getUser().getFavProducts() == null ||Utils.clientF.getUser().getFavProducts().isEmpty()) {
            System.out.println("No hay productos favoritos.");
            return;
        }


      
            favProductList.clear();
            favProductList.addAll(Utils.clientF.getUser().getFavProducts());
            tvShowFav.setItems(favProductList);
            tvShowFav.refresh();
   
            setupRemoveEvent();
        
        
    }

    public void loadFavorites() {
        List<FavProduct> favorites = Utils.clientF.getFavProducts(Utils.clientF.getUser().getId());

        if (favorites == null || favorites.isEmpty()) {
            System.out.println("No hay productos favoritos.");
            return;
        }
        Platform.runLater(() -> {
            favProductList.clear();
            favProductList.addAll(favorites);
            tvShowFav.setItems(favProductList);
            tvShowFav.refresh();
        });
    }

    private void initTable() {
        if (tcProductId == null || tcProductName == null || tcDateAdded == null) {
            System.out.println("Error: Las columnas no están inicializadas. Revisa el FXML.");
            return;
        }

        tcProductId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getIdProduct()));
        tcProductName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        tcDateAdded.setCellValueFactory(new PropertyValueFactory<>("date"));

        tvShowFav.setItems(favProductList);
    }
    
    
    public void removeFav(FavProduct product) {
        if (product != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Estás seguro de que quieres eliminar el producto de tus favoritos?");
            alert.setContentText("");

            ButtonType btnYes = new ButtonType("Sí");
            ButtonType btnNo = new ButtonType("No");
            alert.getButtonTypes().setAll(btnYes, btnNo);
            ButtonType result = alert.showAndWait().orElse(btnNo);

            if (result == btnYes) {
                // Eliminar del json
                Utils.clientF.deleteFavProduct(Utils.clientF.getUser().getId(), product.getProduct().getIdProduct());

                // Actualizar la lista de favoritos del usuario localmente
                Utils.clientF.getUser().getFavProducts().remove(product);

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Información");
                alert2.setHeaderText("Producto eliminado de favoritos");
                alert2.setContentText("");
                alert2.showAndWait();

                // Refrescar la tabla
                favProductList.remove(product);
                tvShowFav.refresh();
            }
        }
    }

    @FXML
    public void setupRemoveEvent() {
        tvShowFav.setOnMouseClicked(e -> {
            FavProduct selectedProduct = tvShowFav.getSelectionModel().getSelectedItem();
            removeFav(selectedProduct);
        });
    }


    @FXML
    public void windowBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIMainWindow.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.show();

            GUIMainWindowController controller = loader.getController();
            

            Stage temp = (Stage) this.btnBack.getScene().getWindow();
            temp.close();
        } catch (Exception e) {
            e.printStackTrace();
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
            

            Stage temp = (Stage) this.btnBack.getScene().getWindow();
            temp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
