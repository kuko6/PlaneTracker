package controller;

import controller.abstracts.Controller;
import controller.helper.Storage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import model.planes.Airbus;
import model.planes.Boeing;
import model.planes.Cessna;
import model.planes.Plane;

import java.util.ArrayList;
import java.util.Objects;

public class AddPlaneController implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private MenuButton manufacturer;

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

    private Stage dialogStage; // dialogove okno, ktore je na obrazovke

    private ArrayList<Airport> airports;
    private ArrayList<Plane> planes;

    private Plane newPlane; // novo vytvorene lietadlo
    private Airport start; // letisko, z ktoreho bolo zavolane dialogove okno, zaciatocne letisko pre nove lietadlo
    private Storage storage;
    private String planeManufacturer;

    public void loadHelper(Storage storage) { this.storage = storage; }

    public void setStartAirport(Airport airport) {
        this.start = airport;
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    // zobrazi na ploche chybove okno
    // okno sa zobrazi po nespravnom vstupe
    private void showErrorDialog(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect input");
        if (error.equals("MaxRange")) {
            alert.setContentText("Destination is out of range for Cessna.\n" + "Please choose different Manufacturer or Destination.");
        } else {
            alert.setContentText("You entered incorrect " + error + ". \nPlease try again.");
        }
        alert.showAndWait();

        if (error.equals("Destination")) {
            this.destination.clear();
        }
    }

    private boolean checkPlaneID(String id) {
        for (Plane plane : planes) {
            if (plane.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkMaxRange(Cessna plane) {
        FlightPath fp = new FlightPath(plane.getStart().getLocation(), plane.getDestination().getLocation());
        if (fp.getLength() > plane.getMaxRange()) {
            return false;
        }
        return true;
    }

    private void addPlane() {
        if (Objects.equals(type.getText(),"") || Objects.equals(airline.getText(),"") || Objects.equals(id.getText(),"")) {
            showErrorDialog("Type, Airline or ID");
            return;
        } else if (checkPlaneID(id.getText())) {
            showErrorDialog("ID");
            return;
        } else if (manufacturer.getText().equals("Manufacturer")) {
            showErrorDialog("Manufacturer");
            return;
        }

        switch (planeManufacturer) {
            case "Airbus":
                this.newPlane = new Airbus(type.getText(), airline.getText(), id.getText());
                break;
            case "Boeing":
                this.newPlane = new Boeing(type.getText(), airline.getText(), id.getText());
                break;
            case "Cessna":
                this.newPlane = new Cessna(type.getText(), airline.getText(), id.getText());
                break;
        }
        
        Airport tmp = null;

        for (Airport airport : airports) {
            if (airport.getName().equalsIgnoreCase(destination.getText())) {
                newPlane.setDestination(airport);
            }
            if (airport.getName().equalsIgnoreCase(start.getName())) {
                tmp = airport;
                //newPlane.setStart(airport); // takto by to zapisal zly objekt
            }
        }

        // pouzivatel zadal nespravnu destinaciu:
        // letisko, ktore neexistuje
        // rovnake letisko ako je start
        if (tmp == null || newPlane.getDestination() == null || newPlane.getDestination().getName().equals(start.getName())) {
            showErrorDialog("Destination");
        } else {
            newPlane.setStart(tmp);

            // Cessna ma ako jedine lietadlo maxRange
            if (newPlane instanceof Cessna) {
                if (!checkMaxRange((Cessna) newPlane)) {
                    showErrorDialog("MaxRange");
                    return;
                }
            }

            newPlane.takeoff();
            planes.add(newPlane);

            storage.saveAirports(airports);
            storage.savePlanes(planes);

            closeDialog();
        }
    }

    // zavrie dialogove okno a vrati sa na hlavnu obrazovku
    private void closeDialog() {
        this.dialogStage.close();
    }

    @Override
    public void initialize() {
        Platform.runLater(() -> {
            airports = storage.loadAirports();
            planes = storage.loadPlanes();
        });

        add.setOnAction(e -> addPlane());
        cancel.setOnAction(e -> closeDialog());
        for (MenuItem choice : manufacturer.getItems()) {
            choice.setOnAction(e -> {
                planeManufacturer = choice.getText();
                manufacturer.setText(planeManufacturer);
            });
        }
    }
}
