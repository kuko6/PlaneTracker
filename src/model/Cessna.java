package model;

public class Cessna extends Plane {

    private String company = "Cessna";

    private int maxRange;

    public Cessna(String type, String airline, String id) {
        super(type, airline, id);
    }

    public Cessna(String type, String airline) {
        super(type, airline);
    }

    public String getCompany() {
        return company;
    }
}
