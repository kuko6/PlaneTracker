package model.planes;

public class Airbus extends Plane {

    private final String manufacturer = "Airbus";

    public Airbus(String type, String airline, String id) {
        super(type, airline, id);
    }

    public final String getManufacturer() {
        return this.manufacturer;
    }

}
