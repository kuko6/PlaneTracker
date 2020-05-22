package controller;

import controller.abstracts.Controller;
import controller.abstracts.Serialization;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class LoginScreenController extends Serialization implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private Button login;

    @FXML
    private Button register;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private void showErrorDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Your login was incorrect");
        alert.setContentText("Woops, looks like you used wrong username or password.");
        alert.showAndWait();

        this.password.clear();
    }

    private void authenticate() {
        for (User user : users) {
            if (username.getText().equals(user.getUsername())) {
                if (password.getText().equals(user.getPassword())) {
                    System.out.println("Logged in as " + user.getUsername() + "\n");
                    this.switchScene(currentScene, "Map");
                    return;
                }
            }
        }

        showErrorDialog();

        /*if (Objects.equals(username.getText(), "user") && (Objects.equals(password.getText(), "1234"))) {
            this.switchScene(currentScene, "Map");
        } else {
            showErrorDialog();
        }
         */
    }

    private void showRegisterDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AddUser.fxml"));
            AnchorPane pane = loader.load();
            RegisterController controller = loader.getController();

            Scene scene = new Scene(pane);
            Stage addDialog = new Stage();
            controller.setDialogStage(addDialog);
            addDialog.setTitle("Register");
            addDialog.setScene(scene);
            addDialog.showAndWait(); // okno bude na obrazovke, az pokial ho pouzivatel nezrusi

            switchScene(currentScene, "LoginScreen");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        //login.defaultButtonProperty().bind(login.focusedProperty());
        loadUsers();
        System.out.println("Registered users: ");
        for (User user : users) {
            System.out.println(user.getUsername());
        }
        System.out.println();

        login.setOnAction((e) -> authenticate());
        register.setOnAction((e) -> showRegisterDialog());
    }
}
