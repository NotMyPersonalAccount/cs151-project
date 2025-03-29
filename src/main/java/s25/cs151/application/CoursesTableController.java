package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.model.Course;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class CoursesTableController {
    @FXML
    protected TableView<Course> table;

    @FXML
    protected TableColumn<Course, String> courseCode;
    @FXML
    protected TableColumn<Course, String> courseName;
    @FXML
    protected TableColumn<Course, String> sectionNumber;

    @FXML
    protected void initialize() {
        courseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        sectionNumber.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));

        try {
            List<Course> allCourses = DatabaseHelper.getAllCourses();
            allCourses.sort(Comparator.comparing(Course::getCourseCode).reversed()); // Descending by courseCode
            table.setItems(FXCollections.observableList(allCourses));
        } catch (SQLException e) {
            e.printStackTrace(); // Optional: replace with custom error display
        }
    }
}
