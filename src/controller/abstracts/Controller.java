package controller.abstracts;

import controller.MapController;
import controller.helper.Storage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public interface Controller {

    @FXML
    void initialize();

    default void switchToMap(AnchorPane currentScene, String scene, Storage storage) {
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

