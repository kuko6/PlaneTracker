package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Plane implements Serializable {

    private String type;
    private String airline;
    private String id;

    private double speed;
    private double altitude;

    private Airport start;
    private Airport destinantion;

    private String startTime;
    private String arrivalTime;

    /* DateTimeFormatter timeFormatter2 = DateTimeFormatter.ofPattern("kk:mm");
        System.out.println(LocalTime.now().format(timeFormatter2)); */

    public Plane(String type, String airline, String id, Airport start, Airport destinantion) {
        this.type = type;
        this.airline = airline;
        this.id = id;
        this.start = start;
        this.destinantion = destinantion;

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("kk:mm");
        this.startTime = LocalTime.now().format(timeFormat);
    }

    public Plane(String type, String airline) {
        this.type = type;
        this.airline = airline;

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

    public String getAirline() {
        return airline;
    }

    // tu este treba doplnit aj destinaciu
    public StringProperty getArrivalInfo() {
        return new SimpleStringProperty(airline + ", " + arrivalTime);
    }

    public StringProperty getDepartureInfo() {
        return new SimpleStringProperty(airline + ", " + startTime);
    }

    public String getId() {
        return id;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public Airport getStart() {
        return start;
    }

    public void setStart(Airport start) {
        this.start = start;
    }

    public Airport getDestinantion() {
        return destinantion;
    }

    public void setDestinantion(Airport destinantion) {
        this.destinantion = destinantion;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

}
