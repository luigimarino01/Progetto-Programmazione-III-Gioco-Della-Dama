module com.example.damafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;
    requires java.sql;
    opens com.example.damafx to javafx.fxml;
    //opens com.example.damafx.Model to javafx.fxml;
    exports com.example.damafx;
    exports com.example.damafx.Model.Giocatore;
    exports com.example.damafx.Controller;
    opens com.example.damafx.Controller to javafx.fxml;
    //exports com.example.damafx.Model;

}