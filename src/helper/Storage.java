package helper;

import model.Airport;
import model.planes.Plane;

import java.util.ArrayList;

public class Storage {
    private ArrayList<Airport> airports = new ArrayList<>();
    private ArrayList<Plane> planes = new ArrayList<>();

    public synchronized ArrayList<Airport> loadAirports() {
        return airports;
    }

    public synchronized ArrayList<Plane> loadPlanes() {
        return planes;
    }

    public synchronized void saveAirports(ArrayList<Airport> airports) {
        this.airports = airports;
    }

    public synchronized void savePlanes(ArrayList<Plane> planes) {
        this.planes = planes;
    }
}
