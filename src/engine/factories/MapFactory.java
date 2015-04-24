package engine.factories;

import engine.element.GameMap;


/**
 * Factory for creating different maps/layouts of the board
 * 
 * @author Qian Wang
 *
 */
public class MapFactory extends GameElementFactory {
    private final static String MY_CLASS_NAME = "engine.element.GameMap";

    public MapFactory () {
        super(MY_CLASS_NAME);
    }

    /**
     * Given a GUID, returns the corresponding object with a prefilled parameters map and values
     * that
     * it represents
     * 
     * @param guid String of GUID identifying the object
     * @return GameMap object
     */
    public GameMap getMap (String guid) {
        return (GameMap) super.getGameElement(guid);
    }
}
