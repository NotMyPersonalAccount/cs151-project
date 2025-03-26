package s25.cs151.application.model;

import javafx.beans.property.*;

import java.time.LocalTime;

public class SemesterTimeSlots {
    public final ObjectProperty<LocalTime> from = new SimpleObjectProperty<>(LocalTime.of(12,0));
    public final ObjectProperty<LocalTime> to = new SimpleObjectProperty<>(LocalTime.of(13,0));

    public SemesterTimeSlots() {}
}
