module ProyectoTiendaClient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
	requires javafx.base;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires java.desktop;

    opens business to javafx.graphics, javafx.fxml;
    opens domain to com.fasterxml.jackson.databind, javafx.base; // Permite acceso a JavaFX
    exports domain; 
}
