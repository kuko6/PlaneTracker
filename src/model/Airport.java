package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.planes.Plane;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Model class Airport is serialized in abstract class Serialization so it has to implement Serializable.
 * It's attributes are name, coordinates, ArrayLists of type plane arrivals and departure.
 *
 * @author Jakub Povinec
 */
public class Airport implements Serializable {

    private String name;

    private double[] location;

    private ArrayList<Plane> arrivals = new ArrayList<>();
    private ArrayList<Plane> departures = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param name name of the airport
     * @param location coordinates of the airport
     */
    public Airport(String name, double[] location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public StringProperty getNameProperty() {
        return new SimpleStringProperty(name);
    }

    public double[] getLocation() {
        return location;
    }

    public void addArrival(Plane arrival) {
        this.arrivals.add(arrival);
    }

    public Plane getArrival(int i) { return this.arrivals.get(i); }

    public void removeArrival(Plane landed) {
        arrivals.remove(landed);
    }

    public ArrayList<Plane> getArrivals() {
        return arrivals;
    }

    public void addDeparture(Plane departure) {
        this.departures.add(departure);
    }

    public Plane getDeparture(int i) {
        return this.departures.get(i);
    }

    public void removeDeparture(Plane plane) {
        departures.remove(plane);
    }

    public ArrayList<Plane> getDepartures() {
        return departures;
    }

    /**
     * Method that controls the plane.
     * It checks for plane's altitude and speed compares them to the final attributes of the plane (maxAltitude, cruisingSpeed, timeOfDescend) that every child of Plane has different.
     * And by that comparison commands plane what it should do (descend, cruise, land).
     *
     * @param plane plane that called updatePlane method.
     */
    public void updatePlane(Plane plane) {
       if (plane.getFlightPath().getTravelled() >= plane.getTimeOfDescend()) {
            plane.descend();
        } else if (plane.getAltitude() >= plane.getMaxAltitude() || plane.getSpeed() >= plane.getCruisingSpeed() || plane.getFlightPath().getCompleted() == 50) {
           if (plane.getAcceleration() > 0) { // za rovnaky cas aky stupal bude aj klesat
               plane.setTimeOfDescend();
           }
            plane.cruise();
        }

        if (plane.getFlightPath().getCompleted() >= 100 || plane.getSpeed() < 0) { // to znamena, ze uz presiel letisko
            plane.land();
        }
    }
}
