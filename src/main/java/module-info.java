module s25.cs151.application {
    requires javafx.fxml;
    requires java.sql;

    requires com.dlsc.formsfx;
    requires com.dlsc.gemsfx;

    opens s25.cs151.application to javafx.fxml;
    exports s25.cs151.application;
    exports s25.cs151.application.model;
    exports s25.cs151.application.controller.courses;
    opens s25.cs151.application.controller.courses to javafx.fxml;
    exports s25.cs151.application.controller.timeslots;
    opens s25.cs151.application.controller.timeslots to javafx.fxml;
    exports s25.cs151.application.controller.hours;
    opens s25.cs151.application.controller.hours to javafx.fxml;
    exports s25.cs151.application.controller.schedules;
    opens s25.cs151.application.controller.schedules to javafx.fxml;
    exports s25.cs151.application.utils;
    opens s25.cs151.application.utils to javafx.fxml;
    exports s25.cs151.application.control;
    opens s25.cs151.application.control to javafx.fxml;
}