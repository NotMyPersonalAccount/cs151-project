package s25.cs151.application;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.IntegerField;
import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.view.controls.SimpleCheckBoxControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import s25.cs151.application.model.SemesterHours;

import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;

public class DefineSemesterHoursController
{
    private Form form;
    private SemesterHours semesterHours;

    @FXML
    private VBox formBox;
    @FXML
    private Label errorHint;

    /**
     * Render the form on page initialization
     */
    @FXML
    public void initialize() {
        // Initialize model for this form
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

        // Add the form to our placeholder container in the fxml template
        formBox.getChildren().add(new FormRenderer(this.form));
    }

    /**
     * Displays an error message at the bottom of the page.
     * @param error The error message.
     */
    protected void showError(String error) {
        this.errorHint.setVisible(true);
        this.errorHint.setText(error);
        this.errorHint.setStyle("-fx-text-fill: red;");
    }

    @FXML
    public void onCancelClicked() {
        Main.switchPage("main-view.fxml");
    }

    @FXML
    public void onSubmitClicked() {
        if (!this.form.isValid()) {
            // Figure out which fields are invalid and display an error.
            ArrayList<String> invalidFields = new ArrayList<>();
            this.form.getFields().forEach(field -> {
               if (!field.isValid()) {
                   invalidFields.add(field.getLabel());
               }
            });
            this.showError("The form has invalid values and can not be submitted: " + String.join(", ", invalidFields));
            return;
        }

        try {
            DatabaseHelper.insertSemesterHours(this.semesterHours);
            Main.switchPage("main-view.fxml");
        } catch (SQLException e) {
            // SQLite error code 19 is constraint failed (in this case, because of primary key uniqueness).
            if (e.getErrorCode() == 19) {
                this.showError("Semester hours already exist for this semester/year combination.");
                return;
            }
            this.showError("An unknown error occurred.");
        }
    }
}
