package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import model.FlightPath;
import model.planes.Plane;

public class PlaneRender {

    public void highlightCompleted(FlightPath flightPath, AnchorPane currentScene) {
        Line line = new Line(flightPath.getStartX(), flightPath.getStartY(), flightPath.getX(), flightPath.getY());
        line.setStroke(Color.RED);
        line.setSmooth(true);
        line.setStrokeWidth(3);
        currentScene.getChildren().add(line);
    }

    public void drawFlightPath(Plane plane, AnchorPane currentScene) {
        Line line = new Line(plane.getFlightPath().getStartX(), plane.getFlightPath().getStartY(), plane.getFlightPath().getDestinationX(), plane.getFlightPath().getDestinationY());
        line.setOpacity(0.9);
        currentScene.getChildren().add(line);
    }

    public ImageView drawPlane(FlightPath flightPath, AnchorPane currentScene) {
        //Polygon polygon = new Polygon();
        //polygon.getPoints().addAll(flightPath.getX(), flightPath.getY(), flightPath.getX() - 20.0, flightPath.getY() - 10.0, flightPath.getX() - 20.0, flightPath.getY() + 10.0);
        //currentScene.getChildren().add(polygon);

        if (flightPath == null) { // ked uz lietadlo pristalo
            return null;
        }
        ImageView img = new ImageView(getClass().getResource("../content/plane.png").toExternalForm());
        img.setX(flightPath.getX() - 16); // 32 je sirka obrazka
        img.setY(flightPath.getY() - 15); // 30 je vyska obrazka
        img.setId("plane");
        img.setPreserveRatio(true);

        if (flightPath.getHeadingX() == 1) { // to znamena, ze ide dolava
            img.setRotate(90 + Math.toDegrees(flightPath.getHeading()));
        } else { // to znamena, ze ide doprava
            img.setRotate(270 - Math.toDegrees(flightPath.getHeading()));
        }
        currentScene.getChildren().add(img);

        return img;
    }
}
