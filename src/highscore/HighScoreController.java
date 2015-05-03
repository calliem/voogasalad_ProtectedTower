// This entire file is part of my masterpiece.
// Greg McKeon

package highscore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import authoringEnvironment.XMLWriter;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * A class that the game goes through to track high scores. To track a score, call
 * either setValue or incrementValue on a gameName, instanceNumber valueName, and value you would
 * like to track.
 * Each game is tracked
 * separately, but simultaneously with all other games and instanceNumbers. The gameName should be
 * the same for scores that
 * should be displayed and saved together. The instance number allows tracking of multiple scores
 * for the same gameName at once.
 * Each instanceNumber can be given a different userName when the saveScore method is called on a
 * specific gameName.
 * 
 * @author Greg McKeon
 */
public class HighScoreController {
    private List<HighScoreHolder> games = new ArrayList<>();
    private Properties propFile;
    private static final String directory = "directory";
    private static HighScoreController instance = new HighScoreController();

    /**
     * Private constructor to defeat instantiation.
     * 
     * @throws IOException
     */
    private HighScoreController () {
        propFile = loadResources();
    }

    protected static Properties loadResources () {
        File file =
                new File(
                         "../voogasalad_util/src/voogasalad/util/highscore/highscoreproperties.properties");
        FileInputStream fileInput;
        Properties propFile = null;
        try {
            fileInput = new FileInputStream(file);
            propFile = new Properties();
            propFile.load(fileInput);
            fileInput.close();
        }
        catch (IOException e) {
            System.err.print("File not Found!");
        }
        return propFile;
    }

    /**
     * This method returns the HighScoreController instance for the calling program
     * 
     * @return the singleton HighScoreController instance
     */
    public static HighScoreController getController () {
        return instance;
    }

    /**
     * This method adds or changes a single value for the high score category defined by valueName.
     * This method will only change the value for a single game at a time.
     * 
     * @param gameName name of the game whose value is being set
     * @param instanceNumber number of the instance whose value is being set
     * @param valueName the name of the value being set
     * @param value the value being set
     * @throws HighScoreException
     */
    public void setValue (String gameName, int instanceNumber, String valueName, double value)
                                                                                              throws HighScoreException {
        gameName = gameName.toLowerCase();
        if (!containsGame(gameName, instanceNumber)) {
            addTracker(gameName, instanceNumber);
        }
        getHolder(gameName, instanceNumber).setValue(valueName, value);
    }

    /**
     * This method adds or changes multiple values for the categories defined by valueName
     * Note this still can only act on one instance
     * 
     * @param gameName name of the game whose values are being set
     * @param instanceNumber number of the instance whose values are being set
     * @param valueNames the names of the values being set
     * @param values the values being set
     * @throws HighScoreException
     */
    public void setValues (String gameName,
                           int instanceNumber,
                           Iterable<String> valueNames,
                           Iterable<Double> values) throws HighScoreException {
        gameName = gameName.toLowerCase();
        Iterator<String> names = valueNames.iterator();
        Iterator<Double> vals = values.iterator();
        while (names.hasNext()) {
            if (!vals.hasNext()) {
                throw new HighScoreException(propFile.getProperty("unequalSizeException"));
            }
            names.next();
            vals.next();
        }
        // reset iterators
        names = valueNames.iterator();
        vals = values.iterator();
        while (names.hasNext()) {
            setValue(gameName, instanceNumber, names.next(), vals.next());
        }
    }

    /**
     * This method increments an already-existing value for the category defined by valueName
     * 
     * @param gameName name of the game whose value is being incremented
     * @param instanceNumber the number of the instance to increment
     * @param valueName the name of the value being set
     * @param value the value being incremented
     */
    public void incrementValue (String gameName,
                                int instanceNumber,
                                String valueName,
                                double increment)
                                                 throws HighScoreException {
        gameName = gameName.toLowerCase();
        if (!containsGame(gameName, instanceNumber)) {
            throw new HighScoreException(propFile.getProperty("incrementException"));
        }
        getHolder(gameName, instanceNumber).incrementValue(valueName, increment);
    }

