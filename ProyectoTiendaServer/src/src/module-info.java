module ProyectoTiendaServer {
	requires javafx.controls;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires com.fasterxml.jackson.annotation;
	exports domain;
	
	opens business to javafx.graphics, javafx.fxml;
	opens domain to com.fasterxml.jackson.databind;
}
