package s25.cs151.application.control;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import s25.cs151.application.Main;
import s25.cs151.application.controller.DefineController;
import s25.cs151.application.model.IModel;
import s25.cs151.application.utils.IDatabaseExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;

/**
 * A table cell control which contains buttons to edit or delete data for the row.
 *
 * @param <T> the data model type
 */
public class ActionsTableCell<T extends IModel<T>> extends TableCell<T, T> {
    protected String modelName;
    protected String definePageName;
    protected IDatabaseExecutor<T> databaseDeleter;

    public ActionsTableCell(String modelName, String definePageName, IDatabaseExecutor<T> databaseDeleter) {
        this.modelName = modelName;
        this.definePageName = definePageName;
        this.databaseDeleter = databaseDeleter;
    }

    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            this.setGraphic(null);
            return;
        }

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(5);
        this.setGraphic(buttonsBox);

        // Delete button
        if (databaseDeleter != null) {
            Button deleteButton = createButton("Delete", "trash.png");
            deleteButton.setOnAction(_ -> this.attemptDelete(item));
            buttonsBox.getChildren().add(deleteButton);
        }
        // Edit button
        if (definePageName != null) {
            Button editButton = createButton("Edit", "edit.png");
            editButton.setOnAction(_ -> {
                @SuppressWarnings("unchecked")
                DefineController<T> controller = (DefineController<T>) Main.switchPage(this.definePageName);
                assert controller != null;
                controller.startEditing(item);
            });
            buttonsBox.getChildren().add(editButton);
        }
    }

    protected void attemptDelete(T item) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete " + this.modelName + "?");
        confirmation.setHeaderText("Delete " + this.modelName + "?");
        confirmation.setContentText("Are you sure you want to delete the " + this.modelName.toLowerCase() + " for " + item.toString() + "?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        try {
            this.databaseDeleter.execute(item);
        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Unknown Error");
            error.setHeaderText("Unknown Error");
            error.setContentText("An unknown error occurred while deleting the " + this.modelName.toLowerCase() + ".");
            error.show();
        }
    }

    protected Button createButton(String tooltip, String imageFileName) {
        Button button = new Button("");
        button.setTooltip(new Tooltip(tooltip));

        try (
                InputStream imageStream = Main.class.getResourceAsStream(imageFileName);
        ) {
            assert imageStream != null;
            button.setGraphic(new ImageView(new Image(imageStream)));
        } catch (IOException e) {
            // Should never happen
            e.printStackTrace();
            button.setText(tooltip);
        }

        return button;
    }
}
