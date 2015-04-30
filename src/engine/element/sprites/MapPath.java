package engine.element.sprites;

import java.util.List;
import java.util.Map;
import annotations.parameter;
import authoringEnvironment.objects.Coordinate;


public class MapPath extends GameElement {

    @parameter(settable = true, playerDisplay = false, defaultValue = "null")
    private List<Coordinate> coordinates;

    public MapPath () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        // TODO fill out this method
    }

    @Override
    public void onCollide (GameElement element) {
        return;
    }

}