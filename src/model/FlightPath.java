package model;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FlightPath implements Serializable {
    /*
    private double startX;
    private double startY;
    private double destinationX;
    private double destinationY;
     */

    double[] start;
    double[] destination;

    double averageSpeed;

    private double lenght;
    private double travelled;
    private double completed;

    private String startTime;
    private String arrivalTime;

    public FlightPath(double[] start, double[] destination, double averageSpeed) {
        /*
        this.startX = start[0];
        this.startY = start[1];
        this.destinationX = destination[0];
        this.destinationY = destination[1];
         */
        this.averageSpeed = averageSpeed;

        this.start = start;
        this.destination = destination;

        this.lenght = Math.hypot(destination[0] - start[0], destination[1] - start[1]);
        //System.out.println(lenght);
        this.travelled = 0;
        this.completed = 0;

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("kk:mm");
        this.startTime = LocalTime.now().format(timeFormat);
        System.out.println(this.averageSpeed);
        long time = (long) (this.lenght/this.averageSpeed);
        //System.out.println(time);

        // keby som chcel cas, za aky to bude trvat naozaj, tak staci dat .plusSeconds(time)
        this.arrivalTime = LocalTime.now().plusMinutes(time).format(timeFormat);
    }

    public String getStartTime() {
        return startTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
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

    public void updatePosition(double speed) {
        travelled += speed;
        //System.out.println(travelled);
    }
}
