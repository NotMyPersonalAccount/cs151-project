package s25.cs151.application.controller;

import com.dlsc.formsfx.model.structure.Form;
import javafx.fxml.FXML;
import s25.cs151.application.Main;
import s25.cs151.application.control.DefinePage;
import s25.cs151.application.forms.IFormCreator;
import s25.cs151.application.model.IModel;
import s25.cs151.application.utils.IDatabaseExecutor;
import s25.cs151.application.utils.IDatabaseUpdater;

import java.sql.SQLException;

/**
 * Base class for all Define page controllers which handles the creation and processing of the definition/editing
 * forms.
 * @param <T> the model type being defined in the Define page
 */
abstract public class DefineController<T extends IModel<T>> {
    @FXML
    protected DefinePage page;

    protected Form form;
    protected IFormCreator<T> formCreator;

    protected T existingModel;
    protected T currentModel;

    protected IDatabaseExecutor<T> databaseInserter;
    protected IDatabaseUpdater<T> databaseUpdater;

    // TODO: Remove this constructor once all data models can be edited
    public DefineController(T currentModel, IFormCreator<T> formCreator, IDatabaseExecutor<T> databaseInserter) {
        this.currentModel = currentModel;
        this.formCreator = formCreator;
        this.databaseInserter = databaseInserter;
    }

    public DefineController(T currentModel, IFormCreator<T> formCreator, IDatabaseExecutor<T> databaseInserter, IDatabaseUpdater<T> databaseUpdater) {
        this(currentModel, formCreator, databaseInserter);
        this.databaseUpdater = databaseUpdater;
    }

    @FXML
    public void initialize() {
        this.initializeForm();
        this.page.setOnSubmit(this::onSubmit);
    }

    protected void initializeForm() {
        this.form = this.formCreator.create(this.currentModel);
        this.page.setForm(this.form);
    }

    public void startEditing(T existingModel) {
        this.existingModel = existingModel;
        this.currentModel = existingModel.copy();
        this.initializeForm();
        this.page.setEditing(true);
    }

    protected void onSubmit() {
        try {
            if (this.existingModel == null) {
                this.databaseInserter.execute(this.currentModel);
            } else {
                this.databaseUpdater.update(this.existingModel, this.currentModel);
            }
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
