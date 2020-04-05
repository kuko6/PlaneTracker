package model;

public class Cessna extends Plane {

    private String company = "Cessna";

    public Cessna(String type, String airline, String id, Airport start, Airport destinantion) {
        super(type, airline, id, start, destinantion);
    }

    public Cessna(String type, String airline) {
        super(type, airline);
    }

    public String getCompany() {
        return company;
    }
}
