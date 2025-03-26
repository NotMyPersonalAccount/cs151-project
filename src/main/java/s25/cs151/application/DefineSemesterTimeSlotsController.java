package s25.cs151.application;

import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import javafx.fxml.FXML;
import s25.cs151.application.form.TimeField;
import s25.cs151.application.model.SemesterTimeSlots;

public class DefineSemesterTimeSlotsController
{
    private Form form;
    private SemesterTimeSlots semesterTimeSlots;

    @FXML
    private BasicDefinePage page;

    /**
     * Render the form on page initialization
     */
    @FXML
    public void initialize() {
        // Initialize model for the form
        this.semesterTimeSlots = new SemesterTimeSlots();

        // Create the FormsFX form
        this.form = Form.of(
            Group.of(
                    new TimeField(this.semesterTimeSlots.from).label("From"),
                    new TimeField(this.semesterTimeSlots.to).label("To")
            )
        ).title("Define Semester Time Slots");
        this.page.setForm(this.form);
        this.page.setOnSubmit(this::onSubmit);
    }

    public void onSubmit() {
        System.out.println(this.semesterTimeSlots.from);
        System.out.println(this.semesterTimeSlots.to);
    }
}
