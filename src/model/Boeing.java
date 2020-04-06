package model;

public class Boeing extends Plane {

    private String company = "Boeing";

    public Boeing(String type, String airline, String id) {
        super(type, airline, id);
    }

    public Boeing(String type, String airline) {
        super(type, airline);
    }

    public String getCompany() {
        return company;
    }
}
