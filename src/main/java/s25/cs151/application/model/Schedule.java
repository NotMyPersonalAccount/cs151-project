package s25.cs151.application.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Schedule {
    protected int id;

    public final StringProperty name = new SimpleStringProperty("");
    public final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(LocalDate.now());
    public final ObjectProperty<SemesterTimeSlot> timeSlot = new SimpleObjectProperty<>();
    public final ObjectProperty<Course> course = new SimpleObjectProperty<>();
    public final StringProperty reason = new SimpleStringProperty("");
    public final StringProperty comment = new SimpleStringProperty("");

    public Schedule() {}

    public Schedule(int id, String name, LocalDate date, SemesterTimeSlot timeSlot, Course course, String reason, String comment) {
        this.id = id;
        this.name.set(name);
        this.date.set(date);
        this.timeSlot.set(timeSlot);
        this.course.set(course);
        this.reason.set(reason);
        this.comment.set(comment);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name.get();
    }

    public LocalDate getDate() {
        return this.date.get();
    }

    public SemesterTimeSlot getTimeSlot() {
        return this.timeSlot.get();
    }

    public Course getCourse() {
        return this.course.get();
    }

    public String getReason() {
        return this.reason.get();
    }

    public String getComment() {
        return this.comment.get();
    }
}
