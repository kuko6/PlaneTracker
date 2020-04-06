package model;

public class Boeing extends Plane {

    private final String company = "Boeing";

    public Boeing(String type, String airline, String id) {
        super(type, airline, id);
    }

    public final String getCompany() {
        return this.company;
    }
}
