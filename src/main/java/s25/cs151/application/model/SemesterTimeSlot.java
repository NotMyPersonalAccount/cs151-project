package s25.cs151.application.model;

import javafx.beans.property.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SemesterTimeSlot implements IModel<SemesterTimeSlot> {
    protected int id;

    public final ObjectProperty<LocalTime> from = new SimpleObjectProperty<>(LocalTime.of(12, 0));
    public final ObjectProperty<LocalTime> to = new SimpleObjectProperty<>(LocalTime.of(13, 0));

    protected static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public SemesterTimeSlot() {
    }

    public SemesterTimeSlot(int id, LocalTime from, LocalTime to) {
        this.id = id;
        this.from.set(from);
        this.to.set(to);
    }

    public int getId() {
        return this.id;
    }

    public LocalTime getFrom() {
        return this.from.get();
    }

    public LocalTime getTo() {
        return this.to.get();
    }

    public String toString() {
        return this.getFrom().format(TIME_FORMATTER) + " - " + this.getTo().format(TIME_FORMATTER);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SemesterTimeSlot semesterTimeSlot)) return false;

        return this.id == semesterTimeSlot.id &&
                this.getFrom() == semesterTimeSlot.getFrom() &&
                this.getTo().equals(semesterTimeSlot.getTo());
    }

    public SemesterTimeSlot copy() {
        return new SemesterTimeSlot(this.id, this.getFrom(), this.getTo());
    }
}
