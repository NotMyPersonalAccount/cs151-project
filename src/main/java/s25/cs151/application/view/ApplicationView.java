package s25.cs151.application.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;

public class ApplicationView extends Scene {
    public ApplicationView() {
        super(new ScrollPane(), 1080, 720);
    }

    /**
     * Loads the given page from its fxml file and updates the root to display it.
     * If the given page could not be found, the method will silently fail.
     *
     * @param page the full name of the page's fxml file under the `resources/s25/cs151/application/view` directory
     * @return the controller of the new page, or null if no page was loaded
     */
    public Object switchPage(String page) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationView.class.getResource(page));
            this.setRoot(fxmlLoader.load());
            return fxmlLoader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
