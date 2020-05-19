package model.planes;

import model.FlightPath;

public class Airbus extends Plane {

    private final String manufacturer = "Airbus";
    private final double averageSpeed;

    private final double cruisingSpeed;

    private final int maxAltitude;

    public Airbus(String type, String airline, String id) {
        super(type, airline, id);
        this.averageSpeed = 430;
        this.cruisingSpeed = 457;
        this.maxAltitude = 41000;
    }

    public final String getManufacturer() { return this.manufacturer; }

    public double getCruisingSpeed() { return this.cruisingSpeed; }

    public int getMaxAltitude() { return this.maxAltitude; }

    public void takeoff() {
        System.out.println("Plane id: " + id + " took off from " + start.getName());
        flightPath = new FlightPath(start.getLocation(), destination.getLocation(), averageSpeed/speedConst);
        flying = true;

        acceleration = 175; // toto je zrychlenie pri starte
        rateOfClimb = 3500; // toto je stupanie pri starte
    }

    public void startAscend() {
        this.acceleration = 120; // zrychlenie pri stupani
        this.rateOfClimb = 1400; // velkost stupania
    }

    public void startCruising() {
        this.acceleration = 0;
        this.rateOfClimb = 0;
    }

    public void startDescend() {
        this.acceleration = -120; // zrychlenie pri klesani
        this.rateOfClimb = -1400; // velkost klesania
    }
}
