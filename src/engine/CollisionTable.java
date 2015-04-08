package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import engine.sprites.Sprite;


/**
 * CollisionTable Object
 * 
 * This class represents a collision table that's created according to user settings from the
 * authoring environment.
 * The sole method of this class is used to apply the appropriate actions to the two sprites
 * according to user-defined logic
 * 
 * @author Janan Zhu
 */

public class CollisionTable {
    private Map<PairList<Predicate<Sprite>>, PairList<Consumer<Sprite>>> myDecisionTable;
    private int numConditions;
    private int numActions;
    private int myTableWidth;

    /**
     * Constructor
     * 
     * @TODO Error checking to make sure conditionValues and actionValues are in the right format,
     *       ie. correct array size
     * 
     * @param conditions A list of predicate functions, the set of all possible conditions about
     *        sprites that need to be checked
     * @param actions A list of consumer functions, the set of all possible actions on sprites that
     *        need to be carried out
     * @param conditionValues 2D array of booleans representing combinations of conditions for
     *        various rules. Different rules are along x-axis, different conditions are along
     *        y-axis. A particular array cell contains true if this condition needs to be checked
     *        for this rule
     * @param actionValues 2D array of booleans representing combinations of actions that need
     *        to be applied according to various rules. Different rules are along x-axis, different
     *        actions are along
     *        y-axis. A particular array cell contains true if this action needs to be checked for
     *        this rule.
     */
    public CollisionTable (List<Predicate<Sprite>> conditions,
                           List<Consumer<Sprite>> actions,
                           Boolean[][] conditionValues,
                           Boolean[][] actionValues) {

        myDecisionTable = new HashMap<PairList<Predicate<Sprite>>, PairList<Consumer<Sprite>>>();
        numConditions = conditions.size();
        numActions = actions.size();
        myTableWidth = conditionValues[0].length;

        for (int i = 0; i < myTableWidth; i++) {
            List<Predicate<Sprite>> spriteOneConditionList = new ArrayList<Predicate<Sprite>>();
            List<Predicate<Sprite>> spriteTwoConditionList = new ArrayList<Predicate<Sprite>>();
            List<Consumer<Sprite>> spriteOneActionList = new ArrayList<Consumer<Sprite>>();
            List<Consumer<Sprite>> spriteTwoActionList = new ArrayList<Consumer<Sprite>>();

            for (int j = 0; j < numConditions; j++) {
                if (conditionValues[j][i]) {
                    spriteOneConditionList.add(conditions.get(j));
                }
            }

            for (int j = numConditions; j < 2 * numConditions; j++) {
                if (conditionValues[j][i]) {
                    spriteTwoConditionList.add(conditions.get(j));
                }
            }

            for (int j = 0; j < numActions; j++) {
                if (actionValues[j][i]) {
                    spriteOneActionList.add(actions.get(j));
                }
            }

            for (int j = numActions; j < 2*numActions; j++) {
                if (actionValues[j][i]) {
                    spriteTwoActionList.add(actions.get(j));
                }
            }

            myDecisionTable.put(new PairList<Predicate<Sprite>>(spriteOneConditionList,
                                                                spriteTwoConditionList),
                                new PairList<Consumer<Sprite>>(spriteOneActionList,
                                                               spriteTwoActionList));
        }
    }

    /**
     * Void method that applies appropriate action upon collision of two sprites according to collisionTable
     *
     * @param spriteOne first sprite to collide
     * @param spriteTwo second sprite to collide
     */
    public void applyCollisionAction (Sprite spriteOne, Sprite spriteTwo) {
        for (PairList<Predicate<Sprite>> conditionList : myDecisionTable.keySet()) {
            boolean ruleSatisfied = true;
            for (Predicate<Sprite> condition : conditionList.firstList) {
                ruleSatisfied = ruleSatisfied && condition.test(spriteOne);
            }
            for (Predicate<Sprite> condition : conditionList.secondList) {
                ruleSatisfied = ruleSatisfied && condition.test(spriteTwo);
            }

            if (ruleSatisfied) {
                for (Consumer<Sprite> action : myDecisionTable.get(conditionList).firstList) {
                    action.accept(spriteOne);
                }

                for (Consumer<Sprite> action : myDecisionTable.get(conditionList).secondList) {
                    action.accept(spriteTwo);
                }
            }
        }
    }

    
    /**
     * PairList Object
     * 
     * Nested class created to store multiple lists in one object, used for concisely expressing conditions
     * and actions that need to be applied on different sprites.
     * @author Janan
     *
     * @param <T> Type of object contained in pairlist
     */
    public class PairList<T> {
        public List<T> firstList;
        public List<T> secondList;

        public PairList (List<T> listOne, List<T> listTwo) {
            firstList = listOne;
            secondList = listTwo;
        }

    }

}
