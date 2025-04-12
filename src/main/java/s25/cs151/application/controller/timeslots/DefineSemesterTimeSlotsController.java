package s25.cs151.application.controller.timeslots;

import com.dlsc.formsfx.model.structure.Form;
import javafx.fxml.FXML;
import s25.cs151.application.control.BasicDefinePage;
import s25.cs151.application.forms.Forms;
import s25.cs151.application.utils.DatabaseHelper;
import s25.cs151.application.Main;
import s25.cs151.application.model.SemesterTimeSlot;

import java.sql.SQLException;

public class DefineSemesterTimeSlotsController
{
    private Form form;
    private SemesterTimeSlot semesterTimeSlots;

    @FXML
    private BasicDefinePage page;

    /**
     * Render the form on page initialization
     */
    @FXML
    public void initialize() {
        // Initialize model for the form
        this.semesterTimeSlots = new SemesterTimeSlot();

        // Create the FormsFX form
        this.form = Forms.createSemesterTimeSlotForm(this.semesterTimeSlots);
        this.page.setForm(this.form);
        this.page.setOnSubmit(this::onSubmit);
    }

    public void onSubmit() {
        try {
            DatabaseHelper.insertSemesterTimeSlot(this.semesterTimeSlots);
            Main.switchPage("semester-time-slots.fxml");
        } catch (SQLException e) {
            this.page.showError("An unknown error occurred.");
        }
    }
}
