module Proyecto_Client {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens business to javafx.graphics, javafx.fxml;
}
