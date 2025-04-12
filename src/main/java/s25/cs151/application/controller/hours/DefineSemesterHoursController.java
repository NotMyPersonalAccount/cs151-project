package s25.cs151.application.controller.hours;

import com.dlsc.formsfx.model.structure.Form;
import javafx.fxml.FXML;
import s25.cs151.application.control.BasicDefinePage;
import s25.cs151.application.forms.Forms;
import s25.cs151.application.utils.DatabaseHelper;
import s25.cs151.application.Main;
import s25.cs151.application.model.SemesterHours;

import java.sql.SQLException;

public class DefineSemesterHoursController
{
    private Form form;
    private SemesterHours semesterHours;

    @FXML
    private BasicDefinePage page;

    /**
     * Render the form on page initialization
     */
    @FXML
    public void initialize() {
        // Initialize model for the form
        this.semesterHours = new SemesterHours();

        // Create the FormsFX form
        this.form = Forms.createSemesterHoursForm(this.semesterHours);
        this.page.setForm(this.form);
        this.page.setOnSubmit(this::onSubmit);
    }

    public void onSubmit() {
        try {
            DatabaseHelper.insertSemesterHours(this.semesterHours);
            Main.switchPage("semester-hours.fxml");
        } catch (SQLException e) {
            // SQLite error code 19 is constraint failed (in this case, because of primary key uniqueness).
            if (e.getErrorCode() == 19) {
                this.page.showError("Semester hours already exist for this semester/year combination.");
                return;
            }
            this.page.showError("An unknown error occurred.");
        }
    }
}
