package model;

public class Boeing extends Plane {

    private final String manufacturer = "Boeing";

    public Boeing(String type, String airline, String id) {
        super(type, airline, id);
    }

    public final String getManufacturer() {
        return this.manufacturer;
    }
}
