package engine;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import engine.element.sprites.*;


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
    private Map<String[], Collection<BiConsumer<Sprite,Sprite>>[]> myDecisionMap;
    private static final String PARAMETER_NAME = "PartType";
    private static final int REQUIRED_KEY_LENGTH = 2;

    /**
     * Load the map of interactions and actions into the table.
     * 
     * @param interactionMap Map<String[], List<Integer>[]> object that maps a 2-element String
     *        array representing the two Sprites that have an interaction to a 2-element
     *        List<Integer> array representing a list of integers that correspond to actions.
     * @param actions List<BiConsumer<Sprite,Sprite>> object of ordered actions that may be used to specify
     *        what actions should be applied to which sprites.
     */
    public ActionManager (Map<String[], List<Integer>[]> interactionMap,
                             List<BiConsumer<Sprite,Sprite>> actions) {
        // check that input is valid
        for (String[] key : interactionMap.keySet()) {
            if (key.length != REQUIRED_KEY_LENGTH ||
                interactionMap.get(key).length != REQUIRED_KEY_LENGTH) { throw new InvalidParameterException(
                                                                                                             "InteractionMap is in invalid format"); }
        }
        // declare and load the decision map
        myDecisionMap = new HashMap<String[], Collection<BiConsumer<Sprite,Sprite>>[]>();
        for (String[] pair : interactionMap.keySet()) {
            @SuppressWarnings("unchecked")
            Collection<BiConsumer<Sprite,Sprite>>[] actionArray =
                    (Collection<BiConsumer<Sprite,Sprite>>[]) new Collection[2];

            // Fill collection of actions
            Collection<BiConsumer<Sprite,Sprite>> pairActions = new ArrayList<BiConsumer<Sprite,Sprite>>();
            for (int i : interactionMap.get(pair)[0]) {
                pairActions.add(actions.get(i));
            }
            actionArray[0] = pairActions;

            Collection<BiConsumer<Sprite,Sprite>> actionsTwo = new ArrayList<BiConsumer<Sprite,Sprite>>();
            for (int i : interactionMap.get(pair)[1]) {
                actionsTwo.add(actions.get(i));
            }
            actionArray[1] = actionsTwo;

            myDecisionMap.put(pair, actionArray);
        }
    }

    /**
     * Void method that applies appropriate action upon collision of two Sprites according to
     * collisionTable
     *
     * @param spriteOne first Sprite to collide
     * @param spriteTwo second Sprite to collide
     * @return true if an interaction was made
     */
    public boolean applyAction (Sprite spriteOne, Sprite spriteTwo) {
        String[] spriteTagPair = getTagPair(spriteOne, spriteTwo);
        if (myDecisionMap.containsKey(spriteTagPair)) {
            for (BiConsumer<Sprite,Sprite> action : myDecisionMap.get(spriteTagPair)[0]) {
                action.accept(spriteOne,spriteTwo);
            }

            for (BiConsumer<Sprite,Sprite> action : myDecisionMap.get(spriteTagPair)[1]) {
                action.accept(spriteTwo,spriteOne);
            }
            return true;
        }
        return false;
    }

    /**
     * Returns boolean describing whether or not two sprites collided
     * 
     * @param spriteOne Sprite object
     * @param spriteTwo Sprite object
     * @return true if two specified sprites are colliding
     */
    public boolean containsActionFor (Sprite spriteOne, Sprite spriteTwo) {
        String[] spriteTagPair = getTagPair(spriteOne, spriteTwo);
        return myDecisionMap.containsKey(spriteTagPair);
    }

    /**
     * Helper function to get String[] for pair of sprite tags from two sprites
     * 
     * @param spriteOne First sprite
     * @param spriteTwo Second sprite
     * @return Array containing tags of sprites
     */
    private String[] getTagPair (Sprite spriteOne, Sprite spriteTwo) {
        String[] spriteTagPair = new String[2];
        spriteTagPair[0] = (String) spriteOne.getParameter(PARAMETER_NAME);
        spriteTagPair[1] = (String) spriteTwo.getParameter(PARAMETER_NAME);
        return spriteTagPair;
    }

}
