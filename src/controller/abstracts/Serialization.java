package controller.abstracts;

import model.Airport;
import model.planes.Plane;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

public abstract class Serialization {

    public ArrayList<Airport> airports = new ArrayList<>();
    public ArrayList<Plane> planes = new ArrayList<>();

    public void saveAirports() {
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

    public void loadAirports() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/airports.txt";
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(p));
            airports = (ArrayList<Airport>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void savePlanes() {
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

    public void loadPlanes() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String p = path.toString() + "/data/currentPlanes.txt";
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(p));
            planes = (ArrayList<Plane>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
