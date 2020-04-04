package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import model.Airport;
import model.Plane;

import java.io.IOException;
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


    @Override
    public void initialize() {
        // nastavi vsetky buttony(letiska) aby robili to iste,
        // mozno by bolo lepsie, aby porovnavali aj id, potom tam mozem mat button na logout
        for (Node n : currentScene.getChildren()) {
            if (n instanceof Button) {
                Button airport = (Button) n;

                double[] position = {airport.getLayoutX(), airport.getLayoutY()};
                airports.add(new Airport(airport.getId(), position));

                airport.setOnAction(e -> {
                    System.out.println("ja som letisko " + airport.getId() + ", moje suradnice su: x:" + airport.getLayoutX() + ", y:" + airport.getLayoutY());
                    try {
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

        //airports.get(0).addDeparture(new Plane("A321", "Flyy", "13422", airports.get(2), airports.get(1)));
        //airports.get(0).addArrival(new Plane("A321", "Ez", "11111", airports.get(3), airports.get(0)));
    }
}
