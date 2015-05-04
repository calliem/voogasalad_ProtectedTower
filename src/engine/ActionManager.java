// This entire file is part of my masterpiece
// Janan Zhu

package engine;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import engine.element.sprites.GameElement;


/**
 * CollisionManager Object
 * 
 * This class represents a collision table that's created according to user settings from the
 * authoring environment. The sole method of this class is used to apply the appropriate actions to
 * the two Sprites according to user-defined logic.
 * 
 * @author Janan Zhu
 */

public class ActionManager {
    private Map<String[], Collection<BiConsumer<GameElement, GameElement>>> myDecisionMap;
    private static final int REQUIRED_KEY_LENGTH = 2;
    private static final String INTERACTION_MAP_FORMAT_ERROR =
            "InteractionMap is in invalid format";

    /**
     * Load the map of interactions and actions into the table.
     * 
     * @param interactionMap Map<String, List<Integer>> object that maps a String
     *        array representing the two Game Elements that have an interaction to a
     *        List<String> array representing a list of tags that correspond to actions.
     * @param actions Map<String,Collection<BiConsumer<GameElement, GameElement>>> object of actions
     *        mapped to tags that specify what actions should be applied to which gameElement.
     */
    public ActionManager (Map<String[], List<String>> interactionMap,
                          Map<String, Collection<BiConsumer<GameElement, GameElement>>> actions) {
        // check that input is valid
        for (String[] key : interactionMap.keySet()) {
            if (key.length != REQUIRED_KEY_LENGTH) { throw new InvalidParameterException(
                                                                                         INTERACTION_MAP_FORMAT_ERROR); }
        }
        // declare and load the decision map
        myDecisionMap = new HashMap<String[], Collection<BiConsumer<GameElement, GameElement>>>();
        for (String[] pair : interactionMap.keySet()) {
            myDecisionMap.put(pair, actions.get(interactionMap.get(pair)));
        }
    }

    /**
     * Boolean method that applies appropriate action upon collision of two gameElements according
     * to
     * decisionMap
     *
     * @param elementOne first GameElement to collide
     * @param elementTwo second GameElement to collide
     * @return true if an interaction was made
     */
    public boolean applyAction (GameElement elementOne, GameElement elementTwo) {
        String[] elementTagPair = getTagPair(elementOne, elementTwo);
        if (isAction(elementOne, elementTwo)) {
            for (BiConsumer<GameElement, GameElement> action : myDecisionMap.get(elementTagPair)) {
                action.accept(elementOne, elementTwo);
            }
            return true;
        }
        return false;
    }

    /**
     * Boolean method that looks into decision map and sees if an entry exists for given pair of
     * Elememnts
     * 
     * @param elementOne first element
     * @param elementTwo second element
     * @return True iff myDecisionMap contains the tag pair for the elements
     */
    public boolean isAction (GameElement elementOne, GameElement elementTwo) {
        String[] elementTagPair = getTagPair(elementOne, elementTwo);
        return myDecisionMap.containsKey(elementTagPair);
    }

    /**
     * Method for adding/appending to an entry in the actionManager
     * 
     * @param tagPair
     * @param actions
     */
    public void addEntryToManager (String[] tagPair,
                                   Collection<BiConsumer<GameElement, GameElement>> actions) {
        if (myDecisionMap.keySet().contains(tagPair)) {
            actions.forEach(a -> myDecisionMap.get(tagPair).add(a));
        }
        else {
            myDecisionMap.put(tagPair, actions);
        }

    }

    /**
     * Helper function to get String[] for pair of GUID tags from two elements
     * 
     * @param elementOne First element
     * @param elementTwo Second element
     * @return Array containing GUID tags of elements
     */
    private String[] getTagPair (GameElement elementOne, GameElement elementTwo) {
        String[] gameElementTagPair = new String[REQUIRED_KEY_LENGTH];
        gameElementTagPair[0] = elementOne.getGUID();
        gameElementTagPair[1] = elementTwo.getGUID();
        return gameElementTagPair;
    }

}
