package controller;

import controller.abstracts.PlaneInfo;
import controller.abstracts.Serialization;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import model.Airport;
import model.planes.Plane;
import view.PlaneRender;

import java.io.IOException;
import java.util.ArrayList;

public class AirTraffic extends Serialization implements Runnable, PlaneInfo {

    AnchorPane currentScene;
    ArrayList<Plane> tmpPlanes;
    ArrayList<Node> images = new ArrayList<>();
    PlaneRender planeRender = new PlaneRender();

    public AirTraffic(AnchorPane currentScene, ArrayList<Airport> airports, ArrayList<Plane> planes) {
        super();
        //System.out.println("zacinam");
        this.airports = airports;
        this.planes = planes;
        this.currentScene = currentScene;
    }

    private void clean() {
        //currentScene.getChildren().removeIf(node -> node instanceof Polygon);
        //currentScene.getChildren().removeIf(node -> node instanceof ImageView && node.getId().equals("plane"));
        /*
        for (Node node : currentScene.getChildren()) {
            if (node.getId() != null && node instanceof ImageView) {
                images.add(node);
            }
        }
         */
        currentScene.getChildren().removeAll(images);
        images.clear();
        //System.out.println(images.size());
    }

    @Override
    public void run() {
        while (true) {
            if (Main.counter == 0) { // toto teoreticky ani nebude treba
                //System.out.println("koncim");
                Thread.currentThread().interrupt();
                clean();
                break;
            }

            loadAirports();
            loadPlanes();
            tmpPlanes = new ArrayList<Plane>(planes); // takto, lebo menim list pocas toho ako ho prechadzam
            Platform.runLater(() -> clean());
            int i = 0;
            for (Plane plane : tmpPlanes) {
                plane.fly();
                //plane.contactAirport();
                int finalI = i;
                i++;
                Platform.runLater(() -> {
                    images.add(planeRender.drawPlane(plane.getFlightPath(), currentScene));
                    if (images.get(finalI) != null) {
                        images.get(finalI).setOnMouseClicked(e -> {
                            if (e.getClickCount() == 2) {
                                //currentScene.getChildren().removeIf(node -> node instanceof Line); // vymaze ciary z predchadzajuceho lietadla
                                showPlaneInfo(planes.get(finalI), currentScene);
                            }
                        });
                    }
                });

                /*
                if (plane instanceof Airbus) {
                    System.out.println(plane.getId());
                    System.out.println("presiel: " + plane.getFlightPath().getTravelled() + " rychlost: " + plane.getSpeed());
                }
                System.out.println();
                 */
                //System.out.println(planes.size());
                if (!plane.getStatus()) {
                    planes.remove(plane);
                    i--;
                }
            }
            //System.out.println("Thread name="+Thread.currentThread().getName());
            //System.out.println(plane.getStart().getDeparture(0).getFlightPath().getCompleted());
            //System.out.println(airports.contains(plane.getStart()));

            saveAirports();
            savePlanes();

            try {
                Thread.sleep(1000); // opakuje sa kazdu sekundu
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
