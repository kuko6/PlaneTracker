package controller;

import controller.abstracts.Controller;
import controller.abstracts.Serialization;
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
import view.PlaneRender;

import java.io.*;
import java.util.ArrayList;

public class MapController extends Serialization implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private Button logout;

    @FXML
    private Button planeList;

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
                    loadAirports();
                }

                int finalI = i;
                airport.setOnAction(e -> showAirportInfo(finalI));
                i++;
            }
        }
    }

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

            plane4.setStart(airports.get(3));
            plane4.setDestination(airports.get(6));

            for (Plane p : planes) {
                p.takeoff();
            }
            savePlanes();
            // znova sa ulozia aj letiska
            // lebo plane.setStart/Destination zmenii ArrayList lietadiel, ktore z letiska odchadzaju alebo prichadzaju
            saveAirports();
        } else {
            // ked uz boli vytvorene, tak sa len nacitaju
            loadPlanes();
        }
    }

    static void incrementCounter() {
        Main.counter++;
    }

    // zobrazi dve tabulky s lietadlami, ktore z letiska odisli alebo do neho prichadzaju
    private void showAirportInfo(int i) {
        try {
            //loadPlanes();
            loadAirports(); // to aby sa updatli potom, ked sa lietadla v AirTraffic zmenili
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AirportInfo.fxml"));
            AnchorPane newScene = loader.load(); // nacita novu scenu z /view/...

            AirportInfoController controller = loader.getController(); // ziska controller nacitanej sceny
            controller.loadSelectedAirport(airports.get(i)); // "posle" do controlleru letisko, o ktorom sa maju zobrazit informacie
            currentScene.getChildren().add(newScene); // prida na obrazovku tabulky

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // zobrazi tabulku so vsetkymi lietadlami
    private void showPlaneList() {
        try {
            loadPlanes(); // to aby sa updatli
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PlaneList.fxml"));
            AnchorPane newScene = loader.load(); // nacita novu scenu z /view/...

            PlaneListController controller = loader.getController(); // ziska controller nacitanej sceny
            controller.loadCurrentPlanes(planes); // "posle" do controlleru lietadla, ktore sa zobrazia v tabulke
            currentScene.getChildren().add(newScene); // prida na obrazovku tabulku s lietadlami

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize() {
        initializeAirports();
        initializePlanes();

        //System.out.println("|tu|");

        if (Main.counter == 0) {
            Thread airTrafic = new Thread(new AirTraffic(currentScene, airports, planes));
            airTrafic.setDaemon(true);
            airTrafic.start();
        }

        //System.out.println(planes.get(0).getStart() + " " + planes.get(2).getStart());
        //System.out.println(planes.get(0).getStart().getDeparture(0) + " " + planes.get(0)); // funguje to pog
        //System.out.println(planes.get(0).getStart().getDeparture(1) + " " + planes.get(2)); // aj toto funguje pog
        //System.out.println(planes.get(0).getStart() + " " + airports.get(0));
        //System.out.println(planes.get(0).equals(airports.get(0).getDeparture(0)));

        incrementCounter();

        planeList.setOnAction(e -> showPlaneList());
        logout.setOnAction(e -> {
            System.out.println("Logged out\n");
            switchScene(currentScene ,"LoginScreen");
            Main.counter = 0;
        });
    }
}
