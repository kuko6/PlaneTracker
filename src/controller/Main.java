package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application {

    // pouziva sa na urcenie v akom stave aplikacie je
    // 1 je ze pouzivatel sa prihlasil
    // 0 je uplny zaciatok aplikacie
    // -1 je ze pouzivatel sa odhlasil
    static int counter = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginScreen.fxml"));
        primaryStage.setTitle("PlaneTracker");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
