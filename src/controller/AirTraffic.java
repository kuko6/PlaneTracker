package controller;

import controller.abstracts.Serialization;
import javafx.scene.layout.AnchorPane;
import model.Airport;
import model.planes.Airbus;
import model.planes.Plane;

import java.util.ArrayList;

public class AirTraffic extends Serialization implements Runnable {

    private int i = 0;
    AnchorPane currentScene;
    ArrayList<Plane> tmpPlanes;

    public AirTraffic(AnchorPane currentScene, ArrayList<Airport> airports, ArrayList<Plane> planes) {
        super();
        System.out.println("zacinam");
        this.airports = airports;
        this.planes = planes;
        this.currentScene = currentScene;
    }

    @Override
    public void run() {
        while (true) {
            loadAirports();
            loadPlanes();
            tmpPlanes = new ArrayList<Plane>(planes); // takto, lebo menim list pocas toho ako ho prechadzam
            for (Plane plane : tmpPlanes) {
                plane.fly();
                plane.contactAirport();
                /*
                if (plane instanceof Airbus) {
                    System.out.println(plane.getId());
                    System.out.println("presiel: " + plane.getFlightPath().getTravelled() + " rychlost: " + plane.getSpeed());
                }
                System.out.println();
                 */
                if (!plane.getStatus()) {
                    planes.remove(plane);
                }
            }
            //System.out.println("Thread name="+Thread.currentThread().getName());
            //System.out.println(plane.getStart().getDeparture(0).getFlightPath().getCompleted());
            //System.out.println(airports.contains(plane.getStart()));

            saveAirports();
            savePlanes();
            if (Main.counter == 0) { // toto teoreticky ani nebude treba
                System.out.println("koncim");
                Thread.currentThread().interrupt();
                break;
            }

            try {
                Thread.sleep(1000); // opakuje sa kazdu sekundu
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            System.out.println(i);
            i++;
            rectangle.setX(rectangle.getX()+1);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
         */
    }
}
