package controller;

import controller.abstracts.Controller;
import controller.abstracts.PlaneInfo;
import helper.Storage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Airport;
import model.planes.Plane;

import java.io.*;

/**
 * AirportInfoController is used to display information about the airport that user clicked on the map.
 * Implements interfaces Controller and PlaneInfo.
 *
 * @author Jakub Povinec
 */
public class AirportInfoController implements Controller, PlaneInfo {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private Button add;

    @FXML
    private TableView<Plane> arrivalsTable;

    @FXML
    private TableView<Plane> departuresTable;

    @FXML
    private TableColumn<Plane, String> arrivals;

    @FXML
    private TableColumn<Plane, String> departures;

    @FXML
    private Button back;

    private Airport airport; // zvolene letisko
    private ObservableList<Plane> arrivalsList = FXCollections.observableArrayList();
    private ObservableList<Plane> departuresList = FXCollections.observableArrayList();
    private Node[] nodes;
    private Storage storage; // tu vlastne potrebujem helper len aby som ho poslal dalej do PlanInfoController

    /**
     * This method sets helper class storage.
     *
     * @param storage helper class that holds ArrayLists of classes Airport and Plane
     */
    public void loadHelper(Storage storage) {
        this.storage = storage;
    }

    /**
     * Loads selected airport from MapController, that user clicked on.
     * Also adds all the arrival planes from airport to ObservableList arrivalsList and all the departure planes from airport to ObservableList departuresList
     *
     * @param airport selected airport by the user
     */
    public void loadSelectedAirport(Airport airport) {
        this.airport = airport;

        arrivalsList.addAll(airport.getArrivals());
        departuresList.addAll(airport.getDepartures());
    }

    /**
     * Displays two TableViews arrivals and departures on the screen.
     */
    private void showTimetable() {
        // do tabulky prida vsetky lietadla, ktore ma zvolene letisko v ArrayList arrivals
        arrivals.setCellValueFactory(cellData -> cellData.getValue().getArrivalInfo());
        // do tabulky prida vsetky lietadla, ktore ma zvolene letisko v ArrayList departures
        departures.setCellValueFactory(cellData -> cellData.getValue().getDepartureInfo());

        arrivalsTable.setItems(arrivalsList);
        departuresTable.setItems(departuresList);
    }

    /**
     * Displays dialog window addDialog and switches to AddPlane.
     */
    // zobrazi dialogove okno, v ktorom sa daju pridavat lietadla
    // toto letisko je automaticky aciatocne letisko pre nove lietadlo
    private void showAddDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AddPlane.fxml"));
            AnchorPane pane = loader.load();
            AddPlaneController controller = loader.getController();

            Scene scene = new Scene(pane);
            Stage addDialog = new Stage();
            controller.setDialogStage(addDialog);
            controller.setStartAirport(airport);
            controller.loadHelper(storage);
            addDialog.setTitle("Add Departure");
            addDialog.setScene(scene);
            addDialog.showAndWait(); // okno bude na obrazovke, az pokial ho pouzivatel nezrusi
            //switchScene(currentScene, "Map");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Adds all nodes from this controller to array nodes.
     * Sets add button to display add dialog, back button to switch to Map view.
     * And displays the TableViews arrivalsTable and departuresTable.
     */
    @Override
    public void initialize() {
        nodes = new Node[] {add, back, arrivalsTable, departuresTable};
        add.setOnAction(e -> showAddDialog());
        back.setOnAction(e -> switchToMap(currentScene, storage));
        Platform.runLater(() -> {
            showPlaneInfo(arrivalsTable, currentScene, nodes, storage);
            showPlaneInfo(departuresTable, currentScene, nodes, storage);
            showTimetable();
        });
    }
}
