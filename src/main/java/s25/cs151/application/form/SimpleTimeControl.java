package s25.cs151.application.form;

import com.dlsc.formsfx.view.controls.SimpleControl;
import com.dlsc.gemsfx.TimePicker;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class SimpleTimeControl extends SimpleControl<TimeField> {
    protected Label fieldLabel;
    protected TimePicker picker;
    protected Label readOnlyLabel;
    protected StackPane stack;

    public void initializeParts() {
        super.initializeParts();

        this.stack = new StackPane();

        this.fieldLabel = new Label();
        this.readOnlyLabel = new Label();
        this.picker = new TimePicker();
    }

    public void layoutParts() {
        super.layoutParts();

        int columns = this.field.getSpan();
        this.readOnlyLabel.getStyleClass().add("read-only-label");

        this.picker.setMaxWidth(Double.MAX_VALUE);

        this.stack.setAlignment(Pos.CENTER_LEFT);
        this.stack.getChildren().addAll(this.picker, this.readOnlyLabel);

        Node labelDescription = this.field.getLabelDescription();
        Node valueDescription = this.field.getValueDescription();

        this.add(this.fieldLabel, 0, 0, 2, 1);
        if (labelDescription != null) {
            GridPane.setValignment(labelDescription, VPos.TOP);
            this.add(labelDescription, 0, 1, 2, 1);
        }
        this.add(stack, 2, 0, columns - 2, 1);
        if (valueDescription != null) {
            GridPane.setValignment(valueDescription, VPos.TOP);
            this.add(valueDescription, 2, 1, columns - 2, 1);
        }
    }

    public void setupBindings() {
        super.setupBindings();

        this.picker.timeProperty().bindBidirectional(this.field.valueProperty());
        this.fieldLabel.textProperty().bind(this.field.labelProperty());
    }
}
