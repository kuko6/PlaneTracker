package controller;

import controller.abstracts.Controller;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.planes.Plane;

import java.io.*;
import java.util.ArrayList;

public class PlaneListController implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private TableView planeTable;

    @FXML
    private TableColumn<Plane, String> id;

    @FXML
    private TableColumn<Plane, String> start;

    @FXML
    private TableColumn<Plane, String> destination;

    private ArrayList<Plane> planes = new ArrayList<>(); // obsahuje vsetky lietadla
    private ObservableList<Plane> planeList;


    public void loadCurrentPlanes(ArrayList<Plane> currentPlanes) {
        this.planes = currentPlanes;
        planeList = FXCollections.observableArrayList(planes);
    }

    private void showCurrentPlanes() {
        if (planes.size() == 0) {
            return;
        }

        // pre kazde lietadlo zobrazi jeho: id, meno zaciatocneho letiska a meno konecneho letiska
        for (Plane plane : planes) {
            id.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
            start.setCellValueFactory(cellData -> cellData.getValue().getStart().getNameProperty());
            destination.setCellValueFactory(cellData -> cellData.getValue().getDestinantion().getNameProperty());
        }

        planeTable.setItems(planeList);
    }

    // po dvojkliku na lietadlo z tabulky, zobrazi novu tabulku s informaciami o konkretnom lietadle
    private void showPlaneInfo(TableView<Plane> table) {
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Plane selectedPlane = table.getSelectionModel().getSelectedItem(); // vyberie lietadlo z tabulky
                currentScene.getChildren().remove(planeTable); // vymaze zo sceny tabulku s lietadlami
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PlaneInfo.fxml"));
                    AnchorPane newScene = loader.load();

                    PlaneInfoController controller = loader.getController();
                    controller.loadSelectedPlane(selectedPlane);
                    currentScene.getChildren().add(newScene); // zobrazi tabulku s informaciami o lietadle

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initialize() {
        currentScene.setOnMouseClicked(e -> switchScene(currentScene, "Map"));
        Platform.runLater(() -> { // potrebuje pockat, pokial sa nenacita
            showPlaneInfo(planeTable);
            showCurrentPlanes();
        });
    }
}
