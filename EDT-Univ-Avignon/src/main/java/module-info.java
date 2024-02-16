module com.example.edtunivavignon {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.edtunivavignon to javafx.fxml;
    exports com.example.edtunivavignon;
}