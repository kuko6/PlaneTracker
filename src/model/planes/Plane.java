package model.planes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Airport;
import model.FlightPath;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Plane implements Serializable {

    protected String type;
    protected String airline;
    protected String id;
    protected final String manufacturer = null;

    protected static final int speedConst = 100; // konstanta, ktorou delim rychlost
    private final double averageSpeed = 0;
    private final int cruisingSpeed = 0;
    protected double speed = 0;

    protected int acceleration = 0;

    private final int maxAltitude = 0;
    protected int altitude = 0;
    protected int rateOfClimb = 0;

    protected Airport start;
    protected Airport destination;

    protected FlightPath flightPath;
    protected double timeOfDescend = 100;

    protected boolean flying;

    public Plane(String type, String airline, String id) {
        this.type = type;
        this.airline = airline;
        this.id = id;
    }

    private synchronized void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    public String getType() {
        return type;
    }

    public StringProperty getTypeProperty() {
        return new SimpleStringProperty(type);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getAirline() {
        return airline;
    }

    public StringProperty getArrivalInfo() {
        return new SimpleStringProperty(id + " | " + airline + " | " + flightPath.getArrivalTime() + " | " + start.getName());
    }

    public StringProperty getDepartureInfo() {
        return new SimpleStringProperty(id + " | " + airline + " | " + flightPath.getStartTime() + " | " + destination.getName());
    }

    public String getId() {
        return id;
    }

    public StringProperty getIdProperty() { return new SimpleStringProperty(id); }

    public double getSpeed() {
        return speed;
    }

    public int getCruisingSpeed() { return cruisingSpeed; }

    public int getAcceleration() { return acceleration; }

    public int getAltitude() { return altitude; }

    public int getMaxAltitude() { return maxAltitude; }

    public Airport getStart() { return start; }

    public void setStart(Airport start) {
        this.start = start;
        start.addDeparture(this);
    }

    public Airport getDestination() { return destination; }

    public void setDestination(Airport destination) {
        this.destination = destination;
        destination.addArrival(this);
    }

    public boolean getStatus() { return flying; }

    public String getStartTime() { return flightPath.getStartTime(); }

    public String getArrivalTime() { return flightPath.getArrivalTime(); }

    public FlightPath getFlightPath() { return flightPath; }

    public double getTimeOfDescend() { return timeOfDescend; }

    public void setTimeOfDescend() {
        timeOfDescend = flightPath.getLength() - flightPath.getTravelled();
        System.out.println(id);
        System.out.println("klesam na: " + timeOfDescend);
    }

    public void contactAirport() { start.updatePlane(this); }

    public void fly() {
        flightPath.updatePosition(speed/speedConst);
        speed += acceleration;
        altitude += rateOfClimb;
        contactAirport();
    }

    public void takeoff() {
        System.out.println("Plane id: " + this.id + " took off from " + this.start.getName());
        flightPath = new FlightPath(start.getLocation(), destination.getLocation(), averageSpeed);
        flying = true;
    }

    public void land() {
        System.out.println("Plane id: " + this.id + " is landing in " + this.destination.getName());
        flightPath = null;
        flying = false;
        destination.removeArrival(this);
        start.removeDeparture(this);
    }

    public void setRateOfClimb(int rateOfClimb) { this.rateOfClimb = rateOfClimb; }

    public void setAcceleration(int acceleration) { this.acceleration = acceleration; }

    public void ascend() {
        this.acceleration = 0; // zrychlenie pri stupani
        this.rateOfClimb = 0; // velkost stupania
    }

    public void cruise() {
        this.acceleration = 0;
        this.rateOfClimb = 0;
    }

    public void descend() {
        this.acceleration = 0; // zrychlenie pri klesani
        this.rateOfClimb = 0; // velkost klesania
    }
}
