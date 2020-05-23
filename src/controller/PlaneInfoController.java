package controller;

import controller.abstracts.Controller;
import controller.abstracts.Serialization;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import model.planes.Plane;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlaneInfoController extends Serialization implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private GridPane infoTable;

    @FXML
    private Label manufacturer;

    @FXML
    private Label id;

    @FXML
    private Label type;

    @FXML
    private Label airline;

    @FXML
    private Label start;

    @FXML
    private Label startTime;

    @FXML
    private Label destination;

    @FXML
    private Label arrivalTime;

    @FXML
    private Label completed;

    @FXML
    private Label altitude;

    @FXML
    private Label speed;

    @FXML
    private Button ref;

    private Plane plane; // zvolene lietadlo

    public void loadSelectedPlane(Plane plane) {
        this.plane = plane;
    }
    
    private void fn() {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //System.out.println("tu som");

                loadAirports();
                loadPlanes();
                //System.out.println(planes.get(0).getFlightPath().getCompleted());
                refresh();

                currentScene.setOnMouseClicked(e -> {
                    switchScene(currentScene, "Map");
                    exec.shutdownNow();
                });
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void refresh() {
        //loadAirports();
        //loadPlanes();
        //System.out.println(plane.getId());

        boolean landed = true;
        for (Plane p : planes) {
            if (p.getId().equals(this.plane.getId())) {
                this.plane = p;
                landed = false;
                break;
            }
        }

        if (landed) {
            System.out.println("Plane ID: " + plane.getId() + " has landed");
            switchScene(currentScene, "Map");
            return;
        }

        Platform.runLater(this::showInfoBoard);
    }

    private void drawLine() {
        Line line = new Line(plane.getFlightPath().getStartX(), plane.getFlightPath().getStartY(), plane.getFlightPath().getDestinationX(), plane.getFlightPath().getDestinationY());
        line.setOpacity(0.9);
        this.currentScene.getChildren().add(line);
    }

    private void showInfoBoard() {
        //drawLine();
        manufacturer.setText(plane.getManufacturer());
        type.setText(plane.getType());
        id.setText(plane.getId());
        airline.setText(plane.getAirline());
        start.setText(plane.getStart().getName());
        startTime.setText(plane.getStartTime());
        destination.setText(plane.getDestination().getName());
        arrivalTime.setText(plane.getArrivalTime());
        completed.setText(plane.getFlightPath().getCompleted() + "%");
        altitude.setText(plane.getAltitude() + " ft");
        speed.setText(plane.getSpeed() + " kts");
    }

    @Override
    public void initialize() {
        currentScene.setOnMouseClicked(e -> switchScene(currentScene, "Map"));
        ref.setOnAction(e -> refresh());
        //Platform.runLater(() -> showInfoBoard());
        fn();
        //Platform.runLater(() -> fn());
    }
}
