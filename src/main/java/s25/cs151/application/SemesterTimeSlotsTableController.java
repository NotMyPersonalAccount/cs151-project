package s25.cs151.application;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import s25.cs151.application.model.SemesterTimeSlot;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SemesterTimeSlotsTableController {
    @FXML
    protected TableView<SemesterTimeSlot> table;

    @FXML
    protected TableColumn<SemesterTimeSlot, String> from;

    @FXML
    protected TableColumn<SemesterTimeSlot, String> to;

    protected static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    protected void initialize() {
        // Connect table columns with the model
        this.from.setCellValueFactory(param -> {
            ObjectProperty<LocalTime> fromProperty = param.getValue().from;
            return Bindings.createStringBinding(() -> fromProperty.get().format(TIME_FORMATTER), fromProperty);
        });
        this.to.setCellValueFactory(param -> {
            ObjectProperty<LocalTime> toProperty = param.getValue().to;
            return Bindings.createStringBinding(() -> toProperty.get().format(TIME_FORMATTER), toProperty);
        });

        // Set items in the table
        this.table.setItems(FXCollections.observableList(DatabaseHelper.getAllSemesterTimeSlots()));
    }
}
