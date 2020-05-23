package controller;

import controller.abstracts.Controller;
import controller.abstracts.Serialization;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import model.planes.Plane;
import view.PlaneRender;

import java.util.Set;
import java.util.concurrent.ExecutorService;
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
    private PlaneRender planeRender = new PlaneRender();
    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

    public void loadSelectedPlane(Plane plane) {
        this.plane = plane;
    }

    private void update() {
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                loadAirports();
                loadPlanes();

                refresh();
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

        Platform.runLater(() -> {
            showInfoBoard();
            planeRender.highlightCompleted(plane.getFlightPath(), currentScene);
        });
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

        completed.setText(String.format("%.2f", plane.getFlightPath().getCompleted()) + "%");
        altitude.setText(plane.getAltitude() + " ft");
        speed.setText(plane.getSpeed() + " kts");
    }

    @Override
    public void initialize() {
        currentScene.setOnMouseClicked(e -> {
            //System.out.println(e.getClickCount());
            switchScene(currentScene, "Map");
            exec.shutdownNow();
        });

        ref.setOnAction(e -> refresh());
        //Platform.runLater(() -> showInfoBoard());
        update();
    }
}
