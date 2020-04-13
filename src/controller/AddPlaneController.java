package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class AddPlaneController implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private TextField manufacturer;

    @FXML
    private TextField type;

    @FXML
    private TextField id;

    @FXML
    private TextField airline;

    @FXML
    private TextField destination;

    @FXML
    private Button add;

    @FXML
    private Button cancel;

    private Stage dialogStage;

    private ArrayList<Airport> airports;
    private ArrayList<Plane> planes;

    private Plane newPlane;
    private Airport start;


    public void setStartAirport(Airport airport) {
        this.start = airport;
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    private void saveAirports() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/airports.txt"));
            objectOutputStream.writeObject(airports);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAirports() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/airports.txt"));
            airports = (ArrayList<Airport>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void savePlanes() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/currentPlanes.txt"));
            objectOutputStream.writeObject(planes);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPlanes() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/currentPlanes.txt"));
            planes = (ArrayList<Plane>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addPlane() {
        if (Objects.equals(manufacturer.getText(), "Airbus")) {
            this.newPlane = new Airbus(type.getText(), airline.getText(), id.getText());
        } else if (Objects.equals(manufacturer.getText(), "Boeing")) {
            this.newPlane = new Boeing(type.getText(), airline.getText(), id.getText());
        } else if (Objects.equals(manufacturer.getText(), "Cessna")) {
            this.newPlane = new Cessna(type.getText(), airline.getText(), id.getText());
        }

        for (Airport airport : airports) {
            if (airport.getName().equalsIgnoreCase(destination.getText())) {
                newPlane.setDestinantion(airport);
            }
            if (airport.getName().equals(start.getName())) {
                newPlane.setStart(airport);
            }
        }
        newPlane.takeoff();
        planes.add(newPlane);

        saveAirports();
        savePlanes();

        closeDialog();
    }

    private void closeDialog() {
        this.dialogStage.close();
        switchScene(currentScene, "Map");
    }

    @Override
    public void initialize() {
        loadAirports();
        loadPlanes();

        add.setOnAction(e -> addPlane());
        cancel.setOnAction(e -> closeDialog());
    }

}
