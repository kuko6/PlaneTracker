package controller;

import controller.abstracts.PlaneInfo;
import helper.Storage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import model.Airport;
import model.planes.Plane;
import view.PlaneRenderer;

import java.util.ArrayList;

/**
 * AirTraffic starts another thread that repeats every second. It's used to update Objects of type Plane, aswell as display them on the scene.
 * Thread is set as daemon in MapController and interrupts itself after user clicked the log out button or closed the app completely.
 *
 * @author Jakub Povinec
 */
public class AirTraffic implements Runnable, PlaneInfo {

    @FXML
    private AnchorPane currentScene;

    private ArrayList<Airport> airports;
    private ArrayList<Plane> planes;
    private ArrayList<Plane> tmpPlanes;

    private ArrayList<Node> images = new ArrayList<>();
    private PlaneRenderer planeRenderer = new PlaneRenderer();
    private Storage storage;

    /**
     * Constructor.
     *
     * @param currentScene main scene that is used to display elements on the screen
     * @param airports ArrayList of type Airport
     * @param planes ArrayList of type Plane
     * @param storage helper class that holds ArrayLists of classes Airport and Plane
     */
    public AirTraffic(AnchorPane currentScene, ArrayList<Airport> airports, ArrayList<Plane> planes, Storage storage) {
        //System.out.println("zacinam");
        this.airports = airports;
        this.planes = planes;
        this.currentScene = currentScene;
        this.storage = storage;
    }

    /**
     * Removes ImageView that represents Object Plane from currentScene, aswell as clears the ArrayList images.
     */
    private void clean() {
        currentScene.getChildren().removeAll(images);
        images.clear();
    }

    /**
     * Method is used to update position of planes and display planes on the map.
     * Images are drawn in class PlanRenderer, then added to the ArrayList.
     * After one second all images are deleted and cycle repeats.
     * Method also sets ImageViews in ArrayList images so they display PlaneInfo table after user double clicks on the ImageView.
     */
    @Override
    public void run() {
        while (true) {
            if (Main.counter == -1) { // -1 znamena, ze pouzivatel sa odhlasil
                //System.out.println("koncim");
                Thread.currentThread().interrupt();
                Platform.runLater(this::clean);
                break;
            }

            airports = storage.loadAirports();
            planes = storage.loadPlanes();
            tmpPlanes = new ArrayList<Plane>(planes); // takto, lebo menim list pocas toho ako ho prechadzam
            Platform.runLater(this::clean);
            int i = 0;
            for (Plane plane : tmpPlanes) {
                plane.fly();

                int finalI = i;
                i++;
                Platform.runLater(() -> {
                    images.add(planeRenderer.drawPlane(plane, currentScene)); // prida sa novy obrazok lietadlo do ArrayList
                    if (images.get(finalI) != null) {
                        images.get(finalI).setOnMouseClicked(e -> { // tu sa nastavi aby po dvojkliku sa zobrazila tabulka z PlaneInfo
                            if (e.getClickCount() == 2) {
                                currentScene.getChildren().removeIf(node -> node instanceof Line); // vymaze ciary z predchadzajuceho lietadla
                                showPlaneInfo(planes.get(finalI), currentScene, storage);
                            }
                        });
                    }
                });

                if (!plane.getStatus()) {
                    planes.remove(plane);
                    i--;
                }
            }

            storage.saveAirports(airports);
            storage.savePlanes(planes);

            try {
                Thread.sleep(1000); // opakuje sa kazdu sekundu
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
