package s25.cs151.application.form;

import com.dlsc.formsfx.model.structure.DataField;
import javafx.beans.property.ObjectProperty;

import java.time.LocalTime;

public class TimeField extends DataField<ObjectProperty<LocalTime>, LocalTime, TimeField> {
    public TimeField(ObjectProperty<LocalTime> valueProperty) {
        super(valueProperty, valueProperty);

        this.rendererSupplier = () -> new SimpleTimeControl();
    }
}