    /**
     * This method clears ALL high scores by deleting the contents of the highscores folder.
     * This affects all saved scores.
     */
    public void deleteAllHighScores () {
        File[] dir = new File(propFile.getProperty(directory)).listFiles();
        if (dir != null) {
            for (File f : dir) {
                f.delete();
            }
        }
    }

    /**
     * This method clears the high scores associated with a specific game. Note for this method
     * that gameName followed by a number is equivalent to gameName
     * 
     * @param gameName the game to delete the associated high score file for
     * @throws HighScoreException
     */
    public void deleteGameHighScores (String gameName) throws HighScoreException {
        gameName = gameName.toLowerCase();
        File[] dir = new File(propFile.getProperty(directory)).listFiles();
        if (dir != null) {
            for (File f : dir) {
                if (f.getName().equals(gameName + propFile.getProperty("saveName"))) {
                    f.delete();
                }
            }
        }
    }

    /**
     * This method resets a given instance by removing all of its valueName/value pairs
     * 
     * @param gameName the name of the game to clear
     * @param instanceNumber the number of the instance to clear
     */
    public void clearInstance (String gameName, int instanceNumber) {
        gameName = gameName.toLowerCase();
        List<Integer> removeIndices = new ArrayList<>();
        for (int i = 0; i < games.size(); i++) {
            HighScoreHolder holder = games.get(i);
            if (holder.getTitle().equals(gameName) && holder.getInstanceNumber() == instanceNumber) {
                removeIndices.add(i);
                holder.clear();
            }
        }
        for (int i : removeIndices) {
            games.remove(i);
        }
    }

    /**
     * This method resets a given game by removing all of its valueName/value pairs
     * 
     * @param gameName the name of the game to clear
     */
    public void clearGame (String gameName) {
        gameName = gameName.toLowerCase();
        List<Integer> removeIndices = new ArrayList<>();
        for (int i = 0; i < games.size(); i++) {
            HighScoreHolder holder = games.get(i);
            if (holder.getTitle().equals(gameName)) {
                removeIndices.add(i);
                holder.clear();
            }
        }
        for (int i : removeIndices) {
            games.remove(i);
        }
    }

    /**
     * This method resets all games
     * 
     */
    public void clearAllGames () {
        games.clear();
    }

    /**
     * private method used to add a new game to the list of current games
     * 
     * @param gameName the name of the game to add
     * @param instanceNumber the instance number of the game to add
     * @throws HighScoreException
     */
    private void addTracker (String gameName, int instanceNumber) throws HighScoreException {
        gameName = gameName.toLowerCase();
        HighScoreHolder myHolder = new HighScoreHolder(gameName, instanceNumber);
        games.add(myHolder);
    }

