// This entire file is part of my masterpiece.
// Greg McKeon

package highscore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * List of objects that hold a high score for one user. This is the actual object backing a specific
 * gameName in the HighScoreController class.
 * 
 * @author Greg McKeon
 *
 */
public class HighScoreHolder {
    private List<HighScoreObject> scoreList = new ArrayList<>();
    private String fileName;
    private String gameTitle;
    private int instNumber;
    private String user;
    private File highScoreXML;
    private Properties propFile;

    protected HighScoreHolder (String gameName, int instanceNumber) throws HighScoreException {
        propFile = HighScoreController.loadResources();
        gameTitle = gameName;
        instNumber = instanceNumber;
        fileName = gameName + propFile.getProperty("saveName");
        highScoreXML = setupDir();
    }

    /**
     * 
     * @return the name of the associated game
     */
    protected String getTitle () {
        return gameTitle;
    }

    /**
     * 
     * @return the associate instanceNumber
     */
    protected int getInstanceNumber () {
        return instNumber;
    }

    /**
     * Sets a single high score category to have the specified value
     * 
     * @param valueName name of the category to set
     * @param value value to set the category to
     */
    protected void setValue (String valueName, double value) {
        HighScoreObject addToList = new HighScoreObject(valueName, value);
        int scoreIndex = scoresIndexOfString(valueName);
        if (scoreIndex > -1) {
            scoreList.get(scoreIndex).setValue(value);
            return;
        }
        scoreList.add(addToList);
    }

    private int scoresIndexOfString (String name) {
        for (int i = 0; i < scoreList.size(); i++) {
            if (name.equals(scoreList.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Increments the given valueName
     * 
     * @param valueName name of the category to increment
     * @param increment amount to increment by
     * @throws HighScoreException if the given valueName does not exist
     */
    protected void incrementValue (String valueName, double increment)
                                                                      throws HighScoreException {
        int scoreIndex = scoresIndexOfString(valueName);
        if (scoreIndex > -1) {
            scoreList.get(scoreIndex).incrementValue(increment);
            return;
        }
        throw new HighScoreException(propFile.getProperty("incrementException"));
    }

    /**
     * Makes the HighScores directory if necessary and gets/creates the file associated
     * with this gameName
     * 
     * @return
     */
    private File setupDir () {
        new File(propFile.getProperty("directory")).mkdir();
        return new File(propFile.getProperty("directory") + "/" + fileName);
    }

    /**
     * Clears the current Holder
     */
    protected void clear () {
        scoreList.clear();
    }

    /**
     * 
     * @return the File holding this game's associated high scores
     */
    protected File getFile () {
        return highScoreXML;
    }

    /**
     * 
     * @return the HighScoreObjects within this Holder
     */
    protected List<HighScoreObject> getObjectList () {
        return scoreList;
    }

    /**
     * Finds the value associated with a given valueName
     * 
     * @param valueName String to find
     * @return associated value for given category name
     */
    protected String getValueAt (String valueName) {
        int index = scoresIndexOfString(valueName);
        if (index != -1) {
            return Double.toString(scoreList.get(index).getValue());
        }
        return propFile.getProperty("notFound");
    }

    /**
     * Used to set a userName for this Holder.
     * 
     * @param username name to associate this Holder with
     */
    protected void setUsername (String username) {
        user = username;
    }

    /**
     * 
     * @return the Holder's associated username
     */
    protected String getUsername () {
        return user;
    }
}
