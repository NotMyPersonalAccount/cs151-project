package s25.cs151.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage appStage;

    @Override
    public void start(Stage stage) {
        appStage = stage;

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
     */
    public static void switchPage(String page) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(page));
            Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
            appStage.setScene(scene);
        } catch (Exception e) {
            // NOOP
        }
    }
}