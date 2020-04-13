package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import model.*;

import java.io.*;
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


    private void saveAirports() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/airports.txt"));
            objectOutputStream.writeObject(airports);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAirports() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/airports.txt"));
            this.airports = (ArrayList<Airport>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/currentPlanes.txt"));
            this.planes = (ArrayList<Plane>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void initializeAirports() {
        // nastavi vsetky buttony(letiska)
        int i = 0;
        for (Node n : currentScene.getChildren()) {
            if (n instanceof Button && n.getId().contains("airport")) {
                Button airport = (Button) n;

                if (Main.counter == 0) {
                    double[] position = {(airport.getLayoutX() + airport.getPrefWidth() / 2), (airport.getLayoutY() + airport.getPrefHeight() / 2)};
                    airports.add(new Airport(airport.getId(), position));
                    saveAirports();
                } else {
                    loadAirports();
                }

                int finalI = i;
                airport.setOnAction(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AirportInfo.fxml"));
                        AnchorPane newScene = loader.load();
                        savePlanes();

                        AirportInfoController controller = loader.getController();
                        controller.loadSelectedAirport(airports.get(finalI));
                        currentScene.getChildren().add(newScene);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                i++;
            }
        }
    }

    private void initializePlanes() {
        if (Main.counter == 0) {
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

            savePlanes();
            saveAirports();
        } else {
            loadPlanes();
        }
    }

    static void incrementCounter() {
        Main.counter++;
    }

    private void showPlaneList() {
        try {
            savePlanes();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PlaneList.fxml"));
            AnchorPane newScene = loader.load();

            PlaneListController controller = loader.getController();
            controller.loadCurrentPlanes(planes);
            currentScene.getChildren().add(newScene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize() {
        initializeAirports();
        initializePlanes();

        incrementCounter();

        planeList.setOnAction(e -> showPlaneList());
        logout.setOnAction(e -> {
            this.switchScene(currentScene ,"LoginScreen");
            Main.counter = 0;
        });
    }
}
