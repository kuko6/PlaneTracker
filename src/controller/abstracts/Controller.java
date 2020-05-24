package controller.abstracts;

import controller.MapController;
import helper.Storage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controller interface that is implemented by every controller class except Main and AirTraffic.
 * It implements method initialize which is essential for FXML Controllers as well as methods for switching scenes.
 *
 * @author Jakub Povinec
 */
public interface Controller {

    @FXML
    void initialize();

    /**
     * Deletes all children from currentScene and adds children from view Map to the currentScene.
     * Also sets (loads) helper class {@link Storage}.
     * Storage is used to move ArrayLists, airports and planes, between different controllers.
     *
     * @param currentScene main scene that is used to display elements on the screen
     * @param storage helper class that holds ArrayLists of classes Airport and Plane
     */
    default void switchToMap(AnchorPane currentScene, Storage storage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Map.fxml"));
            AnchorPane newScene = loader.load();

            MapController controller = loader.getController();
            controller.loadHelper(storage);
            currentScene.getChildren().clear();
            currentScene.getChildren().add(newScene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes all children from currentScene and adds children from view scene to the currentScene.
     *
     * @param currentScene scene used by controller that called this method
     * @param scene specifies name of view that should be displayed
     */
    default void switchScene(AnchorPane currentScene, String scene) {
        try {
            AnchorPane newScene = FXMLLoader.load(getClass().getResource("../view/" + scene + ".fxml"));
            currentScene.getChildren().clear();
            currentScene.getChildren().add(newScene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

