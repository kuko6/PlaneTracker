package model.exceptions;

import javafx.scene.control.Alert;

public class BlankTableException extends Exception {
    public BlankTableException() {
        this.showAlert();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Blank table cell");
        alert.setContentText("You clicked on a blank table cell.");

        alert.showAndWait();
    }
}
