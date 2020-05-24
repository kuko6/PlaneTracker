package helper;

import model.Airport;
import model.planes.Plane;

import java.util.ArrayList;

/**
 * Storage is used as helper class to store ArrayLists of type Airport and Plane and then move around controllers.
 *
 * @author Jakub Povinec
 */
public class Storage {
    private ArrayList<Airport> airports = new ArrayList<>();
    private ArrayList<Plane> planes = new ArrayList<>();

    /**
     * Getter method that returns ArrayList airports.
     *
     * @return ArrayList of type Airport
     */
    public synchronized ArrayList<Airport> loadAirports() {
        return airports;
    }

    /**
     * Getter method that returns ArrayList planes.
     *
     * @return ArrayList of type Plane
     */
    public synchronized ArrayList<Plane> loadPlanes() {
        return planes;
    }

    /**
     * Setter method that sets this ArrayList airports to ArrayList airports from argument.
     *
     * @param airports ArrayList of type Airport
     */
    public synchronized void saveAirports(ArrayList<Airport> airports) {
        this.airports = airports;
    }

    /**
     * Setter method that sets this ArrayList planes to ArrayList plane from argument.
     *
     * @param planes ArrayList of type Plane
     */
    public synchronized void savePlanes(ArrayList<Plane> planes) {
        this.planes = planes;
    }
}
