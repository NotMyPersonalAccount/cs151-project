module s25.cs151.application {
    requires javafx.fxml;
    requires java.sql;

    requires com.dlsc.formsfx;
    requires com.dlsc.gemsfx;

    opens s25.cs151.application to javafx.fxml;
    exports s25.cs151.application;
    exports s25.cs151.application.model;
}