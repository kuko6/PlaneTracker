package controller;

import controller.abstracts.PlaneInfo;
import controller.helper.Storage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import model.Airport;
import model.planes.Plane;
import view.PlaneRenderer;

import java.util.ArrayList;

public class AirTraffic implements Runnable, PlaneInfo {

    @FXML
    private AnchorPane currentScene;

    private ArrayList<Airport> airports;
    private ArrayList<Plane> planes;
    private ArrayList<Plane> tmpPlanes;

    private ArrayList<Node> images = new ArrayList<>();
    private PlaneRenderer planeRenderer = new PlaneRenderer();
    private Storage storage;

    public AirTraffic(AnchorPane currentScene, ArrayList<Airport> airports, ArrayList<Plane> planes, Storage storage) {
        //System.out.println("zacinam");
        this.airports = airports;
        this.planes = planes;
        this.currentScene = currentScene;
        this.storage = storage;
    }

    private void clean() {
        currentScene.getChildren().removeAll(images);
        images.clear();
    }

    @Override
    public void run() {
        while (true) {
            if (Main.counter == -1) {
                //System.out.println("koncim");
                Platform.runLater(this::clean);
                Thread.currentThread().interrupt();
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
                    images.add(planeRenderer.drawPlane(plane, currentScene));
                    if (images.get(finalI) != null) {
                        images.get(finalI).setOnMouseClicked(e -> {
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
