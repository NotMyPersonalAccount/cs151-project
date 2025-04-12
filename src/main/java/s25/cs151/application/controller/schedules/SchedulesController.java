package s25.cs151.application.controller.schedules;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.Main;
import s25.cs151.application.model.Schedule;
import s25.cs151.application.model.SemesterTimeSlot;
import s25.cs151.application.utils.DatabaseHelper;
import s25.cs151.application.model.Course;

import java.time.LocalDate;

public class SchedulesController {
    @FXML
    protected TableView<Schedule> table;

    @FXML
    protected TableColumn<Schedule, String> name;
    @FXML
    protected TableColumn<Schedule, LocalDate> date;
    @FXML
    protected TableColumn<Schedule, SemesterTimeSlot> timeSlot;
    @FXML
    protected TableColumn<Schedule, Course> course;
    @FXML
    protected TableColumn<Schedule, String> reason;
    @FXML
    protected TableColumn<Schedule, String> comment;

    @FXML
    protected void initialize() {
        // Connect table columns with the model
        this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.date.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.timeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        this.course.setCellValueFactory(new PropertyValueFactory<>("course"));
        this.reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        this.reason.setCellFactory(_ ->
                new TableCell<>() {
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setText(item);
                            setTooltip(new Tooltip(item));
                        }
                    }
                }
        );
        this.comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        this.comment.setCellFactory(_ ->
            new TableCell<>() {
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        setText(item);
                        setTooltip(new Tooltip(item));
                    }
                }
            }
        );

        // Load data into the table
        this.table.setItems(FXCollections.observableList(DatabaseHelper.getAllSchedules()));
    }

    @FXML
    protected void onDefineSchedulesClicked() {
        Main.switchPage("define-schedules.fxml");
    }
}
