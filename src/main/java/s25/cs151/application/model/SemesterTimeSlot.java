package s25.cs151.application.model;

import javafx.beans.property.*;

import java.time.LocalTime;

public class SemesterTimeSlot {
    protected int id;

    public final ObjectProperty<LocalTime> from = new SimpleObjectProperty<>(LocalTime.of(12,0));
    public final ObjectProperty<LocalTime> to = new SimpleObjectProperty<>(LocalTime.of(13,0));

    public SemesterTimeSlot() {}

    public SemesterTimeSlot(int id, LocalTime from, LocalTime to) {
        this.id = id;
        this.from.set(from);
        this.to.set(to);
    }

    public int getId() {
        return this.id;
    }
}
