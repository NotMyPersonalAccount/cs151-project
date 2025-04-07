package s25.cs151.application.controller.courses;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.Main;
import s25.cs151.application.utils.DatabaseHelper;
import s25.cs151.application.model.Course;
import s25.cs151.application.model.SemesterHours;

public class CoursesController {
    @FXML
    protected TableView<Course> table;

    @FXML
    protected TableColumn<SemesterHours, String> courseCode;
    @FXML
    protected TableColumn<SemesterHours, String> courseName;
    @FXML
    protected TableColumn<SemesterHours, String> sectionNumber;

    @FXML
    protected void initialize() {
        // Connect table columns with the model.
        this.courseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        this.courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        this.sectionNumber.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));

        // Set items in the table.
        this.table.setItems(FXCollections.observableList(DatabaseHelper.getAllCourses()));
    }

    @FXML
    protected void onDefineCoursesClick() {
        Main.switchPage("define-courses.fxml");
    }
}
