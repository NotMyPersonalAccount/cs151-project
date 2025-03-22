package s25.cs151.application;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.model.SemesterHours;

import java.util.Map;

public class SemesterHoursTableController {
    @FXML
    protected TableView<SemesterHours> table;

    @FXML
    protected TableColumn<SemesterHours, String> semester;
    @FXML
    protected TableColumn<SemesterHours, Integer> year;
    @FXML
    protected TableColumn<SemesterHours, String> days;

    @FXML
    protected void initialize() {
        // Connect table columns with the model.
        this.semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        this.year.setCellValueFactory(new PropertyValueFactory<>("year"));
        // Days in the model are a List<String>, but we want to display a comma-separated string. Create an observable
        // string with commas from the observable list.
        this.days.setCellValueFactory(p -> {
            SemesterHours semesterHours = p.getValue();
            ObjectProperty<String> days = new SimpleObjectProperty<>(String.join(", ", semesterHours.days.getValue()));
            // Overkill for this project, but keep the string in sync with the list.
            semesterHours.days.addListener((_, _, newValue) -> {
                days.setValue(String.join(", ", newValue));
            });
            return days;
        });

        // Set the sorting order of the semester column
        this.semester.setComparator((a, b) -> {
            Map<String, Integer> order = Map.of("Spring", 0, "Summer", 1, "Fall", 2, "Winter", 3);
            return order.get(a).compareTo(order.get(b));
        });

        // Set items in the table.
        this.table.setItems(FXCollections.observableList(DatabaseHelper.getAllSemesterHours()));
    }
}
