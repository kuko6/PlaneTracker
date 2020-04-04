package model;

public class Plane {

    private String type;
    private String airline;
    private String id;

    private double speed;
    private double maxSpeed;

    private double altitude;
    private double maxAltitude;

    private Airport start;
    private Airport destinantion;

    public Plane(String type, String airline, String id, Airport start, Airport destinantion) {
        this.type = type;
        this.airline = airline;
        this.id = id;
        this.start = start;
        this.destinantion = destinantion;
    }
    //private FlyPath flyPath;

    public String getType() {
        return type;
    }

    public String getAirline() {
        return airline;
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

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getMaxAltitude() {
        return maxAltitude;
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


}
