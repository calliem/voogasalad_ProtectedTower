package engine;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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

public class CollisionManager {
    private Map<String[], Collection<Consumer<Sprite>>[]> decisionMap;
    private static final String nameParameter = "name";

    /**
     * Constructor
     * @param interactionMap
     * @param actions
     */
    public CollisionManager (Map<String[], List<Integer>[]> interactionMap,
                             List<Consumer<Sprite>> actions) {
        for(String[] key : interactionMap.keySet()){
            if(key.length != 2 || interactionMap.get(key).length != 2){
                throw new InvalidParameterException("InteractionMap is in invalid format");
            }
        }
        decisionMap = new HashMap<String[], Collection<Consumer<Sprite>>[]>();
        for (String[] pair : interactionMap.keySet()) {
            @SuppressWarnings("unchecked")
            Collection<Consumer<Sprite>>[] actionArray =
                    (Collection<Consumer<Sprite>>[]) new Collection[2];
            
            //Fill collection of actions
            Collection<Consumer<Sprite>> pairActions = new ArrayList<Consumer<Sprite>>();
            for (int i : interactionMap.get(pair)[0]) {
                pairActions.add(actions.get(i));
            }
            actionArray[0] = pairActions;

            Collection<Consumer<Sprite>> actionsTwo = new ArrayList<Consumer<Sprite>>();
            for (int i : interactionMap.get(pair)[1]) {
                actionsTwo.add(actions.get(i));
            }
            actionArray[1] = actionsTwo;

            decisionMap.put(pair, actionArray);
        }
    }

    /**
     * Void method that applies appropriate action upon collision of two Sprites according to
     * collisionTable
     *
     * @param spriteOne first Sprite to collide
     * @param spriteTwo second Sprite to collide
     * 
     * 
     */
    public void applyCollisionAction (Sprite spriteOne, Sprite spriteTwo) {
        String[] spriteTagPair = getTagPair(spriteOne,spriteTwo);
        if(decisionMap.containsKey(spriteTagPair)){
            for(Consumer<Sprite> action : decisionMap.get(spriteTagPair)[0]){
                action.accept(spriteOne);
            }
            
            for(Consumer<Sprite> action : decisionMap.get(spriteTagPair)[1]){
                action.accept(spriteTwo);
            }
        }
    }

    /**
     * Returns boolean describing whether or not two sprites collided
     * @param spriteOne
     * @param spriteTwo
     * @return
     */
    public boolean collisionCheck(Sprite spriteOne, Sprite spriteTwo){
        String[] spriteTagPair = getTagPair(spriteOne,spriteTwo);
        return decisionMap.containsKey(spriteTagPair);
    }
    
    /**
     * Helper function to get String[] for pair of sprite tags from two sprites
     * @param spriteOne First sprite
     * @param spriteTwo Second sprite
     * @return Array containing tags of sprites
     */
    private String[] getTagPair(Sprite spriteOne, Sprite spriteTwo){
        String[] spriteTagPair = new String[2];
        spriteTagPair[0] = (String) spriteOne.getParameter(nameParameter);
        spriteTagPair[1] = (String) spriteTwo.getParameter(nameParameter);
        return spriteTagPair;
    }


}
