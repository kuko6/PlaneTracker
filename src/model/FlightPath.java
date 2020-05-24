package model;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    //private double averageSpeed;

    private double length;
    private double travelled;
    private double completed;

    private String startTime;
    private String arrivalTime;

    public FlightPath(double[] start, double[] destination) {
        this.x = start[0];
        this.y = start[1];
        this.startX = start[0];
        this.startY = start[1];
        this.destinationX = destination[0];
        this.destinationY = destination[1];
        //this.averageSpeed = averageSpeed;

        this.length = Math.hypot(destination[0] - start[0], destination[1] - start[1]);
    }

    public FlightPath(double[] start, double[] destination, double averageSpeed) {
        this.x = start[0];
        this.y = start[1];
        this.startX = start[0];
        this.startY = start[1];
        this.destinationX = destination[0];
        this.destinationY = destination[1];

        //this.averageSpeed = averageSpeed;

        this.length = Math.hypot(destination[0] - start[0], destination[1] - start[1]);
        this.heading = setHeading(startX, destinationX, length);

        this.travelled = 0;
        this.completed = 0;

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("kk:mm");
        this.startTime = LocalTime.now().format(timeFormat);
        //System.out.println(this.averageSpeed);
        long time = (long) (this.length /averageSpeed);

        // keby som chcel cas, za aky to bude trvat naozaj, tak staci dat .plusSeconds(time)
        this.arrivalTime = LocalTime.now().plusMinutes(time).format(timeFormat);
        //this.arrivalTime = LocalTime.now().plusSeconds(time).format(timeFormat);

        /*
        System.out.println("smer: " + heading);
        System.out.println("dlzka " + lenght);
        System.out.println("startx/y: [" + startX + ", " + startY + "]");
        System.out.println("destx/y: [" + destinationX + ", " + destinationY + "]");
        */
    }

    private double setHeading(double startX, double destinationX, double lenght) {
        double tmp;
        if (startX < destinationX) {
            tmp = (destinationX - startX);
            headingX = 1;
        } else {
            tmp = (startX - destinationX);
            headingX = -1;
        }
        if (startY < destinationY) {
            headingY = 1;
        } else {
            headingY = -1;
        }

        double angle = Math.acos(tmp/lenght);
        if (angle < 0) {
            angle += 360;
        }
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

    public double getTravelled() { return travelled; }

    public double getCompleted() {
        completed = travelled/length;
        completed *= 100;
        return completed;
    }

    public void updatePosition(double speed) {
        double tmp = travelled;
        travelled += speed;
        x = x + (Math.cos(heading) * (travelled - tmp)) * headingX;
        y = y + (Math.sin(heading) * (travelled - tmp)) * headingY;
        /*
        System.out.println(travelled);
        System.out.println("x sa zvacsilo o: " + Math.cos(heading) * (travelled - tmp));
        System.out.println("y sa zvacsilo o: " + Math.sin(heading) * (travelled - tmp));
        System.out.println("x/y: [" + x + ", " + y + "]\n");
        System.out.println(speed);
         */
    }
}
