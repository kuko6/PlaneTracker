package model;

public class Airbus extends Plane {

    private String company = "Airbus";

    public Airbus(String type, String airline, String id) {
        super(type, airline, id);
    }

    public Airbus(String type, String airline) {
        super(type, airline);
    }

    public String getCompany() {
        return company;
    }
}
