package s25.cs151.application.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import s25.cs151.application.Main;

import java.io.IOException;

public class Navbar extends FlowPane {
    protected static String activePage = "main-view.fxml";

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
        this.home.setOnAction(_ -> this.switchPage("main-view.fxml"));
        //this.schedules.setOnAction(_ -> this.switchPage("schedules.fxml"));
        this.semester_hours.setOnAction(_ -> this.switchPage("semester-hours.fxml"));
        this.courses.setOnAction(_ -> this.switchPage("courses.fxml"));
        this.time_slots.setOnAction(_ -> this.switchPage("semester-time-slots.fxml"));
    }

    @FXML
    public void initialize() {
        // A hack to keep focus on the clicked button when recreating the scene on page switches.
        this.sceneProperty().addListener((_, _, _) -> {
            Button activeButton = switch (activePage) {
                case "semester-hours.fxml" -> this.semester_hours;
                case "courses.fxml" -> this.courses;
                case "semester-time-slots.fxml" -> this.time_slots;
                default -> this.home;
            };

            activeButton.requestFocus();
        });
    }

    protected void switchPage(String page) {
        if (activePage.equals(page)) return;

        activePage = page;
        Main.switchPage(page);
    }
}
