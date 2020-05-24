package model.planes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Airport;
import model.FlightPath;

import java.io.Serializable;

/**
 * Abstract class Plane is serialized in class Serialization so it has to implement Serializable.
 * It has some attributes that are final like averageSpeed, cruisingSpeed, maxAltitude, that are used to simulate flight.
 * This simulation is done in class AirTraffic where the Plane method fly() is called every second where its speed and altitude is updated and its start airport is contacted.
 * Its altitude grow by rateOfClimb and its speed grow by acceleration.
 * Every class that extends class Plane has different final attributes.
 * Every Plane has start airport and destination airport, those are used to calculate heading which the plane should go and distance that it has to travel.
 * Distance and heading are both calculated in different class flightPath.
 * Every Plane has exactly one flightPath.
 *
 * @author Jakub Povinec
 */
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

    /**
     * Constructor.
     *
     * @param type type of the plane
     * @param airline airline of the plane
     * @param id id of the plane
     */
    public Plane(String type, String airline, String id) {
        this.type = type;
        this.airline = airline;
        this.id = id;
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

    /**
     * Sets start airport as start from arguments also adds this plane to the departures list to airport start.
     *
     * @param start start airport
     */
    public void setStart(Airport start) {
        this.start = start;
        start.addDeparture(this);
    }

    public Airport getDestination() { return destination; }

    /**
     * Sets destination airport as destination from arguments also adds this plane to the arrivals list to airport destination.
     *
     * @param destination start airport
     */
    public void setDestination(Airport destination) {
        this.destination = destination;
        destination.addArrival(this);
    }

    public boolean getStatus() { return flying; }

    public String getStartTime() { return flightPath.getStartTime(); }

    public String getArrivalTime() { return flightPath.getArrivalTime(); }

    public FlightPath getFlightPath() { return flightPath; }

    public double getTimeOfDescend() { return timeOfDescend; }

    public void setTimeOfDescend() { timeOfDescend = flightPath.getLength() - flightPath.getTravelled(); }

    /**
     * Contacts its start airport with updated position, speed and altitude.
     */
    public void contactAirport() { start.updatePlane(this); }

    /**
     * This method is used to simulate flight of the plane.
     * It updates this planes position in flightPath and adds speed or altitude.
     * It also contacts its start airport.
     */
    public void fly() {
        flightPath.updatePosition(speed/speedConst);
        speed += acceleration;
        altitude += rateOfClimb;
        contactAirport();
    }

    /**
     * Creates new FlightPath and sets its state to flying.
     */
    public void takeoff() {
        System.out.println("Plane id: " + this.id + " took off from " + this.start.getName());
        flightPath = new FlightPath(start.getLocation(), destination.getLocation(), averageSpeed);
        flying = true;
    }

    /**
     * Deletes its flightPath, changes state to not flying and removes itself from destination arrivals list and start departures list.
     */
    public void land() {
        System.out.println("Plane id: " + this.id + " is landing in " + this.destination.getName());
        flightPath = null;
        flying = false;
        destination.removeArrival(this);
        start.removeDeparture(this);
    }

    public void setRateOfClimb(int rateOfClimb) { this.rateOfClimb = rateOfClimb; }

    public void setAcceleration(int acceleration) { this.acceleration = acceleration; }

    /**
     * Sets its acceleration and rateOfClimb to max values.
     * In Plane it's 0.
     */
    public void ascend() {
        this.acceleration = 0; // zrychlenie pri stupani
        this.rateOfClimb = 0; // velkost stupania
    }

    /**
     * Sets its acceleration and rateOfClimb to 0.
     */
    public void cruise() {
        this.acceleration = 0;
        this.rateOfClimb = 0;
    }

    /**
     * Sets its acceleration and rateOfClimb to descend values.
     * In Plane it's 0.
     */
    public void descend() {
        this.acceleration = 0; // zrychlenie pri klesani
        this.rateOfClimb = 0; // velkost klesania
    }
}
