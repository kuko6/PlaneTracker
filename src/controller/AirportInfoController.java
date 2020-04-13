package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.Airport;
import model.Plane;

import java.io.*;

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
            //System.out.println(airport.getName());

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

    private void saveSelectedPlane(Plane plane) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/selectedPlane.txt"));
            objectOutputStream.writeObject(plane);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPlaneInfo(TableView<Plane> table) {
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Plane selectedPlane = (Plane) table.getSelectionModel().getSelectedItem();
                currentScene.getChildren().remove(arrivalsTable);
                currentScene.getChildren().remove(departuresTable);
                try {
                    saveSelectedPlane(selectedPlane);
                    AnchorPane newScene = FXMLLoader.load(getClass().getResource("../view/PlaneInfo.fxml"));
                    currentScene.getChildren().add(newScene);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initialize() {
        // schova timeTable TableView
        currentScene.setOnMouseClicked(e -> switchScene(currentScene, "Map"));

        showPlaneInfo(arrivalsTable);
        showPlaneInfo(departuresTable);

        showTimetable();
    }
}
