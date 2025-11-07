module Inventario {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.swing;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
	exports application;
}
