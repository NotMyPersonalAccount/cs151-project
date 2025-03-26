package s25.cs151.application;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.IntegerField;
import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.view.controls.SimpleCheckBoxControl;
import javafx.fxml.FXML;
import s25.cs151.application.model.SemesterHours;

import java.sql.SQLException;
import java.time.Year;

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

        // `Field.ofIntegerType` does not appear to correctly bind its value property to the passed property.
        IntegerField yearField = Field.ofIntegerType(Year.now().getValue())
                .validate( IntegerRangeValidator.between(1000, 9999, "Four digits expected"))
                .label("Year");
        this.semesterHours.year.bind(yearField.valueProperty());
        // Create the FormsFX form
        this.form = Form.of(
            Group.of(
                    Field.ofSingleSelectionType(this.semesterHours.allSeasons, this.semesterHours.semester)
                            .label("Semester"),
                    yearField,
                    Field.ofMultiSelectionType(this.semesterHours.allDays, this.semesterHours.days)
                            .label("Days")
                            .required("At least one day must be selected")
                            .render(new SimpleCheckBoxControl<>())
            )
        ).title("Define Semester Hours");
        this.page.setForm(this.form);
        this.page.setOnSubmit(this::onSubmit);
    }

    public void onSubmit() {
        try {
            DatabaseHelper.insertSemesterHours(this.semesterHours);
            Main.switchPage("main-view.fxml");
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
