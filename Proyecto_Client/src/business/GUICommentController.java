package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.ArrayList;

import domain.Comment;
import domain.Product;
import domain.Utils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class GUICommentController {
    @FXML
    private ComboBox<Product> cbProducts;
    @FXML
    private TextArea taComment;
    @FXML
    private Button btnAddComment;
    @FXML
    private Slider sliderCalification;
    @FXML
    private Label lblStart1;
    @FXML
    private Label lblStart2;
    @FXML
    private Label lblStart3;
    @FXML
    private Label lblStart4;
    @FXML
    private Label lblStart5;
    @FXML
    private Button btnEditComment;
    @FXML
    private Button btnDeleteComent;
    @FXML
    private TextArea taFilterComment;
    @FXML
    private Slider sliderFilter;
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnConsult;
    
    @FXML
    private DatePicker dpFilter;
    
    @FXML
    private Label lblMessage;
    
    @FXML
    private Label lblNumFilter;

    @FXML
    public void initialize() {
    	lblNumFilter.getStyleClass().add("lblNotification");
        setupSliderListener();
        setupFilterSliderListener(); // 🔹 Agregar este método para actualizar lblNumFilter
        loadProducts();
    }


    private void setupSliderListener() {
        // Escuchar cambios en el Slider
        sliderCalification.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateStarVisibility(newValue.intValue());
        });
    }
    private void setupFilterSliderListener() {
        sliderFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblNumFilter.setText(String.valueOf(newValue.intValue())); // 🔹 Mostrar valor entero en el label
        });
    }


    private void updateStarVisibility(int value) {
        // Configurar la visibilidad de las estrellas basado en el valor del slider
        lblStart1.setVisible(value >= 1);
        lblStart2.setVisible(value >= 2);
        lblStart3.setVisible(value >= 3);
        lblStart4.setVisible(value >= 4);
        lblStart5.setVisible(value >= 5);
    }

    // Event Listener en Button[#btnAddComment]
    @FXML
    public void addComment() {
        String text = taComment.getText();
        int rating = (int) sliderCalification.getValue();
        Product productTemp = cbProducts.getValue();

        if (productTemp == null) {
            System.out.println("Error: No se ha seleccionado un producto.");
            return;
        }

        Comment comment = new Comment(Utils.clientF.getUser().getId(), LocalDate.now(), text, rating);
        if(!LogicComment.changeComment(productTemp, comment)) {
        	productTemp.getListComments().add(comment); // Agregar comentario al producto
        	System.out.println(productTemp.getListComments().toString());
            Utils.clientF.sendComment(productTemp); // Enviar producto con el nuevo comentario al servidor
            System.out.println("Comentario agregado y enviado al servidor: " + comment);
            notify("Comentario agregado excitosamente", lblMessage);
        }
        else {
        	System.out.println(productTemp.getListComments().toString());
        	Utils.clientF.sendComment(productTemp);
        	notify("Comentario editado excitosamente", lblMessage);
        }
        loadProducts();
        taComment.clear();
        taComment.setEditable(false);
    }


    @FXML
    public void editComment() {
    	taComment.setEditable(true);
    	
    }

    @FXML
    public void deleteComment() {
       Product productTemp = cbProducts.getValue();
       if(LogicComment.deleteComment(productTemp, Utils.clientF.getUser().getId())) {
    	   Utils.clientF.sendComment(productTemp); 
    	   System.out.println("Eliminado");
    	   notify("Comentario eliminado excitosamente", lblMessage);
    	   loadProducts();
    	   taComment.clear();
       }
       else {
    	   System.out.println("No eliminado");
       }
    }

    @FXML
    public void backWindow() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIMainWindow.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.show();

			GUIMainWindowController controller = loader.getController();
			

			Stage temp = (Stage) this.btnAddComment.getScene().getWindow();
			temp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    private void loadProducts() {
    	Utils.listProducts = (ArrayList<Product>) Utils.clientF.getProducts();
    	cbProducts.getItems().clear();
    	while(Utils.listProducts == null) {
    	}
    	for(Product product : Utils.listProducts) {
    		cbProducts.getItems().add(product);
    	}
    }
    @FXML
    public void selectComment() {
    	Product productTemp = cbProducts.getValue();
    	  if (productTemp == null) {
              System.out.println("Error: No se ha seleccionado un producto.");
              return;
          }
        Comment commentTemp = LogicComment.getComment(productTemp.getListComments(),Utils.clientF.getUser().getId());
        if(commentTemp != null) {
        	taComment.setText(commentTemp.getText());
        	sliderCalification.setValue(commentTemp.getQualification());
        	btnDeleteComent.setDisable(false);
        	btnEditComment.setDisable(false);
        	taComment.setEditable(false);
        }
        else {
        	taComment.clear();
        	sliderCalification.setValue(0);
        	taComment.setEditable(true);
        	btnDeleteComent.setDisable(true);
        	btnEditComment.setDisable(true);
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
 


                 Stage temp = (Stage) this.btnAddComment.getScene().getWindow();
                 temp.close();
             } catch (Exception e) {
                 e.printStackTrace();
             }
      	
      }
    @FXML
    public void consult() {
    	taFilterComment.clear();
    	LocalDate date = dpFilter.getValue();
    	int value = (int) sliderFilter.getValue();
    	Product productTemp = cbProducts.getValue();
    	if(date != null && value != 0) {
    		notify("Solo se puede filtrar por un medio a la vez", lblMessage);
    		dpFilter.setValue(null);
    		sliderFilter.setValue(0);
    	}
    	else if(date != null) {
    		String text = LogicComment.filterCommentDate(productTemp, date);
        	taFilterComment.setText(text);
        	dpFilter.setValue(null);
    	}
    	else if (value != 0){
    		String text = LogicComment.filterCommentValue(productTemp, value);
        	taFilterComment.setText(text);
        	sliderFilter.setValue(0);
    	}
    	else {
    		notify("Escoja un filtro para mostrar comentarios", lblMessage);
    	}
    	
    }
    private void notify(String message, Label lblMessage) {
		Platform.runLater(() -> {
			lblMessage.getStyleClass().clear();
			lblMessage.setText(message);
			lblMessage.getStyleClass().add("lblNotification");

			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> lblMessage.setText("")));
			timeline.setCycleCount(1);
			timeline.play();
		});
	}
}
