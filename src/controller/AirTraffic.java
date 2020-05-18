package controller;

import controller.abstracts.Serialization;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Airport;
import model.planes.Plane;

public class AirTraffic extends Serialization implements Runnable {

    private int i = 0;
    AnchorPane currentScene;

    public AirTraffic(AnchorPane currentScene) {
        super();
        System.out.println("zacinam");
        this.currentScene = currentScene;
    }

    @Override
    public void run() {
        //Rectangle rectangle = new Rectangle(500, 200, 50, 50);
        //currentScene.getChildren().add(rectangle);
        //Plane plane = planes.get(0);
        Plane plane;
        while (true) {
            loadPlanes();
            loadAirports();
            plane = planes.get(0);
            plane.getFlightPath().updatePosition(10);
            plane.updateAirport();
            System.out.println(plane.getStart().getDeparture(0).getFlightPath().getCompleted());
            saveAirports();
            savePlanes();
            if (Main.counter == 0) { // toto teoreticky ani nebude treba
                System.out.println("koncim");
                break;
            }
            System.out.println(i);
            i++;
            //rectangle.setX(rectangle.getX() + 5);
            try {
                Thread.sleep(2000);
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
