package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.Airport;
import model.Plane;

public class AirportController implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private TableView timeTable;

    @FXML
    private TableColumn arrivals;

    @FXML
    private TableColumn departures;

    private ObservableList<Plane> planeList = FXCollections.observableArrayList();

    
    @Override
    public void initialize() {
        // schova timeTable TableView
        currentScene.setOnMouseClicked(e -> switchScene(currentScene, "Map"));
    }
}
