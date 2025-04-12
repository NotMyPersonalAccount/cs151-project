package s25.cs151.application.controller.schedules;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.Main;
import s25.cs151.application.model.Schedule;
import s25.cs151.application.model.SemesterTimeSlot;
import s25.cs151.application.utils.DatabaseHelper;
import s25.cs151.application.model.Course;

public class SchedulesController {
    @FXML
    protected TableView<Schedule> table;

    @FXML
    protected TableColumn<Schedule, String> name;
    @FXML
    protected TableColumn<Schedule, String> date;
    @FXML
    protected TableColumn<Schedule, String> timeSlot;
    @FXML
    protected TableColumn<Schedule, String> course;
    @FXML
    protected TableColumn<Schedule, String> reason;
    @FXML
    protected TableColumn<Schedule, String> comment;

    @FXML
    protected void initialize() {
        // Connect table columns with the model
        this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.date.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() -> cellData.getValue().getDate().toString())
        );
        this.timeSlot.setCellValueFactory(cellData -> {
            ObjectProperty<SemesterTimeSlot> timeSlotProp = cellData.getValue().timeSlot;
            return Bindings.createStringBinding(() -> timeSlotProp.get().toString(), timeSlotProp);
        });
        this.course.setCellValueFactory(cellData -> {
            ObjectProperty<Course> courseProp = cellData.getValue().course;
            return Bindings.createStringBinding(() -> courseProp.get().toString(), courseProp);
        });
        this.reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        this.comment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        // Load data into the table
        this.table.setItems(FXCollections.observableList(DatabaseHelper.getAllSchedules()));
    }

    @FXML
    protected void onDefineSchedulesClicked() {
        Main.switchPage("define-schedules.fxml");
    }
}
