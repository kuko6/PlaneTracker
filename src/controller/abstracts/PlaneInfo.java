package controller.abstracts;

import controller.PlaneInfoController;
import helper.Storage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.exceptions.BlankTableException;
import model.planes.Plane;
import view.PlaneRenderer;

import java.io.IOException;

/**
 * PlaneInfo interface is implemented by classes that are required to show information by certain plane.
 * Implemented by controllers AirportInfoController and PlaneListController aswell as AirTraffic.
 * This interface implements methods that add table with information about selected plane to the scene.
 *
 * @author Jakub Povinec
 */
public interface PlaneInfo {
    PlaneRenderer renderer = new PlaneRenderer();

    /**
     * Method extracts plane from TableView and adds table with information about the selected plane.
     * Before adding the table it first checks if any plane was selected, otherwise would throw BlankTableException.
     * Also deletes nodes from previous view that are stored in array nodes and moves storage from previous controller to PlaneInfoController.
     *
     * @param table TableView on which user clicked.
     * @param currentScene main scene that is used to display elements on the screen
     * @param nodes array of nodes that were used by controller class that called this method
     * @param storage helper class that holds ArrayLists of classes Airport and Plane
     * @throws BlankTableException exception that is thrown if user clicked on blank table cell
     */
    default void showPlaneInfo(TableView<Plane> table, AnchorPane currentScene, Node[] nodes, Storage storage) {
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Plane selectedPlane = table.getSelectionModel().getSelectedItem();
                if (selectedPlane == null) { // ked je tabulka prazdna
                    try {
                        throw new BlankTableException();
                    } catch (BlankTableException ex) {
                        System.out.println("Nothing to click on.");
                    }

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
                    renderer.drawFlightPath(selectedPlane.getFlightPath(), currentScene);
                    currentScene.getChildren().add(newScene);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Overload of method showPlaneInfo, used only by class AirTraffic to display information about plane that user double clicked on on the screen.
     * This version of showPlaneInfo doesn't check for user input because it's done in AirTraffic and also it doesn't deletes nodes from previous view.
     *
     * @param selectedPlane plane that was selected by user
     * @param currentScene helper class that holds ArrayLists of classes Airport and Plane
     * @param storage main scene that is used to display elements on the screen
     */
    default void showPlaneInfo(Plane selectedPlane, AnchorPane currentScene, Storage storage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PlaneInfo.fxml"));
            AnchorPane newScene = loader.load();

            PlaneInfoController controller = loader.getController();
            controller.loadHelper(storage);
            controller.loadSelectedPlane(selectedPlane);
            renderer.drawFlightPath(selectedPlane.getFlightPath(), currentScene);
            currentScene.getChildren().add(newScene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
