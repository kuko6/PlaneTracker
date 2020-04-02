package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MapController implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    public Button airport1;

    @FXML
    public Button airport2;

    @FXML
    public Button airport3;

    @Override
    public void initialize() {
        airport1.setOnAction((e) -> System.out.println("ja som letisko 1, moje suradnice su: x:" + airport1.getLayoutX() + ", y:" + airport1.getLayoutY()));
        airport2.setOnAction((e) -> System.out.println("ja som letisko 2, moje suradnice su x:" + airport2.getLayoutX() + ", y:" + airport2.getLayoutY()));
        airport3.setOnAction((e) -> System.out.println("ja som letisko 3, moje suradnice su x:" + airport3.getLayoutX() + ", y:" + airport3.getLayoutY()));
    }
}
