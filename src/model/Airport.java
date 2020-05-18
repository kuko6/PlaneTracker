package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.planes.Plane;

import java.io.Serializable;
import java.util.ArrayList;

public class Airport implements Serializable {

    private String name;

    private double[] location;

    private ArrayList<Plane> arrivals = new ArrayList<>();
    private ArrayList<Plane> departures = new ArrayList<>();

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

    public void addArrival(Plane plane) {
        this.arrivals.add(plane);
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
        arrivals.remove(plane);
    }

    public ArrayList<Plane> getDepartures() {
        return departures;
    }

    public void updatePlane(Plane plane) {
        int i = this.departures.indexOf(plane);
        //System.out.println(this.departures.contains(plane));
        System.out.println(this.departures.get(i).getFlightPath().getCompleted());
    }

    public void allowTakeoff(Plane plane) {
        plane.takeoff();
        this.addDeparture(plane);
    }

    public void allowLanding(Plane plane) {
        plane.land();
        this.addArrival(plane);
    }
}
