package model.planes;

public class Cessna extends Plane {

    private final String manufacturer = "Cessna";

    private int maxRange;

    public Cessna(String type, String airline, String id) {
        super(type, airline, id);
    }

    public final String getManufacturer() {
        return this.manufacturer;
    }

}
