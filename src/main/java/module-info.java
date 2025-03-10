module s25.cs151.bookieprofessor {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens s25.cs151.bookieprofessor to javafx.fxml;
    exports s25.cs151.bookieprofessor;
}