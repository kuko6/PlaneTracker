package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.FlightPath;

public class Main extends Application {

    static int counter = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginScreen.fxml"));
        primaryStage.setTitle("PlaneTracker");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        /*
        Label l = new Label();
        final int[] i = {0};
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            int finalI = i[0];
            l.setText(finalI + "");
            i[0]++;
        }));
        timeline.setCycleCount(Animation.INDEFINITE); // loop forever
        timeline.play();

        primaryStage.setScene(new Scene(new StackPane(l), 200, 200));
        primaryStage.show();*/
    }

    public static void main(String[] args) {
        double[] s = {0, 12};
        double[] d = {20, 50};
        FlightPath f = new FlightPath(s, d);
        
        /*
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                f.updatePosition(10);
                // Here comes your void to refresh the whole application.

            }
        }, 2000, 2000);
         */

        launch(args);
    }
}
