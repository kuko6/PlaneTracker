package view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import model.FlightPath;
import model.planes.Cessna;
import model.planes.Plane;

public class PlaneRenderer {

    public void highlightCompleted(FlightPath flightPath, AnchorPane currentScene) {
        Line line = new Line(flightPath.getStartX(), flightPath.getStartY(), flightPath.getX(), flightPath.getY());
        line.setStroke(Color.DARKBLUE);
        line.setSmooth(true);
        line.setStrokeWidth(3);
        currentScene.getChildren().add(line);
    }

    public void drawFlightPath(FlightPath flightPath, AnchorPane currentScene) {
        Line line = new Line(flightPath.getStartX(), flightPath.getStartY(), flightPath.getDestinationX(), flightPath.getDestinationY());
        line.setOpacity(0.9);
        line.setStroke(Color.WHITE);
        currentScene.getChildren().add(line);
    }

    public ImageView drawPlane(Plane plane, AnchorPane currentScene) {
        if (plane.getFlightPath() == null) { // ked uz lietadlo pristalo
            return null;
        }

        FlightPath flightPath = plane.getFlightPath();
        ImageView img;
        if (plane instanceof Cessna) {
            img = new ImageView(getClass().getResource("../content/cessna.png").toExternalForm());
        } else {
            img = new ImageView(getClass().getResource("../content/plane.png").toExternalForm());
        }

        img.setX(flightPath.getX() - 16); // 32 je sirka obrazka
        img.setY(flightPath.getY() - 15); // 30 je vyska obrazka
        img.setId("plane");
        img.setPreserveRatio(true);

        if (flightPath.getHeadingX() == 1 && flightPath.getHeadingY() == 1) { // to znamena, ze ide dolava a dole
            img.setRotate(90 + Math.toDegrees(flightPath.getHeading()));
        } else if (flightPath.getHeadingX() == 1 && flightPath.getHeadingY() == -1) { // to znamena, ze ide dolava a hore
            img.setRotate(90 - Math.toDegrees(flightPath.getHeading()));
        } else if (flightPath.getHeadingX() == -1 && flightPath.getHeadingY() == 1) { // to znamena, ze ide doprava a dole
            img.setRotate(270 - Math.toDegrees(flightPath.getHeading()));
        } else if (flightPath.getHeadingX() == -1 && flightPath.getHeadingY() == -1) { // to znamena, ze ide doprava a hore
            img.setRotate(270 + Math.toDegrees(flightPath.getHeading()));
        }
        currentScene.getChildren().add(img);

        return img;
    }
}
