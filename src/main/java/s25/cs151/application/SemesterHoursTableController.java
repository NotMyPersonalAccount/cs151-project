package s25.cs151.application;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.model.SemesterHours;

import java.util.Comparator;
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

    protected static final Map<String, Integer> SEMESTER_ORDERING = Map.of("Spring", 0, "Summer", 1, "Fall", 2, "Winter", 3);

    @FXML
    protected void initialize() {
        // Connect table columns with the model.
        this.semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        this.year.setCellValueFactory(new PropertyValueFactory<>("year"));
        // Days in the model are a List<String>, but we want to display a comma-separated string. Create an observable
        // string with commas from the observable list.
        this.days.setCellValueFactory(p -> {
            ListProperty<String> daysProperty = p.getValue().days;
            return Bindings.createStringBinding(() -> String.join(", ", daysProperty.getValue()), daysProperty);
        });

        // Set the sorting order of the semester column.
        this.semester.setComparator(Comparator.comparing(SEMESTER_ORDERING::get));

        // Set items in the table.
        this.table.setItems(FXCollections.observableList(DatabaseHelper.getAllSemesterHours()));
    }
}
