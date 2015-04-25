package engine.factories;

import engine.element.Round;


/**
 * Factory for creating different rounds
 * 
 * @author Qian Wang
 *
 */
public class RoundFactory extends GameElementFactory {
    private final static String MY_CLASS_NAME = "engine.element.Round";

    public RoundFactory () {
        super(MY_CLASS_NAME);
    }

    /**
     * Given a GUID, returns the corresponding object with a prefilled parameters map and values
     * that
     * it represents
     * 
     * @param guid String of GUID identifying the object
     * @return Round object
     */
    public Round getRound (String guid) {
        return (Round) super.getGameElement(guid);
    }
}
