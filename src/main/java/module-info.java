module com.example.projectthreealgorthem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projectthreealgorthem to javafx.fxml;
    exports com.example.projectthreealgorthem;
}