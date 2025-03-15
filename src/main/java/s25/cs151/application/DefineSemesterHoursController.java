package s25.cs151.application;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.view.controls.SimpleCheckBoxControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.time.Year;
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
                    Field.ofIntegerType(Year.now().getValue())
                            .validate( IntegerRangeValidator.between(1000, 9999, "Four digits expected"))
                            .label("Year"),
                    Field.ofMultiSelectionType(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"))
                            .label("Days")
                            .required("At least one day must be selected")
                            .render(new SimpleCheckBoxControl<>())
            )
        ).title("Define Semester Hours");

        // Add the form to our placeholder container in the fxml template
        formBox.getChildren().add(new FormRenderer(form));
    }
}
