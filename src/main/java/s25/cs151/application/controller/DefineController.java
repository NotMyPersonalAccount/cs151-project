package s25.cs151.application.controller;

import com.dlsc.formsfx.model.structure.Form;
import javafx.fxml.FXML;
import s25.cs151.application.Main;
import s25.cs151.application.control.BasicDefinePage;
import s25.cs151.application.forms.Forms;
import s25.cs151.application.model.IModel;
import s25.cs151.application.utils.DatabaseHelper;

import java.sql.SQLException;

/**
 * Base class for all Define page controllers which handles the creation and processing of the definition/editing
 * forms.
 * @param <T> the model type being defined in the Define page
 */
abstract public class DefineController<T extends IModel> {
    @FXML
    protected BasicDefinePage page;

    protected Form form;
    protected T currentModel;

    public DefineController(T currentModel) {
        this.currentModel = currentModel;
    }

    @FXML
    public void initialize() {
        this.form = Forms.createForm(currentModel);
        this.page.setForm(this.form);
        this.page.setOnSubmit(this::onSubmit);
    }

    public void onSubmit() {
        try {
            DatabaseHelper.insert(this.currentModel);
            Main.switchPage(this.page.getParentPage());
        } catch (SQLException e) {
            // SQLite error code 19 is constraint failed (in our case, because of primary key uniqueness).
            if (e.getErrorCode() == 19) {
                this.page.showError("Duplicate entry. Combination already exists.");
                return;
            }
            this.page.showError("An unknown error occurred.");
        }
    }
}
