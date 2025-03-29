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
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> codeColumn;
    @FXML
    private TableColumn<Course, String> nameColumn;
    @FXML
    private TableColumn<Course, String> sectionColumn;
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
                                        value -> value.matches("\\d+"), "Section Number must be an integer"))
                                .required("Section Number is required")
                )
        ).title("Define Courses").binding(BindingMode.CONTINUOUS);

        this.page.setForm(this.form);
        this.page.setOnSubmit(this::onSubmit);

        setupTable();   // Setup the table columns
        loadCourses();  // Load data from DB into table
    }

    private void setupTable() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));
    }

    private void loadCourses() {
        try {
            List<Course> courses = DatabaseHelper.getAllCourses(); // You’ll need to implement this
            ObservableList<Course> observableCourses = FXCollections.observableArrayList(courses);
            observableCourses.sort((a, b) -> b.getCourseCode().compareTo(a.getCourseCode())); // Descending sort
            courseTable.setItems(observableCourses);
        } catch (SQLException e) {
            this.page.showError("Could not load courses from the database.");
        }
    }

    public void onSubmit() {
        try {
            DatabaseHelper.insertCourse(this.course);
            loadCourses();  // Refresh table after insert
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
