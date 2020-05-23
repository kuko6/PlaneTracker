package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.planes.Plane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    private synchronized void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /*
    private synchronized void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
     */

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

    public void updatePlane(Plane plane) {
        //System.out.println(plane.getMaxAltitude() + " " + plane.getCruisingSpeed());

       if (plane.getFlightPath().getTravelled() >= plane.getTimeOfDescend()) {
            plane.descend();
        } else if (plane.getAltitude() >= plane.getMaxAltitude() || plane.getSpeed() >= plane.getCruisingSpeed() || plane.getFlightPath().getCompleted() == 50) {
           if (plane.getAcceleration() > 0) {
               plane.setTimeOfDescend();
               //System.out.println(plane.getFlightPath().getTravelled());
           }
            plane.cruise();
        }

        if (plane.getFlightPath().getCompleted() >= 100 || plane.getSpeed() < 0) {
            plane.land();
        }
        //System.out.println(this.departures.contains(plane));
    }
}
