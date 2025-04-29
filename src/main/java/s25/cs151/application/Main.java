package s25.cs151.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import s25.cs151.application.utils.DatabaseHelper;

import java.sql.SQLException;

public class Main extends Application {
    private static Stage appStage;

    @Override
    public void start(Stage stage) throws SQLException {
        appStage = stage;

        // Initialize database on app launch.
        DatabaseHelper.initialize();

        stage.setTitle("BookieProfessor");
        switchPage("main-view.fxml");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Loads the given page from its fxml file and updates the primary stage to display it.
     * If the given page could not be found, the method will silently fail.
     *
     * @param page the full name of the page's fxml file under the `resources/` directory
     * @return the controller of the new page, or null if no page was loaded
     */
    public static Object switchPage(String page) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/" + page));
            Scene scene = appStage.getScene();

            // Create and set scene if one does not yet exist. Otherwise, update the existing scene.
            if (scene == null) {
                appStage.setScene(new Scene(fxmlLoader.load(), 1080, 720));
                return fxmlLoader.getController();
            }
            scene.setRoot(fxmlLoader.load());
            return fxmlLoader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}