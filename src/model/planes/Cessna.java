package model.planes;

import model.FlightPath;

public class Cessna extends Plane {

    private final String manufacturer = "Cessna";
    private final double averageSpeed;

    private final double cruisingSpeed;

    private final int maxAltitude;

    private int maxRange;

    public Cessna(String type, String airline, String id) {
        super(type, airline, id);
        this.averageSpeed = 140;
        this.cruisingSpeed = 196;
        this.maxAltitude = 12000;
    }

    public final String getManufacturer() {
        return this.manufacturer;
    }

    public double getCruisingSpeed() { return this.cruisingSpeed; }

    public int getMaxAltitude() { return this.maxAltitude; }

    public void takeoff() {
        System.out.println("Plane id: " + id + " took off from " + start.getName());
        flightPath = new FlightPath(start.getLocation(), destination.getLocation(), averageSpeed/speedConst);
        flying = true;
    }

    public void startAscend() {
        this.acceleration = 0; // zrychlenie pri stupani
        this.rateOfClimb = 0; // velkost stupania
    }

    public void startCruising() {
        this.acceleration = 0;
        this.rateOfClimb = 0;
    }

    public void startDescend() {
        this.acceleration = 0; // zrychlenie pri klesani
        this.rateOfClimb = 0; // velkost klesania
    }
}
