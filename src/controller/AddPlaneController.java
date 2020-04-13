package controller;

import controller.abstracts.Controller;
import controller.abstracts.Serialization;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import model.planes.Airbus;
import model.planes.Boeing;
import model.planes.Cessna;
import model.planes.Plane;

import java.util.Objects;

public class AddPlaneController extends Serialization implements Controller {

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

    private Stage dialogStage; // dialogove okno, ktore je na obrazovke

    private Plane newPlane; // novo vytvorene lietadlo
    private Airport start; // letisko, z ktoreho bolo zavolane dialogove okno, zaciatocne letisko pre nove lietadlo

    public AddPlaneController() {
        super();
    }

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
        alert.setContentText("You entered incorrect " + error + ". \nPlease try again.");
        alert.showAndWait();

        if (Objects.equals(error, "Manufacturer")) {
            this.manufacturer.clear();
        } else {
            this.destination.clear();
        }
    }

    private void addPlane() {
        if (Objects.equals(type.getText(),"") || Objects.equals(airline.getText(),"") || Objects.equals(id.getText(),"")) {
            showErrorDialog("Type, Airline or ID");
            return;
        } else if (Objects.equals(manufacturer.getText(), "Airbus")) {
            this.newPlane = new Airbus(type.getText(), airline.getText(), id.getText());
        } else if (Objects.equals(manufacturer.getText(), "Boeing")) {
            this.newPlane = new Boeing(type.getText(), airline.getText(), id.getText());
        } else if (Objects.equals(manufacturer.getText(), "Cessna")) {
            this.newPlane = new Cessna(type.getText(), airline.getText(), id.getText());
        } else {
            // pouzivatel zadal nespravneho vyrobcu
            showErrorDialog("Manufacturer");
            return;
        }
        
        Airport tmp = null;

        for (Airport airport : airports) {
            if (airport.getName().equalsIgnoreCase(destination.getText())) {
                newPlane.setDestinantion(airport);
            }
            if (airport.getName().equals(start.getName())) {
                tmp = airport;
                //newPlane.setStart(airport);
            }
        }

        if (newPlane.getDestinantion() == null) { // pouzivatel zadal nespravnu destinaciu
            showErrorDialog("Destination");
        } else {
            newPlane.setStart(tmp);
            newPlane.takeoff();
            planes.add(newPlane);
            System.out.println(newPlane.getDestinantion());
            saveAirports();
            savePlanes();

            closeDialog();
        }
    }

    // zavrie dialogove okno a vrati sa na hlavnu obrazovku
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
