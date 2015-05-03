// This entire file is part of my masterpiece.
// Greg McKeon
package highscore;

/**
 * Creates an Object that associates a String/Double pair
 * 
 * @author Greg McKeon
 *
 */
public class HighScoreObject implements Comparable<HighScoreObject> {
    private String myName;
    private double myValue;

    public HighScoreObject (String name, double value) {
        myValue = value;
        myName = name;
    }

    public double getValue () {
        return myValue;
    }

    public String getName () {
        return myName;
    }

    public void incrementValue (double increment) {
        myValue += increment;
    }

    @Override
    public int compareTo (HighScoreObject highScoreObject) {
        return new Double(this.myValue).compareTo(new Double(highScoreObject.myValue));
    }

    public void setValue (double value) {
        myValue = value;
    }

}
