package model.planes;

import model.FlightPath;

/**
 * Model class Airbus extends abstract class Plane.
 * It overrides methods getManufacturer(), getCruisingSpeed(), getMaxAltitude(), setTimeOfDescend(), takeOf(), ascend() and descend().
 * Unlike its parent class Plane it has final attributes maxAcceleration and maxRateOfClimb.
 *
 * @author Jakub Povinec
 */
public class Airbus extends Plane {

    private final String manufacturer = "Airbus";
    private final double averageSpeed;

    private final int cruisingSpeed;
    private final int maxAcceleration;

    private final int maxRateOfClimb;
    private final int maxAltitude;

    /**
     * Constructor.
     * Sets final attributes.
     *
     * @param type type of the plane
     * @param airline airline of the plane
     * @param id id of the plane
     */
    public Airbus(String type, String airline, String id) {
        super(type, airline, id);
        this.averageSpeed = 430;
        this.cruisingSpeed = 450;
        this.maxAltitude = 41000;
        this.maxRateOfClimb = 1400;
        this.maxAcceleration = (int) (((double) this.maxRateOfClimb/this.maxAltitude) * this.cruisingSpeed);
        //System.out.println(this.maxAcceleration);
    }

    public final String getManufacturer() { return this.manufacturer; }

    public int getCruisingSpeed() { return this.cruisingSpeed; }

    public int getMaxAltitude() { return this.maxAltitude; }

    /**
     * Calculates and sets timeOfDescend.
     */
    public void setTimeOfDescend() {
        timeOfDescend = flightPath.getLength() - flightPath.getTravelled();
        timeOfDescend = timeOfDescend - ((double) cruisingSpeed/speedConst);
        //timeOfDescend = timeOfDescend - maxAcceleration;
    }

    /**
     * Creates new FlightPath, sets its state to flying and starts ascending.
     */
    public void takeoff() {
        System.out.println("Plane id: " + id + " took off from " + start.getName());
        flightPath = new FlightPath(start.getLocation(), destination.getLocation(), averageSpeed/speedConst);
        flying = true;

        this.ascend();
    }

    /**
     * Sets acceleration to maxAcceleration and rateOfClimb to maxRateOfClimb.
     */
    public void ascend() {
        this.acceleration = this.maxAcceleration; // zrychlenie pri stupani
        this.rateOfClimb = this.maxRateOfClimb; // velkost stupania
    }

    /**
     * Sets acceleration to maxAcceleration * -1 and rateOfClimb to maxRateOfClimb * -1.
     */
    public void descend() {
        this.acceleration = this.maxAcceleration * -1; // zrychlenie pri klesani
        this.rateOfClimb = this.maxRateOfClimb * -1; // velkost klesania
    }
}
