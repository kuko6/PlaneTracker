package model.exceptions;

import javafx.scene.control.Alert;

/**
 * Custom exception that is thrown when user clicks on blank cell in TableView and displays warning dialog window.
 * Extends Exception.
 *
 * @author Jakub Povinec
 */
public class BlankTableException extends Exception {
    /**
     * Constructor.
     */
    public BlankTableException() {
        this.showAlert();
    }

    /**
     * Displays warning dialog window.
     */
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Blank table cell");
        alert.setContentText("You clicked on a blank table cell.");

        alert.showAndWait();
    }
}
