package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.Airport;
import model.Plane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class AirportInfoController implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private TableView<Plane> arrivalsTable;

    @FXML
    private TableView<Plane> departuresTable;

    @FXML
    private TableColumn<Plane, String> arrivals;

    @FXML
    private TableColumn<Plane, String> departures;

    private ObservableList<Plane> arrivalsList = FXCollections.observableArrayList();
    private ObservableList<Plane> departuresList = FXCollections.observableArrayList();
    private Airport airport;

    public AirportInfoController() {
        loadSelectedAirport();
    }

    private void loadSelectedAirport() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/selectedAirport.txt"));
            airport = (Airport) objectInputStream.readObject();
            objectInputStream.close();
            System.out.println(airport.getName());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        arrivalsList.addAll(airport.getArrivals());
        departuresList.addAll(airport.getDepartures());
        //timeBoard.addAll(airport.getArrivals());
        //timeBoard.addAll(airport.getDepartures());
    }

    private void showTimetable() {
        arrivals.setCellValueFactory(cellData -> cellData.getValue().getArrivalInfo());
        departures.setCellValueFactory(cellData -> cellData.getValue().getDepartureInfo());

        arrivalsTable.setItems(arrivalsList);
        departuresTable.setItems(departuresList);

        /*
        arrivals.setCellValueFactory(cellData -> cellData.getValue().getArrivalInfo());

        timeBoard.removeAll();
        timeBoard.addAll(airport.getDepartures());
        departures.setCellValueFactory(cellData -> cellData.getValue().getDepartureInfo());
        timeTable.setItems(timeBoard);

        if (timeBoard != null) {
            timeTable.setItems(timeBoard);
        } else {
            timeTable.managedProperty().bind(timeTable.visibleProperty());
        }
         */
    }

    @Override
    public void initialize() {
        // schova timeTable TableView
        currentScene.setOnMouseClicked(e -> switchScene(currentScene, "Map"));

        showTimetable();
    }
}
