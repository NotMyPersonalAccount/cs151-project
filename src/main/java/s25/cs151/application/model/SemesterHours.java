package s25.cs151.application.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.time.Year;

public class SemesterHours {
    public final ListProperty<String> allSeasons = new SimpleListProperty<>(FXCollections.observableArrayList("Spring", "Summer", "Fall", "Winter"));
    public final ObjectProperty<String> semester = new SimpleObjectProperty<>("Spring");

    public final IntegerProperty year = new SimpleIntegerProperty(Year.now().getValue());

    public final ListProperty<String> allDays = new SimpleListProperty<>(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"));
    public final ListProperty<String> days = new SimpleListProperty<>(FXCollections.observableArrayList());
}
