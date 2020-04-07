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

    private void initializeAirports() {
        // nastavi vsetky buttony(letiska)
        int i = 0;
        for (Node n : currentScene.getChildren()) {
            if (n instanceof Button && n.getId().contains("airport")) {
                Button airport = (Button) n;

                if (Main.counter == 0) {
                    double[] position = {(airport.getLayoutX() + airport.getPrefWidth()/2), (airport.getLayoutY() + airport.getPrefHeight()/2)};
                    airports.add(new Airport(airport.getId(), position));
                    saveAirports();
                } else {
                    loadAirports();
                }

                int finalI = i;
                airport.setOnAction(e -> {
                    try {
                        saveSelectedAirport(airports.get(finalI));
                        AnchorPane newScene = FXMLLoader.load(getClass().getResource("../view/AirportInfo.fxml"));
                        currentScene.getChildren().add(newScene);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                i++;
            }
        }
    }

    private void loadAirports() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/airports.txt"));
            airports = (ArrayList<Airport>) objectInputStream.readObject();
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

        planes.get(0).getFlightPath().updatePosition(100);

        /*
        double[] startLocation = plane1.getStart().getLocation();
        double[] destinationLocation = plane1.getDestinantion().getLocation();

        Line path = new Line(startLocation[0], startLocation[1], destinationLocation[0], destinationLocation[1]);
        currentScene.getChildren().add(path);
         */
    }

    private void loadPlanes() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/currentPlanes.txt"));
            planes = (ArrayList<Plane>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    static void incrementCounter() {
        Main.counter++;
    }

    @Override
    public void initialize() {
        initializeAirports();
        initializePlanes();

        incrementCounter();

        logout.setOnAction(e -> {
            this.switchScene(currentScene ,"LoginScreen");
            Main.counter = 0;
        });
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
