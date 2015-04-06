package engine.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;


public class TowerFactory {

    private Map<String, String> myTowers;
    private final static String MY_CLASS_NAME = "engine.sprites.Tower";

    public TowerFactory () {
        myTowers = new HashMap<>();
        // TODO: Fill tower map
    }

    public Tower getTower (String userInput) {
        if (!myTowers.containsKey(userInput)) {
            throw new InvalidParameterException(userInput + " is an undefined tower");
        }

        Tower tower = (Tower) Reflection.createInstance(MY_CLASS_NAME);
        // TODO: set tower Parameters

        return tower;
    }
}
