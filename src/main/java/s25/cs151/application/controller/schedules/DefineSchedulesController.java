package s25.cs151.application.controller.schedules;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.util.BindingMode;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import s25.cs151.application.control.BasicDefinePage;
import s25.cs151.application.model.Schedule;
import s25.cs151.application.utils.DatabaseHelper;
import s25.cs151.application.Main;

import java.sql.SQLException;


public class DefineSchedulesController {;
    protected Form form;
    protected Schedule schedule;

    @FXML
    protected BasicDefinePage page;

    /**
     * Render the form on page initialization
     */
    @FXML
    public void initialize() {
        // Initialize model for the form
        this.schedule = new Schedule();

        // Create the FormsFX form
        this.form = Form.of(
                Group.of(
                        Field.ofStringType(this.schedule.name)
                                .label("Name")
                                .required("Name is required"),
                        Field.ofDate(this.schedule.date)
                                .label("Date")
                                .required("Date is required"),
                        Field.ofSingleSelectionType(new SimpleListProperty<>(FXCollections.observableList(DatabaseHelper.getAllSemesterTimeSlots())), this.schedule.timeSlot)
                                .label("Time Slot")
                            .required("Time Slot is required"),
                        Field.ofSingleSelectionType(new SimpleListProperty<>(FXCollections.observableList(DatabaseHelper.getAllCourses())), this.schedule.course)
                                .label("Course")
                            .required("Course is required"),
                        Field.ofStringType(this.schedule.reason)
                                .label("Reason"),
                        Field.ofStringType(this.schedule.comment)
                                .label("Comment")
                )
        ).title("Define Schedule").binding(BindingMode.CONTINUOUS);
        this.page.setForm(this.form);
        this.page.setOnSubmit(this::onSubmit);
    }

    public void onSubmit() {
        // TODO: Implement saving
        try {
            DatabaseHelper.insertSchedule(this.schedule);
            Main.switchPage("schedules.fxml");
        } catch (SQLException e) {
            this.page.showError("An error occurred while saving the schedule.");
        }
        /*System.out.println(this.schedule.getName());
        System.out.println(this.schedule.getDate());
        System.out.println(this.schedule.getTimeSlot());
        System.out.println(this.schedule.getCourse());
        System.out.println(this.schedule.getComment());
        System.out.println(this.schedule.getReason());*/
    }
}
