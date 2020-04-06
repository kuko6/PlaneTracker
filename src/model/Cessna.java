package model;

public class Cessna extends Plane {

    private final String company = "Cessna";

    private int maxRange;

    public Cessna(String type, String airline, String id) {
        super(type, airline, id);
    }

    public final String getCompany() {
        return this.company;
    }

}
