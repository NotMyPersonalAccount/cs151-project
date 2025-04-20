package s25.cs151.application.controller;

import com.dlsc.formsfx.model.structure.Form;
import javafx.fxml.FXML;
import s25.cs151.application.Main;
import s25.cs151.application.control.DefinePage;
import s25.cs151.application.forms.IFormCreator;
import s25.cs151.application.utils.IDatabaseExecutor;

import java.sql.SQLException;

/**
 * Base class for all Define page controllers which handles the creation and processing of the definition/editing
 * forms.
 * @param <T> the model type being defined in the Define page
 */
abstract public class DefineController<T> {
    @FXML
    protected DefinePage page;

    protected Form form;
    protected IFormCreator<T> formCreator;

    protected T currentModel;

    protected IDatabaseExecutor<T> databaseInserter;

    public DefineController(T currentModel, IFormCreator<T> formCreator, IDatabaseExecutor<T> databaseInserter) {
        this.currentModel = currentModel;
        this.formCreator = formCreator;
        this.databaseInserter = databaseInserter;
    }

    @FXML
    public void initialize() {
        this.form = this.formCreator.create(this.currentModel);
        this.page.setForm(this.form);
        this.page.setOnSubmit(this::onSubmit);
    }

    public void onSubmit() {
        try {
            this.databaseInserter.execute(this.currentModel);
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
