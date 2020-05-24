package model;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Model class FlightPath is used to keep track of the position of the plane.
 * It calculates distance between start airport and destination airport, calculates how much of the distance has the plane already travelled.
 * It calculates the time the plane needs to get to destination, heading it should go.
 *
 * @author Jakub Povinec
 */
public class FlightPath implements Serializable {
    private double x;
    private double y;
    private double startX;
    private double startY;
    private double destinationX;
    private double destinationY;

    private double heading; // uhol medzi start a destination
    private int headingX; // ak sa musim na x-ovej osi posuvat dolava bude -1 inak 1
    private int headingY; // ak sa musim na y-ovej osi posuvat hore bude -1

    private double length;
    private double travelled;
    private double completed;

    private String startTime;
    private String arrivalTime;

    /**
     * Constructor.
     * Sets x and y of the start and destination and also calculates the distance between start and destination.
     *
     * @param start start airport
     * @param destination destination airport
     */
    public FlightPath(double[] start, double[] destination) {
        this.x = start[0];
        this.y = start[1];
        this.startX = start[0];
        this.startY = start[1];
        this.destinationX = destination[0];
        this.destinationY = destination[1];

        this.length = Math.hypot(destination[0] - start[0], destination[1] - start[1]);
    }

    /**
     * Constructor.
     * Sets x and y of the start and destination and also calculates the distance between start and destination.
     * Calculates heading, arrival time of the plane to the destination.
     *
     * @param start start airport
     * @param destination destination airport
     * @param averageSpeed average speed of the plane, final attribute
     */
    public FlightPath(double[] start, double[] destination, double averageSpeed) {
        this.x = start[0];
        this.y = start[1];
        this.startX = start[0];
        this.startY = start[1];
        this.destinationX = destination[0];
        this.destinationY = destination[1];

        this.length = Math.hypot(destination[0] - start[0], destination[1] - start[1]);
        this.heading = setHeading(startX, destinationX, length);

        this.travelled = 0;
        this.completed = 0;

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("kk:mm");
        this.startTime = LocalTime.now().format(timeFormat);
        long time = (long) (this.length /averageSpeed);

        // keby som chcel cas, za aky to bude trvat naozaj, tak staci dat .plusSeconds(time)
        this.arrivalTime = LocalTime.now().plusMinutes(time).format(timeFormat);
        //this.arrivalTime = LocalTime.now().plusSeconds(time).format(timeFormat); // irl cas
    }

    /**
     * Calculates the heading from the angle between start airport and destination airport.
     *
     * @param startX x of the start airport
     * @param destinationX x of the destination airport
     * @param lenght distance between start airport and destination
     * @return angle in radians between airport start and destination
     */
    private double setHeading(double startX, double destinationX, double lenght) {
        double tmp;
        if (startX < destinationX) { // ked ide doprava
            tmp = (destinationX - startX);
            headingX = 1;
        } else { // ked ide dolava
            tmp = (startX - destinationX);
            headingX = -1;
        }
        if (startY < destinationY) { // ked ide dole
            headingY = 1;
        } else { // ked ide hore
            headingY = -1;
        }

        double angle = Math.acos(tmp/lenght);
        return angle;
    }

    public String getStartTime() { return startTime; }

    public String getArrivalTime() { return arrivalTime; }

    public double getLength() { return length; }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getStartX() { return startX; }

    public double getStartY() { return startY; }

    public double getDestinationX() { return destinationX; }

    public double getDestinationY() { return destinationY; }

    public double getHeading() { return heading; }

    public int getHeadingX() { return headingX; }

    public int getHeadingY() { return headingY; }

    public double getTravelled() { return travelled; }

    /**
     * Calculates how much percent is completed from lenght.
     *
     * @return double how much percent is completed
     */
    public double getCompleted() {
        completed = travelled/length;
        completed *= 100;
        return completed;
    }

    /**
     * Updates plane's coordinates.
     *
     * @param speed speed of the plane
     */
    public void updatePosition(double speed) {
        double tmp = travelled;
        travelled += speed;
        x = x + (Math.cos(heading) * (travelled - tmp)) * headingX;
        y = y + (Math.sin(heading) * (travelled - tmp)) * headingY;
    }
}
