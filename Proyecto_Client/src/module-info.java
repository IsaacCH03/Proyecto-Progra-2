module ProyectoTiendaClient {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires com.fasterxml.jackson.databind;
	
	opens business to javafx.graphics, javafx.fxml;
	opens domain to com.fasterxml.jackson.databind;
}
