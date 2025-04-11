package s25.cs151.application.controller.schedules;

import javafx.fxml.FXML;
import s25.cs151.application.Main;

public class SchedulesController {
    @FXML
    protected void initialize() {
        // TODO: Implement table
    }

    @FXML
    protected void onDefineSchedulesClicked() {
        Main.switchPage("define-schedules.fxml");
    }
}
