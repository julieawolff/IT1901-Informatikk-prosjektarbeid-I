module greenhouse.ui {
    requires greenhouse.core;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    opens ui to javafx.graphics, javafx.fxml;
}
