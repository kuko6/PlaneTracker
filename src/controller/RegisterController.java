package controller;

import controller.abstracts.Controller;
import controller.abstracts.Serialization;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.exceptions.NoRegisteredUsersException;

import java.util.regex.Pattern;

/**
 * RegisterController controls the addUser dialog window that is displayed when user clicks register button in LoginScreen view.
 * It extends abstract class Serialization and implements interface Controller.
 *
 * @author Jakub Povinec
 */
public class RegisterController extends Serialization implements Controller {

    @FXML
    private AnchorPane currentScene;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button register;

    @FXML
    private Button cancel;

    @FXML
    private Stage dialogStage; // dialogove okno, ktore je na obrazovke

    /**
     * Sets dialog stage.
     *
     * @param stage the register dialogStage
     */
    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    /**
     * Shows error window for the specific problem that occurred while registering new user.
     * Could be wrong username or password.
     * Username is not accepted if there already is an user with the same username or it contains special characters.
     * Password is not accepted if it matches username or contains any special characters.
     *
     * @param error type of error that occurred while registering new user.
     */
    private void showErrorDialog(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect input");
        if (error.equals("Username2")) {
            alert.setContentText("There already is registered account with this username.\nPlease try different username.");
        } else {
            alert.setContentText("You entered incorrect " + error + ". \nPlease try again. \n\nRemeber that both username and password must be unique and can't contain any special characters.");
        }
        alert.showAndWait();

        if (error.equals("Password")) {
            this.password.clear();
            return;
        }
        this.username.clear();
        this.password.clear();
    }

    /**
     * Method checks both username and password if they are correct.
     * Username is not accepted if there already is an user with the same username or it contains special characters.
     * Password is not accepted if it matches username or contains any special characters.
     * If any is incorrect displays errorDialog.
     * If everything was correct adds user to ArrayList users and serializes it.
     */
    private void addUser() {
        String usrnm = username.getText();
        String psswrd = password.getText();
        //System.out.println(usrnm + ", " + psswrd);

        for (User user : users) {
            if (user.getUsername().equals(usrnm)) {
                showErrorDialog("Username2");
                return;
            }
        }

        Pattern p = Pattern.compile("[^a-zA-Z0-9]");

        if (p.matcher(usrnm).find() || usrnm.equals("")) {
            showErrorDialog("Username");
            return;
        }

        if (p.matcher(psswrd).find() || usrnm.equals(psswrd) || psswrd.equals("")) {
            showErrorDialog("Password");
            return;
        }

        users.add(new User(usrnm, psswrd));
        saveUser();
        closeDialog();
    }

    /**
     * Closes this dialogStage.
     * After user clicked either cancel or register and everything was correct.
     */
    // zavrie dialogove okno a vrati sa na hlavnu obrazovku
    private void closeDialog() {
        this.dialogStage.close();
        switchScene(currentScene, "LoginScreen");
    }

    /**
     * Deserializes users ArrayList, sets register and cancel buttons.
     */
    @Override
    public void initialize() {
        try {
            loadUsers();
        } catch (NoRegisteredUsersException e) {
            e.printStackTrace();
        }
        register.setOnAction(e -> addUser());
        cancel.setOnAction(e -> closeDialog());
    }
}
