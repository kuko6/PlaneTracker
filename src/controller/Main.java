package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.nio.file.Paths;

public class Main extends Application {

    // pouziva sa na urcenie v akom stave aplikacie je
    // 1 je ze pouzivatel sa prihlasil
    // 0 je uplny zaciatok aplikacie
    // -1 je ze pouzivatel sa odhlasil
    static int counter = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginScreen.fxml"));

        String url = System.getProperty("user.dir") + "/src/content/";
        com.apple.eawt.Application.getApplication().setDockIconImage(new ImageIcon(Paths.get(url + "icon.png").toUri().toURL()).getImage());
        primaryStage.getIcons().add(new Image(Paths.get(url + "icon.png").toUri().toURL().toString()));

        primaryStage.setTitle("PlaneTracker");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
