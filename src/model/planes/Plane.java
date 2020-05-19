package model.planes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Airport;
import model.FlightPath;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Plane implements Serializable {

    private String type;
    private String airline;
    private String id;
    private final String manufacturer = null;

    private double speed;
    private double altitude;

    private Airport start;
    private Airport destination;

    private String startTime;
    private String arrivalTime;

    private FlightPath flightPath;

    private boolean flying;

    public Plane(String type, String airline, String id) {
        this.type = type;
        this.airline = airline;
        this.id = id;

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("kk:mm");
        this.startTime = LocalTime.now().format(timeFormat);
        this.arrivalTime = LocalTime.now().plusHours(1).format(timeFormat);
    }
    //private FlyPath flyPath;

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
        return new SimpleStringProperty(id + " | " + airline + " | " + arrivalTime + " | " + start.getName());
    }

    public StringProperty getDepartureInfo() {
        return new SimpleStringProperty(id + " | " + airline + " | " + startTime + " | " + destination.getName());
    }

    public String getId() {
        return id;
    }

    public StringProperty getIdProperty() { return new SimpleStringProperty(id); }

    public boolean getStatus() { return this.flying; }

    public void updateAirport() { this.start.updatePlane(this); }

    public double getSpeed() {
        return speed;
    }

    public void increaseSpeed(double speed) {
        this.speed += speed;
    }

    public void decreaseSpeed(double speed) {
        this.speed -= speed;
    }

    public double getAltitude() {
        return altitude;
    }

    public void ascend(double altitude) {
        this.altitude += altitude;
    }

    public void descend(double altitude) { this.altitude -= altitude; }

    public Airport getStart() {
        return start;
    }

    public void setStart(Airport start) {
        this.start = start;
        start.addDeparture(this);
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
        destination.addArrival(this);
    }

    public String getStartTime() {
        return startTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public FlightPath getFlightPath() {
        return flightPath;
    }

    public void takeoff() {
        System.out.println("Plane id: " + this.id + " took off from " + this.start.getName());
        this.flightPath = new FlightPath(start.getLocation(), destination.getLocation());
        this.flying = true;
    }

    public void land() {
        System.out.println("Plane id: " + this.id + " is landing in " + this.destination.getName());
        this.flightPath = null;
        this.flying = false;
        destination.removeArrival(this);
        start.removeDeparture(this);
    }

}
