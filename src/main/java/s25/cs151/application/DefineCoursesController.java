package s25.cs151.application;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.util.BindingMode;
import com.dlsc.formsfx.model.validators.CustomValidator;
import javafx.fxml.FXML;
import s25.cs151.application.model.Course;

import java.sql.SQLException;

public class DefineCoursesController {
    private Form form;
    private Course course;

    @FXML
    private BasicDefinePage page;

    @FXML
    public void initialize() {
        this.course = new Course();

        this.form = Form.of(
                Group.of(
                        Field.ofStringType(this.course.courseCode)
                                .label("Course Code")
                                .required("Course Code is required"),
                        Field.ofStringType(this.course.courseName)
                                .label("Course Name")
                                .required("Course Name is required"),
                        Field.ofStringType(this.course.sectionNumber)
                                .label("Section Number")
                                .validate(CustomValidator.forPredicate(
                                        value -> value.matches("\\d+"), "Section Number must be an integer")
                                )
                                .required("Section Number is required")
                )
        ).title("Define Courses").binding(BindingMode.CONTINUOUS);
        this.page.setForm(this.form);
        this.page.setOnSubmit(this::onSubmit);
    }

    public void onSubmit() {
        try {
            DatabaseHelper.insertCourse(this.course);
            Main.switchPage("main-view.fxml");
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                this.page.showError("Duplicate entry. Combination already exists.");
                return;
            }
            this.page.showError("An unknown error occurred.");
        }
    }
}
