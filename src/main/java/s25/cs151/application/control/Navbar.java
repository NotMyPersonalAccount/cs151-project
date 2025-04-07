package s25.cs151.application.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import s25.cs151.application.Main;

import java.io.IOException;

public class Navbar extends FlowPane {
    @FXML
    protected Button home;
    @FXML
    protected Button schedules;
    @FXML
    protected Button semester_hours;
    @FXML
    protected Button courses;
    @FXML
    protected Button time_slots;

    public Navbar() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("navbar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Handle button actions
        this.home.setOnAction(_ -> Main.switchPage("main-view.fxml"));
        //this.schedules.setOnAction(_ -> Main.switchPage("schedules.fxml"));
        this.semester_hours.setOnAction(_ -> Main.switchPage("semester-hours.fxml"));
        this.courses.setOnAction(_ -> Main.switchPage("courses.fxml"));
        this.time_slots.setOnAction(_ -> Main.switchPage("semester-time-slots.fxml"));
    }
}
