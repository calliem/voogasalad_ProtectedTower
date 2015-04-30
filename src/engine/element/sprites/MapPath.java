package engine.element.sprites;

import java.util.List;
import annotations.parameter;
import authoringEnvironment.objects.Coordinate;


public class MapPath extends GameElement {

    @parameter(settable = true, playerDisplay = false, defaultValue = "null")
    private List<Coordinate> coordinates;

    @Override
    public void onCollide (GameElement element) {
        return;
    }

}
