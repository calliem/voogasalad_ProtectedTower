package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import engine.Collidable;


/**
 * CollisionTable Object
 * 
 * This class represents a collision table that's created according to user settings from the
 * authoring environment. The sole method of this class is used to apply the appropriate actions to
 * the two Collidables according to user-defined logic.
 * 
 * @author Janan Zhu
 */

public class CollisionTable {
    private Map<PairList<Predicate<Collidable>>, PairList<Consumer<Collidable>>> myDecisionTable;
    private Boolean[][] myAlternateDecisionTable;
    private int numConditions;
    private int numActions;
    private int myTableWidth;
    private List<String> myClassTags;

    /**
     * Constructor
     * 
     * @TODO Error checking to make sure conditionValues and actionValues are in the right format,
     *       ie. correct array size
     * 
     * @param conditions A list of predicate functions, the set of all possible conditions about
     *        Collidables that need to be checked
     * @param actions A list of consumer functions, the set of all possible actions on Collidables
     *        that
     *        need to be carried out
     * @param conditionValues 2D array of booleans representing combinations of conditions for
     *        various rules. Different rules are along x-axis, different conditions are along
     *        y-axis. A particular array cell contains true if this condition needs to be checked
     *        for this rule
     * @param actionValues 2D array of booleans representing combinations of actions that need
     *        to be applied according to various rules. Different rules are along x-axis, different
     *        actions are along y-axis. A particular array cell contains true if this action needs
     *        to be checked for this rule.
     *        @deprecated
     */
    public CollisionTable (List<Predicate<Collidable>> conditions,
                           List<Consumer<Collidable>> actions,
                           Boolean[][] conditionValues,
                           Boolean[][] actionValues) {

        myDecisionTable =
                new HashMap<PairList<Predicate<Collidable>>, PairList<Consumer<Collidable>>>();
        numConditions = conditions.size();
        numActions = actions.size();
        myTableWidth = conditionValues[0].length;

        for (int i = 0; i < myTableWidth; i++) {
            List<Predicate<Collidable>> CollidableOneConditionList =
                    new ArrayList<Predicate<Collidable>>();
            List<Predicate<Collidable>> CollidableTwoConditionList =
                    new ArrayList<Predicate<Collidable>>();
            List<Consumer<Collidable>> CollidableOneActionList =
                    new ArrayList<Consumer<Collidable>>();
            List<Consumer<Collidable>> CollidableTwoActionList =
                    new ArrayList<Consumer<Collidable>>();

            for (int j = 0; j < numConditions; j++) {
                if (conditionValues[j][i]) {
                    CollidableOneConditionList.add(conditions.get(j));
                }
            }

            for (int j = numConditions; j < 2 * numConditions; j++) {
                if (conditionValues[j][i]) {
                    CollidableTwoConditionList.add(conditions.get(j));
                }
            }

            for (int j = 0; j < numActions; j++) {
                if (actionValues[j][i]) {
                    CollidableOneActionList.add(actions.get(j));
                }
            }

            for (int j = numActions; j < 2 * numActions; j++) {
                if (actionValues[j][i]) {
                    CollidableTwoActionList.add(actions.get(j));
                }
            }

            myDecisionTable.put(new PairList<Predicate<Collidable>>(CollidableOneConditionList,
                                                                    CollidableTwoConditionList),
                                new PairList<Consumer<Collidable>>(CollidableOneActionList,
                                                                   CollidableTwoActionList));
        }
    }

    /**
     * Alternate constructor
     * 
     * @param classTags List of String tags representing the class/type of the collidable
     * @param tableValues Table of booleans representing collisions (or lack thereof) between
     *        classes
     */
    public CollisionTable (List<String> classTags, Boolean[][] tableValues) {
        myAlternateDecisionTable = tableValues;
        myClassTags = classTags;
    }

    /**
     * Alternate method for returning a boolean of whether or not two collidables collided
     * based on user-defined collision table
     * @param firstCollidable First collidable to check
     * @param secondCollidable Second collidable
     * @return True if objects can collide, false otherwise
     */
    public boolean collisionCheck(Collidable firstCollidable, Collidable secondCollidable){
        int firstIndex = myClassTags.indexOf(firstCollidable);
        int secondIndex = myClassTags.indexOf(secondCollidable);
        return myAlternateDecisionTable[firstIndex][secondIndex];
    }
    
    /**
     * Void method that applies appropriate action upon collision of two Collidables according to
     * collisionTable
     *
     * @param CollidableOne first Collidable to collide
     * @param CollidableTwo second Collidable to collide
     * 
     * @deprecated
     */
    public void applyCollisionAction (Collidable CollidableOne, Collidable CollidableTwo) {
        for (PairList<Predicate<Collidable>> conditionList : myDecisionTable.keySet()) {
            boolean ruleSatisfied = true;
            for (Predicate<Collidable> condition : conditionList.firstList) {
                ruleSatisfied = ruleSatisfied && condition.test(CollidableOne);
            }
            for (Predicate<Collidable> condition : conditionList.secondList) {
                ruleSatisfied = ruleSatisfied && condition.test(CollidableTwo);
            }

            if (ruleSatisfied) {
                for (Consumer<Collidable> action : myDecisionTable.get(conditionList).firstList) {
                    action.accept(CollidableOne);
                }

                for (Consumer<Collidable> action : myDecisionTable.get(conditionList).secondList) {
                    action.accept(CollidableTwo);
                }
            }
        }
    }

    /**
     * PairList Object
     * 
     * Nested class created to store multiple lists in one object, used for concisely expressing
     * conditions and actions that need to be applied on different Collidables.
     * 
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
