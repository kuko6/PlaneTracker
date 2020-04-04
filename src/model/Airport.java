package model;

import java.util.ArrayList;

public class Airport {

    private String name;

    private double[] location;

    private ArrayList<Plane> arrivals;
    private ArrayList<Plane> departures;

    public Airport(String name, double[] location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public double[] getLocation() {
        return location;
    }

    public ArrayList<Plane> getArrivals() {
        return arrivals;
    }

    public ArrayList<Plane> getDepartures() {
        return departures;
    }

    public void addArrival(Plane plane) {
        this.arrivals.add(plane);
    }

    public void removeArrival(Plane landed) {
        arrivals.remove(landed);
    }

    public void addDeparture(Plane departure) {
        this.arrivals.add(departure);
    }

    public void removeDeparture(Plane plane) {
        arrivals.remove(plane);
    }
}
