package controller;

import controller.abstracts.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegisterController implements Controller {

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

    private ArrayList<User> users = new ArrayList<>();

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void loadUsers() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/users.txt";
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(p));
            users = (ArrayList<User>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveUser() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/users.txt";
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(p));
            objectOutputStream.writeObject(users);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        } else if (error.contains("Username")) {
            this.username.clear();
        }
    }

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

    // zavrie dialogove okno a vrati sa na hlavnu obrazovku
    private void closeDialog() {
        this.dialogStage.close();
        switchScene(currentScene, "LoginScreen");
    }

    @Override
    public void initialize() {
        loadUsers();
        register.setOnAction(e -> addUser());
        cancel.setOnAction(e -> closeDialog());
    }
}
