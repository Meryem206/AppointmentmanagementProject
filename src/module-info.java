module Builder {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;

    opens application to javafx.fxml;
    exports application;
}
