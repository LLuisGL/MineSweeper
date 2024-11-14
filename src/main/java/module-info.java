module com.mycompany.buscaminaspoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.buscaminaspoo to javafx.fxml;
    exports com.mycompany.buscaminaspoo;
}
