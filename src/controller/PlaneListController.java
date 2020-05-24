package controller;

import controller.abstracts.Controller;
import controller.abstracts.PlaneInfo;
import controller.helper.Storage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.planes.Plane;

import java.util.ArrayList;

public class PlaneListController implements Controller, PlaneInfo {

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

    @FXML
    private Button back;

    private ArrayList<Plane> planes = new ArrayList<>(); // obsahuje vsetky lietadla
    private Storage storage;
    private ObservableList<Plane> planeList;
    private Node[] nodes;

    public void loadHelper(Storage storage) {
        this.storage = storage;
        this.planes = storage.loadPlanes();
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
            destination.setCellValueFactory(cellData -> cellData.getValue().getDestination().getNameProperty());
        }

        planeTable.setItems(planeList);
    }

    @Override
    public void initialize() {
        nodes = new Node[] {back, planeTable};
        back.setOnAction(e -> switchToMap(currentScene, "Map", storage));
        Platform.runLater(() -> { // potrebuje pockat, pokial sa nenacita
            showPlaneInfo(planeTable, currentScene, nodes, storage);
            showCurrentPlanes();
        });
    }
}
