package s25.cs151.application.controller.schedules;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import s25.cs151.application.Main;
import s25.cs151.application.model.Schedule;
import s25.cs151.application.model.SemesterTimeSlot;
import s25.cs151.application.utils.DatabaseHelper;
import s25.cs151.application.model.Course;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class SchedulesController {
    protected ObservableList<Schedule> schedules;
    protected StringProperty searchQuery = new SimpleStringProperty("");

    @FXML
    protected TextField searchField;

    @FXML
    protected TableView<Schedule> table;

    @FXML
    protected TableColumn<Schedule, String> name;
    @FXML
    protected TableColumn<Schedule, LocalDate> date;
    @FXML
    protected TableColumn<Schedule, SemesterTimeSlot> timeSlot;
    @FXML
    protected TableColumn<Schedule, Course> course;
    @FXML
    protected TableColumn<Schedule, String> reason;
    @FXML
    protected TableColumn<Schedule, String> comment;
    @FXML
    protected TableColumn<Schedule, Schedule> actions;

    @FXML
    protected void initialize() {
        // Connect table columns with the model
        this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.date.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.timeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        this.course.setCellValueFactory(new PropertyValueFactory<>("course"));
        this.reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        this.reason.setCellFactory(_ -> new TableCell<>() {
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setTooltip(empty ? null : new Tooltip(item));
            }
        });
        this.comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        this.comment.setCellFactory(_ -> new TableCell<>() {
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setTooltip(empty ? null : new Tooltip(item));
            }
        });

        // Setup actions buttons
        this.actions.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue()));
        this.actions.setCellFactory(_ -> new TableCell<>() {
            protected void updateItem(Schedule item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                HBox buttonsBox = new HBox();
                buttonsBox.setSpacing(5);
                setGraphic(buttonsBox);
                try {
                    // Delete button
                    Button deleteButton = new Button("", new ImageView(new Image(Objects.requireNonNull(Main.class.getResource("trash.png")).openStream())));
                    deleteButton.setTooltip(new Tooltip("Delete"));
                    deleteButton.setOnAction(_ -> attemptDeletion(item));
                    buttonsBox.getChildren().add(deleteButton);
                } catch (Exception e) {
                    // Should never happen
                }
            }
        });

        // Create and track an observable list of schedules
        this.schedules = FXCollections.observableArrayList(DatabaseHelper.getAllSchedules());
        // Create a filtered list from the observable lst
        FilteredList<Schedule> filteredSchedules = new FilteredList<>(this.schedules);
        // Bind the filtered list predicate to the search query
        filteredSchedules.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            return s -> s.getName().toLowerCase().contains(this.searchQuery.get().toLowerCase());
        }, this.searchQuery));
        // Load data into the table
        this.table.setItems(filteredSchedules);
    }

    protected void attemptDeletion(Schedule schedule) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Schedule?");
        confirmation.setHeaderText("Delete Schedule?");
        confirmation.setContentText("Are you sure you want to delete the schedule for " + schedule.getName() + "?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isEmpty() || result.get() != ButtonType.OK)
            return;

        try {
            DatabaseHelper.deleteSchedule(schedule);
            this.schedules.remove(schedule);
        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Unknown Error");
            error.setHeaderText("Unknown Error");
            error.setContentText("An unknown error occurred while deleting the schedule.");
            error.show();
        }
    }

    @FXML
    protected void onSearchClicked() {
        this.searchQuery.set(this.searchField.getText());
    }

    @FXML
    protected void onDefineSchedulesClicked() {
        Main.switchPage("define-schedules.fxml");
    }
}
