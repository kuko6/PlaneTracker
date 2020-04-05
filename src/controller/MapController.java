package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import model.Airbus;
import model.Airport;
import model.Plane;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MapController implements Controller {

    @FXML
    private AnchorPane currentScene;

    /*
    @FXML
    private Button airport1;
    @FXML
    private Button airport2;
    @FXML
    private Button airport3;
    */

    private ArrayList<Airport> airports = new ArrayList<>();

    private void saveClickedAirport(Airport airport) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/currentAirport.txt"));

            objectOutputStream.writeObject(airport);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize() {
        // nastavi vsetky buttony(letiska) aby robili to iste,
        // mozno by bolo lepsie, aby porovnavali aj id, potom tam mozem mat button na logout .contains("airport")
        for (Node n : currentScene.getChildren()) {
            if (n instanceof Button) {
                Button airport = (Button) n;

                double[] position = {airport.getLayoutX(), airport.getLayoutY()};
                Airport currentAirport = new Airport(airport.getId(), position);
                airports.add(currentAirport);

                airport.setOnAction(e -> {
                    //System.out.println("ja som letisko " + airport.getId() + ", moje suradnice su: x:" + airport.getLayoutX() + ", y:" + airport.getLayoutY());
                    try {
                        saveClickedAirport(currentAirport);
                        AnchorPane newScene = FXMLLoader.load(getClass().getResource("../view/AirportInfo.fxml"));
                        currentScene.getChildren().add(newScene);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
        /*
        airport1.setOnAction((e) -> System.out.println("ja som letisko 1, moje suradnice su: x:" + airport1.getLayoutX() + ", y:" + airport1.getLayoutY());
        airport2.setOnAction((e) -> System.out.println("ja som letisko 2, moje suradnice su x:" + airport2.getLayoutX() + ", y:" + airport2.getLayoutY()));
        airport3.setOnAction((e) -> System.out.println("ja som letisko 3, moje suradnice su x:" + airport3.getLayoutX() + ", y:" + airport3.getLayoutY()));
        */

        this.airports.get(0).addArrival(new Airbus("A321", "Flyyyy"));
        this.airports.get(0).addDeparture(new Airbus("A321", "EasyFly"));
    }
}
