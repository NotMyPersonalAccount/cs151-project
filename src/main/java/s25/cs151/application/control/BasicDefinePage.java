package s25.cs151.application.control;

import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import s25.cs151.application.Main;

import java.io.IOException;
import java.util.ArrayList;

public class BasicDefinePage extends ScrollPane {
    @FXML
    protected Label titleLabel;
    @FXML
    protected VBox formBox;
    @FXML
    protected Button cancelButton;
    @FXML
    protected Button submitButton;
    @FXML
    protected Label errorHint;

    protected String parentPage;
    protected String title;

    protected Form form;
    protected Runnable onSubmit;

    public BasicDefinePage() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("basic-define-page.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Handle button actions
        this.cancelButton.setOnAction(_ -> this.onCancelClicked());
        this.submitButton.setOnAction(_ -> this.onSubmitClicked());
    }

    protected void onCancelClicked() {
        Main.switchPage(this.parentPage);
    }

    protected void onSubmitClicked() {
        if (!this.form.isValid()) {
            // Figure out which fields are invalid and display an error.
            ArrayList<String> invalidFields = new ArrayList<>();
            this.form.getFields().forEach(field -> {
                if (!field.isValid()) {
                    invalidFields.add(field.getLabel());
                }
            });
            this.showError("The form has invalid values and can not be submitted: " + String.join(", ", invalidFields));
            return;
        }

        this.onSubmit.run();
    }

    /**
     * Displays an error message at the bottom of the page.
     * @param error The error message.
     */
    public void showError(String error) {
        this.errorHint.setVisible(true);
        this.errorHint.setText(error);
        this.errorHint.setStyle("-fx-text-fill: red;");
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.titleLabel.setText(title);
    }

    public String getParentPage() {
        return this.parentPage;
    }

    public void setParentPage(String parentPage) {
        this.parentPage = parentPage;
    }

    public Form getForm() {
        return this.form;
    }

    public void setForm(Form form) {
        this.form = form;

        // Add the form to our placeholder container in the fxml template
        this.formBox.getChildren().setAll(new FormRenderer(this.form));
    }

    public void setOnSubmit(Runnable onSubmit) {
        this.onSubmit = onSubmit;
    }
}
