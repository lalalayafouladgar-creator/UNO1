module org.example.uno {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.uno to javafx.fxml;
    exports org.example.uno;
}