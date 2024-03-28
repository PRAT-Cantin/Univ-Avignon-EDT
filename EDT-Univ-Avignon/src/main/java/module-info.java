module com.example.edtunivavignon {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.edtunivavignon to javafx.fxml;
    exports com.example.edtunivavignon;
}