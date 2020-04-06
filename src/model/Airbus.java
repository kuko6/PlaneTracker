package model;

public class Airbus extends Plane {

    private final String company = "Airbus";

    public Airbus(String type, String airline, String id) {
        super(type, airline, id);
    }

    public final String getCompany() {
        return this.company;
    }

}
