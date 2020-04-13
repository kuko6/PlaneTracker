package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Plane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PlaneInfoController implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private GridPane infoTable;

    @FXML
    private Label company;

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

    private Plane plane;

    public PlaneInfoController() {
        loadSelectedPlane();
    }

    private void loadSelectedPlane() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/selectedPlane.txt"));
            plane = (Plane) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showInfoBoard() {
        company.setText(plane.getCompany());
        type.setText(plane.getType());
        id.setText(plane.getId());
        airline.setText(plane.getAirline());
        start.setText(plane.getStart().getName());
        startTime.setText(plane.getStartTime());
        destination.setText(plane.getDestinantion().getName());
        arrivalTime.setText(plane.getArrivalTime());
        completed.setText(plane.getFlightPath().getCompleted() + "%");
        altitude.setText(plane.getAltitude() + " ft");
        speed.setText(plane.getSpeed() + " kts");

    }

    @Override
    public void initialize() {
        currentScene.setOnMouseClicked(e -> switchScene(currentScene, "Map"));

        showInfoBoard();
    }
}
