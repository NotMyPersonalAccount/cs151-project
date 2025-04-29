package s25.cs151.application;

import javafx.application.Application;
import javafx.stage.Stage;
import s25.cs151.application.utils.DatabaseHelper;
import s25.cs151.application.view.ApplicationView;

import java.sql.SQLException;

public class Main extends Application {
    private static final ApplicationView appView = new ApplicationView();

    @Override
    public void start(Stage stage) throws SQLException {
        // Initialize database on app launch.
        DatabaseHelper.initialize();

        stage.setTitle("BookieProfessor");
        stage.setScene(appView);
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
     * @param page the full name of the page's fxml file under the `resources/s25/cs151/application/view` directory
     * @return the controller of the new page, or null if no page was loaded
     */
    public static Object switchPage(String page) {
        return appView.switchPage(page);
    }
}