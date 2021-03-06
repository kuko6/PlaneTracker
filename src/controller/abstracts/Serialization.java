package controller.abstracts;

import model.Airport;
import model.User;
import model.exceptions.NoRegisteredUsersException;
import model.planes.Plane;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Serialization class is used for serialization of ArrayLists of type Airport, Plane and User.
 *
 * @author Jakub Povinec
 */
public abstract class Serialization {

    protected ArrayList<Airport> serializedAirports = new ArrayList<>();
    protected ArrayList<Plane> serializedPlanes = new ArrayList<>();
    protected ArrayList<User> users = new ArrayList<>();

    /**
     * Since references of Objects change during serialization. Method is used to "repair" them.
     * It takes airports start and destination from Object Plane and puts them in ArrayList serializedAirports.
     */
    // toto asi nie je najlepsie riesenie, ale funguje to xd
    // problem bol, ze pri serializacii a potom deserializacii sa menia referencie na objekty
    // takze napriklad, ked je start lietadla v airport1 nie je to to iste letisko ako airport1 ulozene v airports
    private synchronized void repairReferences() {
        String airportName;
        int airportIndex;
        for (Plane plane : serializedPlanes) {
            airportName = plane.getStart().getName();
            airportName = airportName.replace("airport", "");
            airportIndex = Integer.parseInt(airportName) - 1;
            serializedAirports.set(airportIndex, plane.getStart());

            airportName = plane.getDestination().getName();
            airportName = airportName.replace("airport", "");
            airportIndex = Integer.parseInt(airportName) - 1;
            serializedAirports.set(airportIndex, plane.getDestination());
        }
    }

    /**
     * Serializes ArrayList of type Airport to airports.txt
     */
    protected synchronized void saveAirports() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/airports.txt";
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(p));
            objectOutputStream.writeObject(serializedAirports);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes ArrayList of type Airport from airports.txt to ArrayList serializedAirports
     */
    protected synchronized void loadAirports() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/airports.txt";
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(p));
            serializedAirports = (ArrayList<Airport>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //System.out.println("ouch");
        }
    }

    /**
     * Serializes ArrayList of type Plane to planes.txt
     */
    protected synchronized void savePlanes() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/currentPlanes.txt";
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(p));
            objectOutputStream.writeObject(serializedPlanes);
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes ArrayList of type Plane from planes.txt to ArrayList serializePlanes
     */
    protected synchronized void loadPlanes() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/currentPlanes.txt";
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(p));
            serializedPlanes = (ArrayList<Plane>) objectInputStream.readObject();
            repairReferences();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes ArrayList of type User from users.txt to ArrayList users
     *
     * @throws NoRegisteredUsersException if users.txt is empty
     */
    protected void loadUsers() throws NoRegisteredUsersException {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/users.txt";
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(p));
            users = (ArrayList<User>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (EOFException e) {
            throw new NoRegisteredUsersException();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes ArrayList of type User to users.txt
     */
    protected void saveUser() {
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
