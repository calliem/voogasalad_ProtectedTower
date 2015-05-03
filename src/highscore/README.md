### HighScore Module ReadMe

### Overview

This highscore module provides support for tracking multiple players and their associated score(s) across multiple "games" or game instances.

The module is accessible from anywhere throughout the program, and provides automatic display/sorting capabilities.  Further,
High Scores persist across program runs, and are able to load/maintain themselves with no input from the game designer.

Many methods throw a HighScoreException, which alerts the calling program why the high score could not be saved.  In the case where an
exception occurs, no scores are added.

Some definitions - note that while valueNames are case-sensitive, gameNames are not:

"gameName" - A string which determines which high score file to associate a particular score with.
A gameName can exist at whatever level you would like - a complete game, a level, etc. Note that gameNames will display all their associated scores together.

"instanceNumber" - A way to track multiple scores for the same gameName.
For example, player 1 could have an instance number of 0, while player 2 has an instance number of 1 - their scores display/are saved together, but the scores are different.
Alternatively, you could track multiple levels for the same player by changing the instanceNumber.

"valueName" - A string which provides a description for the value in the high score, such as "Shots Fired" or "Total Points".  This will be the category name in the scores table.

"value" - a double representing the value of the corresponding valueName.

All actions are implemented by first retrieving an instance of the controller, then calling any methods on that instance.

### Available Methods

* HighScoreController getController ()

	This method returns the HighScoreController for the program to call all other methods on.

* void setValue (String gameName, int instanceNumber, String valueName, double value)

	This method is used to set the value for the associated valueName to the given value.  The gameName determines which other scores
	the highscore will be grouped with - all Strings with the same name, will be saved in the same file and displayed together.

* void setValues (String gameName, int instanceNumber, Iterable<String> valueNames, Iterable<Double> values)

	This method calls setValue for each valueName, value pair contained in the iterables.

* void incrementValue (String gameName, int instanceNumber, String valueName, double increment)
	
	This method adds the increment to the already existing value associated with valueName. 
	Note that the valueName must already exist.
	
* void deleteGameHighScores (String gameName)

	This method deletes the highscores.xml file associated with the given gameName.

* void deleteAllHighScores ()

	This method deletes all files stores in the highscores folder.

* void clearAllGames (String gameName)
	
	This method removes all gameNames, resetting the controller.

* void clearGame (String gameName)
	
	This method removes the given gameName and all of its instances.
	
* void clearInstance (String gameName, int instanceNumber)	

	This method removes the specific instance associated with this gameName and instanceNumber.

* void displayHighScores (String gameToDisplay, String sortScoresBy, double width, double height)

	This method displays the file associated with the gameName gameToDisplay in a TableView, sorting the scores by the given valueName in sortScoresBy.  
	The size of the stage launched is given by the width and height parameters.

* void saveInstanceScoreData (String gameName, int instanceNumber, String userName)

	This method is used to save the given instance's high score to the XML file it is associated with, and clear the instance.
	Use saveGameScoreData to save all scores associated with a given game.

* void saveInstanceScoreData (String gameName, int instanceNumber)

	Equivalent to calling saveScoreData(gameName, instanceNumber, null)

* void saveGameScoreData (String gameName)

	Saves all instances associated with the given gameName to XML.

* void saveAllScoreData ()
	
	Calls saveScoreData for all known gameNames.  All gameNames/instances will be cleared
	after this method is called.
	
### Example Code

	HighScoreController controller = HighScoreController.getController();
	
	controller.setValue("player", 1, "Category 1", 5");
	
	controller.deleteGameHighScores("player");
	
	controller.incrementValue("player", 1, "Category 1", 5);
	
	controller.saveScoreData("player", 1, null); // equivalent to HighScoreController.saveAllScoreData in this case
	
	controller.displayHighScores("player", "Category 1");

The given code will display a high score table with one entry whose name is given by the user, with one category named Category 1 set to a value of 10.  No matter
how many times the program is run, this will be the only score displayed because of the clearGameHighScores("player") line.
	
### Example Implementation

An example implementation can be found in the HighScoreTester.java class.  

