package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.Airport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class AirportController implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private TableView<Airport> timeTable;

    @FXML
    private TableColumn<Airport, String> arrivals;

    @FXML
    private TableColumn<Airport, String> departures;

    private ObservableList<Airport> timeBoard = FXCollections.observableArrayList();

    private Airport airport;

    public AirportController() {
        loadCurrentAirport();
        timeBoard.add(this.airport);
    }

    private void loadCurrentAirport() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/currentAirport.txt"));
            this.airport = (Airport) objectInputStream.readObject();
            objectInputStream.close();
            System.out.println(this.airport.getName());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showTimetable() {
        int i = 0, n = this.airport.getArrivals().size();
        while (n != 0 && i < n) {
            int finalI = i;
            arrivals.setCellValueFactory(cellData -> cellData.getValue().getArrival(finalI).getArrivalInfo());
            i++;
        }

        i = 0;
        n = this.airport.getDepartures().size();
        while (n != 0 && i < n) {
            int finalI = i;
            departures.setCellValueFactory(cellData -> cellData.getValue().getDeparture(finalI).getDepartureInfo());
            i++;
        }

        if (timeBoard != null) {
            timeTable.setItems(timeBoard);
        } else {
            timeTable.managedProperty().bind(timeTable.visibleProperty());
        }
    }

    @Override
    public void initialize() {
        // schova timeTable TableView
        currentScene.setOnMouseClicked(e -> switchScene(currentScene, "Map"));

        showTimetable();
    }
}
