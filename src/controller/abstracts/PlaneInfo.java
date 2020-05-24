package controller.abstracts;

import controller.PlaneInfoController;
import controller.helper.Storage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.planes.Plane;
import view.PlaneRenderer;

import java.io.IOException;

public interface PlaneInfo {
    PlaneRenderer renderer = new PlaneRenderer();

    default void showPlaneInfo(TableView<Plane> table, AnchorPane currentScene, Node[] nodes, Storage storage) {
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Plane selectedPlane = table.getSelectionModel().getSelectedItem();
                if (selectedPlane == null) { // ked je tabulka prazdna
                    System.out.println("nothing to click on :(");
                    return;
                }

                for (Node node : nodes) {
                    currentScene.getChildren().remove(node);
                }

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PlaneInfo.fxml"));
                    AnchorPane newScene = loader.load();

                    PlaneInfoController controller = loader.getController();
                    controller.loadSelectedPlane(selectedPlane);
                    controller.loadHelper(storage);
                    renderer.drawFlightPath(selectedPlane, currentScene);
                    currentScene.getChildren().add(newScene);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    default void showPlaneInfo(Plane selectedPlane, AnchorPane currentScene, Storage storage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PlaneInfo.fxml"));
            AnchorPane newScene = loader.load();

            PlaneInfoController controller = loader.getController();
            controller.loadHelper(storage);
            controller.loadSelectedPlane(selectedPlane);
            renderer.drawFlightPath(selectedPlane, currentScene);
            currentScene.getChildren().add(newScene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
