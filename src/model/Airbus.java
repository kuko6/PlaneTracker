package model;

public class Airbus extends Plane {

    private String company = "Airbus";

    public Airbus(String type, String airline, String id, Airport start, Airport destinantion) {
        super(type, airline, id, start, destinantion);
    }

    public Airbus(String type, String airline) {
        super(type, airline);
    }

    public String getCompany() {
        return company;
    }
}
