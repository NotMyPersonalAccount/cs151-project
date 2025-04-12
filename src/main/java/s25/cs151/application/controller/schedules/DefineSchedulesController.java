package s25.cs151.application.controller.schedules;

import com.dlsc.formsfx.model.structure.Form;
import javafx.fxml.FXML;
import s25.cs151.application.control.BasicDefinePage;
import s25.cs151.application.forms.Forms;
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
        this.form = Forms.createScheduleForm(this.schedule);
        this.page.setForm(this.form);
        this.page.setOnSubmit(this::onSubmit);
    }

    public void onSubmit() {
        try {
            DatabaseHelper.insertSchedule(this.schedule);
            Main.switchPage("schedules.fxml");
        } catch (SQLException e) {
            this.page.showError("An error occurred while saving the schedule.");
        }
    }
}
