package controller;

import controller.abstracts.Controller;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Airport;
import model.planes.Plane;

import java.io.*;

public class AirportInfoController implements Controller {

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

    private Airport airport; // zvolene letisko
    private ObservableList<Plane> arrivalsList = FXCollections.observableArrayList();
    private ObservableList<Plane> departuresList = FXCollections.observableArrayList();


    public void loadSelectedAirport(Airport airport) {
        this.airport = airport;

        arrivalsList.addAll(airport.getArrivals());
        departuresList.addAll(airport.getDepartures());
    }

    private void showTimetable() {
        // do tabulky prida vsetky lietadla, ktore ma zvolene letisko v ArrayList arrivals
        arrivals.setCellValueFactory(cellData -> cellData.getValue().getArrivalInfo());
        // do tabulky prida vsetky lietadla, ktore ma zvolene letisko v ArrayList departures
        departures.setCellValueFactory(cellData -> cellData.getValue().getDepartureInfo());

        arrivalsTable.setItems(arrivalsList);
        departuresTable.setItems(departuresList);
    }

    // po dvojkliku na lietadlo z tabulky, zobrazi novu tabulku s informaciami o konkretnom lietadle
    private void showPlaneInfo(TableView<Plane> table) {
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Plane selectedPlane = table.getSelectionModel().getSelectedItem();
                currentScene.getChildren().remove(arrivalsTable);
                currentScene.getChildren().remove(departuresTable);
                currentScene.getChildren().remove(add);

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PlaneInfo.fxml"));
                    AnchorPane newScene = loader.load();

                    PlaneInfoController controller = loader.getController();
                    controller.loadSelectedPlane(selectedPlane);
                    currentScene.getChildren().add(newScene);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

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
            addDialog.setTitle("Add Departure");
            addDialog.setScene(scene);
            addDialog.showAndWait(); // okno bude na obrazovke, az pokial ho pouzivatel nezrusi

            switchScene(currentScene, "Map");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize() {
        // schova timeTable TableView
        currentScene.setOnMouseClicked(e -> switchScene(currentScene, "Map"));
        add.setOnAction(e -> showAddDialog());
        Platform.runLater(() -> {
            showPlaneInfo(arrivalsTable);
            showPlaneInfo(departuresTable);
            showTimetable();
        });
    }
}
