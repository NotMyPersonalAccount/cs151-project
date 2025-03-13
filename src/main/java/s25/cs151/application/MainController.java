package s25.cs151.application;

import javafx.fxml.FXML;

public class MainController {
    @FXML
    protected void onDefineSemesterHoursClick() {
        Main.switchPage("define-semester-hours.fxml");
    }
}