package s25.cs151.application.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.time.Year;
import java.util.List;

public class SemesterHours {
    public final ListProperty<String> allSeasons = new SimpleListProperty<>(FXCollections.observableArrayList("Spring", "Summer", "Fall", "Winter"));
    public final ObjectProperty<String> semester = new SimpleObjectProperty<>("Spring");

    public final IntegerProperty year = new SimpleIntegerProperty(Year.now().getValue());

    public final ListProperty<String> allDays = new SimpleListProperty<>(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"));
    public final ListProperty<String> days = new SimpleListProperty<>(FXCollections.observableArrayList());

    public SemesterHours() {
    }

    public SemesterHours(String semester, int year, String[] days) {
        this.semester.set(semester);
        this.year.set(year);
        this.days.setAll(days);
    }

    public String getSemester() {
        return this.semester.get();
    }

    public int getYear() {
        return this.year.get();
    }

    public List<String> getDays() {
        return this.days.get();
    }

    public String toString() {
        return this.getSemester() + " " + this.getYear();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SemesterHours semesterHours)) return false;

        return this.getSemester().equals(semesterHours.getSemester()) &&
                this.getYear() == semesterHours.getYear() &&
                this.getDays().equals(semesterHours.getDays());
    }
}
