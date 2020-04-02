package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;


public class LoginScreenController implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    public void initialize() {
        //login.defaultButtonProperty().bind(login.focusedProperty());
        login.setOnAction((e) -> authenticate());
    }

    private void authenticate() {
        if (Objects.equals(username.getText(), "user") &&(Objects.equals(password.getText(), "1234"))) {
                this.switchScene(currentScene, "Map");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your login was incorrect");
            alert.setContentText("Woops, looks like you used wrong username or password.");
            alert.showAndWait();

            this.password.clear();
        }

       /*System.out.println(username.getText());
        System.out.println(password.getText());
        System.out.println("ouch you clicked me");*/
    }
}
