package controller.abstracts;

import model.Airport;
import model.User;
import model.planes.Plane;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

public abstract class Serialization {

    public ArrayList<Airport> airports = new ArrayList<>();
    public ArrayList<Plane> planes = new ArrayList<>();
    public ArrayList<User> users = new ArrayList<>();

    // toto asi nie je najlepsie riesenie, ale funguje to xd
    // problem bol, ze pri serializacii a potom deserializacii sa menia referencie na objekty
    // takze napriklad, ked je start lietadla v airport1 nie je to to iste letisko ako airport1 ulozene v airports
    public synchronized void repairReferences() {
        String airportName;
        int airportIndex;
        for (Plane plane : planes) {
            airportName = plane.getStart().getName();
            airportName = airportName.replace("airport", "");
            airportIndex = Integer.parseInt(airportName) - 1;
            airports.set(airportIndex, plane.getStart());

            airportName = plane.getDestination().getName();
            airportName = airportName.replace("airport", "");
            airportIndex = Integer.parseInt(airportName) - 1;
            airports.set(airportIndex, plane.getDestination());
        }
    }

    public synchronized void saveAirports() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/airports.txt";
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(p));
            objectOutputStream.writeObject(airports);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void loadAirports() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/airports.txt";
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(p));
            airports = (ArrayList<Airport>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //System.out.println("ouch");
        }
    }

    public synchronized void savePlanes() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/currentPlanes.txt";
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(p));
            objectOutputStream.writeObject(planes);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void loadPlanes() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/currentPlanes.txt";
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(p));
            planes = (ArrayList<Plane>) objectInputStream.readObject();
            repairReferences();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadUsers() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/users.txt";
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(p));
            users = (ArrayList<User>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            //System.out.println("Prazdny zoznam :(");
            e.printStackTrace();
        }
    }

    public void saveUser() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/users.txt";
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(p));
            objectOutputStream.writeObject(users);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
