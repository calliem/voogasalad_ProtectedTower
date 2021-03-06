package engine;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import engine.element.sprites.GameElement;
import engine.element.sprites.Sprite;


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
    private Map<String[], Collection<BiConsumer<GameElement, GameElement>>[]> myDecisionMap;
    private static final int REQUIRED_KEY_LENGTH = 2;

    /**
     * Load the map of interactions and actions into the table.
     * 
     * @param interactionMap Map<String[], List<Integer>[]> object that maps a 2-element String
     *        array representing the two Sprites that have an interaction to a 2-element
     *        List<Integer> array representing a list of integers that correspond to actions.
     * @param actions List<BiConsumer<Sprite,Sprite>> object of ordered actions that may be used to
     *        specify
     *        what actions should be applied to which sprites.
     */
    public ActionManager (Map<String[], List<Integer>[]> interactionMap,
                          List<BiConsumer<GameElement, GameElement>> actions) {
        // check that input is valid
        for (String[] key : interactionMap.keySet()) {
            if (key.length != REQUIRED_KEY_LENGTH ||
                    interactionMap.get(key).length != REQUIRED_KEY_LENGTH) { throw new InvalidParameterException(
                            "InteractionMap is in invalid format"); }
        }
        // declare and load the decision map
        myDecisionMap = new HashMap<String[], Collection<BiConsumer<GameElement, GameElement>>[]>();
        for (String[] pair : interactionMap.keySet()) {
            Collection<BiConsumer<GameElement, GameElement>>[] actionArray =
                    (Collection<BiConsumer<GameElement, GameElement>>[]) new Collection[REQUIRED_KEY_LENGTH];

            actionArray[0] = actionList(interactionMap.get(pair)[0], actions);
            actionArray[1] = actionList(interactionMap.get(pair)[1], actions);

            myDecisionMap.put(pair, actionArray);
        }
    }

    /**
     * Private helper method to generate collection of BiConsumers to be applied to certain sprite
     * as a result of collision, using action indices and given actions
     * 
     * @param actionIndices
     * @param actions
     * @return
     */
    private Collection<BiConsumer<GameElement, GameElement>> actionList (List<Integer> actionIndices,
                                                                         List<BiConsumer<GameElement, GameElement>> actions) {
        Collection<BiConsumer<GameElement, GameElement>> returnList =
                new ArrayList<BiConsumer<GameElement, GameElement>>();

        for (int i : actionIndices) {
            returnList.add(actions.get(i));
        }
        return returnList;
    }

    /**
     * Void method that applies appropriate action upon collision of two Sprites according to
     * collisionTable
     *
     * @param spriteOne first Sprite to collide
     * @param spriteTwo second Sprite to collide
     * @return true if an interaction was made
     */
    public boolean applyAction (GameElement spriteOne, GameElement spriteTwo) {
        String[] spriteTagPair = getTagPair(spriteOne, spriteTwo);
        if (isAction(spriteOne, spriteTwo)) {
            for (BiConsumer<GameElement, GameElement> action : myDecisionMap.get(spriteTagPair)[0]) {
                action.accept(spriteOne, spriteTwo);
            }
            for (BiConsumer<GameElement, GameElement> action : myDecisionMap.get(spriteTagPair)[1]) {
                action.accept(spriteTwo, spriteOne);
            }
            return true;
        }
        return false;
    }

    public boolean isAction (GameElement spriteOne, GameElement spriteTwo) {
        String[] spriteTagPair = getTagPair(spriteOne, spriteTwo);
        return myDecisionMap.containsKey(spriteTagPair);
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
     * Method for adding/appending to an entry in the actionManager
     * 
     * @param tagPair
     * @param actions
     */
    public void addEntryToManager (String[] tagPair,
                                   Collection<BiConsumer<GameElement, GameElement>>[] actions) {
        if (myDecisionMap.keySet().contains(tagPair)) {
            for (BiConsumer<GameElement, GameElement> action : actions[0]) {
                myDecisionMap.get(tagPair)[0].add(action);
            }
            for (BiConsumer<GameElement, GameElement> action : actions[1]) {
                myDecisionMap.get(tagPair)[1].add(action);
            }
        }
        else {
            myDecisionMap.put(tagPair, actions);
        }

    }

    /**
     * Helper function to get String[] for pair of sprite tags from two sprites
     * 
     * @param spriteOne First sprite
     * @param spriteTwo Second sprite
     * @return Array containing tags of sprites
     */
    private String[] getTagPair (GameElement spriteOne, GameElement spriteTwo) {
        String[] spriteTagPair = new String[REQUIRED_KEY_LENGTH];
        // TODO takes the first tag for now, make work for multiple tags
        spriteTagPair[0] = spriteOne.getTags().get(0);
        spriteTagPair[1] = spriteTwo.getTags().get(0);
        return spriteTagPair;
    }

}
