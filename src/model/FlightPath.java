package model;

import javafx.geometry.Point2D;

public class FlightPath {
    /*
    private double startX;
    private double startY;
    private double destinationX;
    private double destinationY;
     */

    double[] start;
    double[] destination;

    private Point2D location;
    private double lenght;
    private double travelled;
    private double completed;

    public FlightPath(double[] start, double destination[]) {
        /*
        this.startX = start[0];
        this.startY = start[1];
        this.destinationX = destination[0];
        this.destinationY = destination[1];
         */
        this.start = start;
        this.destination = destination;

        this.location = new Point2D(start[0], start[1]);
        this.lenght = location.distance(destination[0], destination[1]);
        this.travelled = 0;
        this.completed = 0;
    }

    public double getLenght() {
        return lenght;
    }

    public double getCompleted() {
        completed = travelled /lenght;
        completed *= 100;
        return completed;
    }

    public void updatePosition(int speed) {
        travelled += speed;
    }
}
