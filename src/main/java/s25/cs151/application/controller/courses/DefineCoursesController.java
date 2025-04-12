package s25.cs151.application.controller.courses;

import com.dlsc.formsfx.model.structure.Form;
import javafx.fxml.FXML;
import s25.cs151.application.control.BasicDefinePage;
import s25.cs151.application.forms.Forms;
import s25.cs151.application.utils.DatabaseHelper;
import s25.cs151.application.Main;
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

        this.form = Forms.createCourseForm(this.course);
        this.page.setForm(this.form);
        this.page.setOnSubmit(this::onSubmit);
    }

    public void onSubmit() {
        try {
            DatabaseHelper.insertCourse(this.course);
            Main.switchPage("courses.fxml");
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                this.page.showError("Duplicate entry. Combination already exists.");
                return;
            }
            this.page.showError("An unknown error occurred.");
        }
    }
}
