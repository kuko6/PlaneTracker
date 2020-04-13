package controller.abstracts;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public interface Controller {

    @FXML
    void initialize();

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