    /**
     * helper that checks if a game with the given name exists
     * 
     * @param gameName the name to check for
     * @param instanceNumber the instanceNumber to check for
     * @return whether the game with the associated name and number exists
     */
    private boolean containsGame (String gameName, int instanceNumber) {
        gameName = gameName.toLowerCase();
        for (HighScoreHolder holder : games) {
            if (holder.getTitle().equals(gameName) && holder.getInstanceNumber() == instanceNumber) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method that finds the correct HighScoreHolder
     * 
     * @param gameToFind game to get the HighScoreHolder for
     * @param instanceToFind number of the instance to get the HighScoreHolder for
     * @return
     */
    private HighScoreHolder getHolder (String gameToFind, int instanceToFind) {
        gameToFind = gameToFind.toLowerCase();
        for (HighScoreHolder holder : games) {
            if (holder.getTitle().equals(gameToFind) &&
                holder.getInstanceNumber() == instanceToFind) {
                return holder;
            }
        }
        return null;
    }

    /**
     * Method that displays a HighScoreScene for the given game. Note that this does not
     * save the associated data, and only displays data from an XML file -
     * the user must explicitly call saveInstanceScoreData() or some variant to save current scores
     * for them to be included
     * 
     * @param gameToDisplay the name of the game to display high scores for
     * @param sortScoresBy the valueName to initially sort the scores by
     * @param width the width of the displayed stage
     * @param height he height of the displayed stage
     * @throws HighScoreException
     */
    public void displayHighScores (String gameToDisplay,
                                   String sortScoresBy,
                                   double width,
                                   double height)
                                                 throws HighScoreException {
        gameToDisplay = gameToDisplay.toLowerCase();
        List<HighScoreHolder> allScores =
                readInScoreData(new HighScoreHolder(gameToDisplay, 0).getFile());
        Scene highScoreScreen = new HighScoreScene(allScores, sortScoresBy).getScene();
        Stage highScoreStage = new Stage();
        highScoreStage.setScene(highScoreScreen);
        highScoreStage.setWidth(width);
        highScoreStage.setHeight(height);
        highScoreStage.setTitle(propFile.getProperty("tableTitle"));
        highScoreStage.show();
    }

    /**
     * Reads in the current HighScores and adds the data in the given instance to the file.
     * This must be called for each unique instance/game combination to include
     * their score in the scores screen.
     * 
     * @param gameName the gameName to save
     * @param instanceNumber the specific instance to save
     * @param userName the name of the user to associate with the score. If left blank, will launch
     *        an alert asking for their name
     * @throws HighScoreException
     */
    public void saveInstanceScoreData (String gameName, int instanceNumber, String userName)
                                                                                            throws HighScoreException {
        gameName = gameName.toLowerCase();
        HighScoreHolder gameHolder = getHolder(gameName, instanceNumber);
        if (gameHolder == null) {
            throw new HighScoreException(propFile.getProperty("nullException"));
        }
        List<HighScoreHolder> allScores = readInScoreData(gameHolder.getFile());
        if (userName != null) {
            gameHolder.setUsername(userName);
        }
        else {
            HighScoreAlert launch = new HighScoreAlert();
            launch.getName(gameHolder);
        }
        allScores.add(gameHolder);
        XMLWriter.toXML(allScores, gameHolder.getFile().getName(),
                        propFile.getProperty(directory));
        gameHolder.clear();
        games.remove(gameHolder);
    }

    /**
     * Saves the specified instance to the corresponding High Scores file.
     * An alert will be launched asking the player for
     * their name.
     * 
     * @param gameName the gameName to save
     * @param instanceNumber the instance number to save
     * @throws HighScoreException
     */
    public void saveInstanceScoreData (String gameName, int instanceNumber)
                                                                           throws HighScoreException {
        saveInstanceScoreData(gameName, instanceNumber, null);
    }

    /**
     * Saves all instances for the specified game to the corresponding High Scores file
     * 
     * @param gameName the name to save all instances for
     * @throws HighScoreException
     */
    public void saveGameScoreData (String gameName) throws HighScoreException {
        List<HighScoreHolder> gamesToSave = new ArrayList<HighScoreHolder>(games);
        for (HighScoreHolder holder : gamesToSave) {
            if (holder.getTitle().equals(gameName)) {
                saveInstanceScoreData(gameName, holder.getInstanceNumber());
            }
        }
    }

    /**
     * Method that saves all active games, prompting for userName from each
     * 
     * @throws HighScoreException
     */
    public void saveAllScoreData () throws HighScoreException {
        List<HighScoreHolder> gamesToSave = new ArrayList<HighScoreHolder>(games);
        for (HighScoreHolder holder : gamesToSave) {
            saveInstanceScoreData(holder.getTitle(), holder.getInstanceNumber(), null);
        }
    }

    /**
     * Private method that reads in the High Scores from a specified file.
     * 
     * @param highScoreXML the file to read in from
     * @return
     */
    private List<HighScoreHolder> readInScoreData (File highScoreXML) {
        List<HighScoreHolder> pastScores = new ArrayList<>();
        if (highScoreXML.exists()) {
            pastScores = (List<HighScoreHolder>) XMLWriter
                    .fromXML(highScoreXML.getAbsolutePath());
        }
        return pastScores;
    }

}
