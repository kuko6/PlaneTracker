package controller;

import controller.abstracts.Controller;
import helper.Storage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.planes.Plane;
import view.PlaneRenderer;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * PlaneInfoController is used to display the PlaneInfo view.
 * It implements interface Controller.
 * It also starts ScheduledExecutorService that is used to update the PlaneInfo table every second.
 *
 * @author Jakub Povinec
 */
public class PlaneInfoController implements Controller {

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
    private Button back;

    private Plane plane; // zvolene lietadlo
    private ArrayList<Plane> planes;

    private PlaneRenderer planeRenderer = new PlaneRenderer();
    private Storage storage;

    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

    /**
     * Sets plane that user selected as plane.
     *
     * @param plane plane that user selected
     */
    public void loadSelectedPlane(Plane plane) { this.plane = plane; }

    public void loadHelper(Storage storage) { this.storage = storage; }

    /**
     * Starts the ScheduledExecutorService that repeats every second and loads planes from storage refreshes the table from PlaneInfo view.
     */
    private void update() {
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                planes = storage.loadPlanes();

                refresh();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Refreshes the table in PlaneInfo view.
     * First checks if the selected plane is still in the ArrayList planes, otherwise it has already landed.
     * If it landed terminates ScheduledExecutorService and switches back to Map view, otherwise displays the information about the plane.
     */
    private void refresh() {
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
            switchToMap(currentScene, storage);
            exec.shutdownNow();
            return;
        }

        Platform.runLater(() -> {
            showInfoBoard();
            planeRenderer.highlightCompleted(plane.getFlightPath(), currentScene);
        });
    }

    /**
     * Displays the table infoBoard.
     */
    private void showInfoBoard() {
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

    /**
     * Sets up back button to terminate ScheduledExecutorService and switch to Map view.
     * Calls the update method.
     */
    @Override
    public void initialize() {
        back.setOnAction(e -> {
            switchToMap(currentScene, storage);
            exec.shutdownNow();
        });

        update();
    }
}
