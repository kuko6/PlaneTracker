package controller;

import controller.abstracts.Controller;
import controller.abstracts.Serialization;
import helper.Storage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import model.*;
import model.planes.Airbus;
import model.planes.Boeing;
import model.planes.Cessna;
import model.planes.Plane;

import java.io.*;
import java.util.ArrayList;

/**
 * MapController is used to control main view of this app the Map view.
 * Every other controller leads back to MapController.
 * It extends abstract class Serialization and implements interface Controller.
 * It's used to initialize airports which are represented as Buttons and initialize planes at the start of the app.
 * It also starts Thread AirTraffic that updates the planes and displays them on the screen.
 *
 * @author Jakub Povinec
 */
public class MapController extends Serialization implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private Button logout;

    @FXML
    private Button planeList;

    private Storage storage;

    private ArrayList<Airport> airports = new ArrayList<>();
    private ArrayList<Plane> planes = new ArrayList<>();

    /**
     * This method sets helper class storage.
     *
     * @param storage helper class that holds ArrayLists of classes Airport and Plane
     */
    public void loadHelper(Storage storage) {
        this.storage = storage;
    }

    /**
     * Initialize airports.
     * If it's the start of the app, stores them in storage otherwise loads them from storage and sets them to display AirportInfo.
     */
    // nastavi vsetky buttony(letiska)
    private synchronized void initializeAirports() {
        int i = 0;
        // prechadza vsetky deti sceny a hlada buttony, ktore reprezentuju letiska
        for (Node n : currentScene.getChildren()) {
            if (n instanceof Button && n.getId().contains("airport")) {
                Button airport = (Button) n;

                if (Main.counter == 0) { // ked je zaciatok, teda este neboli vytvorene objekty letiska
                    double[] position = {(airport.getLayoutX() + airport.getPrefWidth()/2), (airport.getLayoutY() + airport.getPrefHeight()/2)};
                    airports.add(new Airport(airport.getId(), position));
                    //saveAirports();
                } else {
                    airports = storage.loadAirports();
                }

                int finalI = i;
                airport.setOnAction(e -> showAirportInfo(finalI));
                i++;
            }
        }
    }

    /**
     * Initialize planes.
     * If it's the start of the app, stores them in storage otherwise loads them from storage.
     */
    private synchronized void initializePlanes() {
        // na zaciatku programu sa vytvori par lietadiel a ulozia sa do ArrayList lietadla
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
            plane1.setDestination(airports.get(2));

            plane2.setStart(airports.get(3));
            plane2.setDestination(airports.get(0));

            plane3.setStart(airports.get(0));
            plane3.setDestination(airports.get(1));

            plane4.setStart(airports.get(6));
            plane4.setDestination(airports.get(3));

            for (Plane p : planes) {
                p.takeoff();
            }
            storage.savePlanes(planes);
            // znova sa ulozia aj letiska
            // lebo plane.setStart/Destination zmeni ArrayList lietadiel, ktore z letiska odchadzaju alebo prichadzaju
            storage.saveAirports(airports);
        } else {
            // ked uz boli vytvorene, tak sa len nacitaju
            planes = storage.loadPlanes();
        }
    }

    /**
     * Shows AirportInfo view after user clicked on an airport and sets storage in AirportInfoController class.
     *
     * @param i index of selected airport in ArrayList airports
     */
    // zobrazi dve tabulky s lietadlami, ktore z letiska odisli alebo do neho prichadzaju
    private void showAirportInfo(int i) {
        try {
            airports = storage.loadAirports(); // to aby sa updatli potom, ako sa lietadla v thread AirTraffic zmenili
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AirportInfo.fxml"));
            AnchorPane newScene = loader.load(); // nacita novu scenu z /view/...

            AirportInfoController controller = loader.getController(); // ziska controller nacitanej sceny
            controller.loadSelectedAirport(airports.get(i)); // "posle" do controlleru letisko, o ktorom sa maju zobrazit informacie
            controller.loadHelper(storage);
            currentScene.getChildren().add(newScene); // prida na obrazovku tabulky

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Displays PlaneList view after user clicked on planes Button and sets storage in PlaneListController class.
     */
    // zobrazi tabulku so vsetkymi lietadlami
    private void showPlaneList() {
        try {
            planes = storage.loadPlanes(); // to aby sa updatli potom, ako sa lietadla v thread AirTraffic zmenili
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PlaneList.fxml"));
            AnchorPane newScene = loader.load(); // nacita novu scenu z /view/...

            PlaneListController controller = loader.getController(); // ziska controller
            controller.loadHelper(storage);
            currentScene.getChildren().add(newScene); // prida na obrazovku tabulku s lietadlami

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initializes airports and planes.
     * Starts thread AirTraffic if it's the start of the program or another user logged in.
     * Sets up planeList and logout buttons.
     * If user clicked on logout button also serializes airports and planes and switches to LoginScreen.
     */
    @Override
    public void initialize() {
        Platform.runLater(() -> {
            initializeAirports(); //nacitaju sa letiska
            initializePlanes(); // nacitaju sa lietadla

            // 0 je uplny zaciatok, -1 je logout
            if (Main.counter == 0 || Main.counter == -1) {
                Thread airTraffic = new Thread(new AirTraffic(currentScene, airports, planes, storage));
                airTraffic.setDaemon(true);
                airTraffic.start();
                Main.counter = 1;
            }
        });

        planeList.setOnAction(e -> showPlaneList());
        logout.setOnAction(e -> {
            System.out.println("Logged out\n");

            // ulozia sa lietadla
            serializedAirports = storage.loadAirports();
            serializedPlanes = storage.loadPlanes();
            saveAirports();
            savePlanes();

            switchScene(currentScene ,"LoginScreen");
            Main.counter = -1;
        });
    }
}
