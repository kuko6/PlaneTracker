package model;

import java.io.Serializable;

public class FlightPath implements Serializable {
    /*
    private double startX;
    private double startY;
    private double destinationX;
    private double destinationY;
     */

    double[] start;
    double[] destination;

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

        this.lenght = Math.hypot(destination[0] - start[0], destination[1] - start[1]);
        this.travelled = 0;
        this.completed = 0;
    }

    public double getLenght() {
        return lenght;
    }

    public double getTravelled() { return travelled; }

    public double getCompleted() {
        completed = travelled/lenght;
        completed *= 100;
        //System.out.println(completed);
        return Math.ceil(completed);
    }

    public void updatePosition(int speed) {
        travelled += speed;
        //System.out.println(travelled);
    }
}
