package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import model.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MapController implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private Button logout;

    @FXML
    private Button planeList;

    private ArrayList<Airport> airports = new ArrayList<>();
    private ArrayList<Plane> planes = new ArrayList<>();

    private void saveSelectedAirport(Airport airport) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/selectedAirport.txt"));
            objectOutputStream.writeObject(airport);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAirports() {
        // nastavi vsetky buttony(letiska)
        for (Node n : currentScene.getChildren()) {
            if (n instanceof Button && n.getId().contains("airport")) {
                Button airport = (Button) n;

                double[] position = {(airport.getLayoutX() + airport.getPrefWidth()/2), (airport.getLayoutY() + airport.getPrefHeight()/2)};
                Airport currentAirport = new Airport(airport.getId(), position);
                airports.add(currentAirport);

                airport.setOnAction(e -> {
                    try {
                        saveSelectedAirport(currentAirport);
                        AnchorPane newScene = FXMLLoader.load(getClass().getResource("../view/AirportInfo.fxml"));
                        currentScene.getChildren().add(newScene);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
    }

    private void savePlanes() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/currentPlanes.txt"));
            objectOutputStream.writeObject(planes);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPlanes() {
        Plane plane1 = new Airbus("A321", "Flyyyy", "1234");
        Plane plane2 = new Airbus("A321", "EasyFly", "2222");
        Plane plane3 = new Cessna("150", "Pff", "3242");
        Plane plane4 = new Boeing("787", "EasyFly", "1323");

        planes.add(plane1);
        planes.add(plane2);
        planes.add(plane3);
        planes.add(plane4);

        plane1.setStart(airports.get(0));
        plane1.setDestinantion(airports.get(2));

        plane2.setStart(airports.get(3));
        plane2.setDestinantion(airports.get(0));

        plane3.setStart(airports.get(0));
        plane3.setDestinantion(airports.get(1));

        plane4.setStart(airports.get(3));
        plane4.setDestinantion(airports.get(6));

        for (Plane p : planes) {
            p.takeoff();
        }

        /*
        double[] startLocation = plane1.getStart().getLocation();
        double[] destinationLocation = plane1.getDestinantion().getLocation();

        Line path = new Line(startLocation[0], startLocation[1], destinationLocation[0], destinationLocation[1]);
        currentScene.getChildren().add(path);
         */
    }


    @Override
    public void initialize() {
        loadAirports();
        loadPlanes();

        logout.setOnAction(e -> this.switchScene(currentScene ,"LoginScreen"));
        planeList.setOnAction(e -> {
            try {
                savePlanes();
                AnchorPane newScene = FXMLLoader.load(getClass().getResource("../view/PlaneList.fxml"));
                currentScene.getChildren().add(newScene);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
