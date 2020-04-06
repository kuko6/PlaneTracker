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

    private ArrayList<Plane> planes = new ArrayList<>();
    private ObservableList<Plane> planeList;

    public PlaneListController() {
        loadCurrentPlanes();
    }

    private void loadCurrentPlanes() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/currentPlanes.txt"));
            planes = (ArrayList<Plane>) objectInputStream.readObject();
            planeList = FXCollections.observableArrayList(planes);
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showCurrentPlanes() {
        if (planes.size() == 0) {
            return;
        }

        for (Plane plane : planes) {
            id.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
            start.setCellValueFactory(cellData -> cellData.getValue().getStart().getNameProperty());
            destination.setCellValueFactory(cellData -> cellData.getValue().getDestinantion().getNameProperty());
        }

        if (planeList != null) {
            planeTable.setItems(planeList);
        } else {
            planeTable.managedProperty().bind(planeTable.visibleProperty());
        }
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

    @Override
    public void initialize() {
        currentScene.setOnMouseClicked(e -> switchScene(currentScene, "Map"));
        planeTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Plane selectedPlane = (Plane) planeTable.getSelectionModel().getSelectedItem();
                //System.out.println(p.getId());

                currentScene.getChildren().remove(planeTable);
                try {
                    saveSelectedPlane(selectedPlane);
                    AnchorPane newScene = FXMLLoader.load(getClass().getResource("../view/PlaneInfo.fxml"));
                    currentScene.getChildren().add(newScene);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        showCurrentPlanes();
    }
}
