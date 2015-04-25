package engine.factories;

import engine.element.Wave;


/**
 * Factory for creating different waves
 * 
 * @author Qian Wang
 *
 */
public class WaveFactory extends GameElementFactory {
    private final static String MY_CLASS_NAME = "engine.element.Wave";

    public WaveFactory () {
        super(MY_CLASS_NAME);
    }

    /**
     * Given a GUID, returns the corresponding object with a prefilled parameters map and values
     * that
     * it represents
     * 
     * @param guid String of GUID identifying the object
     * @return Wave object
     */
    public Wave getWave (String guid) {
        return (Wave) super.getGameElement(guid);
    }
}
