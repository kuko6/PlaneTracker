package model.planes;

import model.FlightPath;

public class Cessna extends Plane {

    private final String manufacturer = "Cessna";
    private final double averageSpeed;

    private final int cruisingSpeed;
    private final int maxAcceleration;

    private final int maxRateOfClimb;
    private final int maxAltitude;

    private int maxRange;

    public Cessna(String type, String airline, String id) {
        super(type, airline, id);
        this.averageSpeed = 140;
        this.cruisingSpeed = 196;
        this.maxAltitude = 12000;
        this.maxRateOfClimb = 720;
        this.maxAcceleration = (int) (((double) this.maxRateOfClimb/this.maxAltitude) * this.cruisingSpeed);
        //System.out.println(this.maxAcceleration);
    }

    public final String getManufacturer() { return this.manufacturer; }

    public int getCruisingSpeed() { return this.cruisingSpeed; }

    public int getMaxAltitude() { return this.maxAltitude; }

    public void setTimeOfDescend() {
        timeOfDescend = flightPath.getLength() - flightPath.getTravelled();
        timeOfDescend = timeOfDescend - ((double) cruisingSpeed/speedConst);
        //timeOfDescend = timeOfDescend - maxAcceleration;
        System.out.println(id);
        System.out.println("klesam na: " + timeOfDescend);
    }

    public void takeoff() {
        System.out.println("Plane id: " + id + " took off from " + start.getName());
        flightPath = new FlightPath(start.getLocation(), destination.getLocation(), averageSpeed/speedConst);
        flying = true;

        this.ascend();
    }

    public void ascend() {
        this.acceleration = this.maxAcceleration; // zrychlenie pri stupani
        this.rateOfClimb = this.maxRateOfClimb; // velkost stupania
    }

    public void cruise() {
        this.acceleration = 0;
        this.rateOfClimb = 0;
    }

    public void descend() {
        this.acceleration = this.maxAcceleration * -1; // zrychlenie pri klesani
        this.rateOfClimb = this.maxRateOfClimb * -1; // velkost klesania
    }
}
