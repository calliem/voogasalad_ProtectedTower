package engine.element.sprites;

import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import annotations.parameter;
import authoringEnvironment.objects.Coordinate;
import engine.Reflectable;


public class MapPath extends GameElement implements Reflectable {

    @parameter(settable = true, playerDisplay = false, defaultValue = "null")
    private List<Coordinate> coordinates;

    public MapPath () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        super.addInstanceVariables(parameters);
        coordinates = (List<Coordinate>) parameters.get("Curves");
    }

    @Override
    public void onCollide (GameElement element) {
        return;
    }

    public Point2D getStartingPoint2D () {
        Coordinate first = coordinates.get(0);
        return new Point2D(first.getX(), first.getY());
    }

    public List<Coordinate> getCoordinateList () {
        return coordinates;
    }
}
