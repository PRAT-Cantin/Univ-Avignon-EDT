module com.example.edtunivavignon {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.controlsfx.controls;


    opens com.example.edtunivavignon to javafx.fxml;
    exports com.example.edtunivavignon;
}