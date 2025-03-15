package s25.cs151.application;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class DefineSemesterHoursController
{
    @FXML
    private VBox formBox;

    /**
     * Render the form on page initialization
     */
    @FXML
    public void initialize() {
        // Create the FormsFX form
        Form form = Form.of(
            Group.of(
                    Field.ofSingleSelectionType(Arrays.asList("Spring", "Summer", "Fall", "Winter"), 0).label("Semester"),
                    Field.ofStringType("2025")
                            .label("Year (4-digit)"),
                    Field.ofBooleanType(false).label("Monday"),
                    Field.ofBooleanType(false).label("Tuesday"),
                    Field.ofBooleanType(false).label("Wednesday"),
                    Field.ofBooleanType(false).label("Thursday"),
                    Field.ofBooleanType(false).label("Friday"),
                    Field.ofStringType("")
                            .label("From Hour (HH:MM)"),
                    Field.ofStringType("")
                            .label("To Hour (HH:MM)"),
                    Field.ofStringType("")
                            .label("Course Code"),
                    Field.ofStringType("")
                            .label("Course Name"),
                    Field.ofStringType("")
                            .label("Section Number")
            )
        ).title("Define Semester Hours");

        // Add the form to our placeholder container in the fxml template
        formBox.getChildren().add(new FormRenderer(form));
    }
}
