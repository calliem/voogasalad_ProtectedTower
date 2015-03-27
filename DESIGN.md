Design
===
The UI will be abstracted by dividing and organizing the tabs by the type of UI that they display and the functions that they contain. 

Introduction
---
For this project we are making a game engine, which facilitates the creation of games using visual editors. Our game engine will be for the tower defense genre. There are many aspects of gameplay that differ to distinguish one tower defense game from another and our engine should be flexible enough for users to modify any of those aspects when creating their game. Things that should be modifiable include: type/existence of paths, enemy abilities (e.g. flying, path-following), tower types/features, ammo strength (i.e. what objects can each ammo type harm), and tower placement rules.

In tower defense games, the player’s life is determined by the health of their base. A destroyed base triggers the lose condition and win conditions are triggered by clearing each round of enemies. The health can be determined by damage caused by direct attacks from enemies, the number of enemies that are able to infiltrate the tower, removal of goods from the tower, and any action that modifies the state of your base. Enemies do not target your players directly (if players are even present) but rather they attack them for the purpose of getting to your base.

To protect the base, tower defense games can provide weapons to attach to your base, towers that attack enemies en route, characters that engage in battle with the enemies, or any combination of those. The availability of those weapons are usually limited by funds and experience points. Difficulty can be adjusted by changing the number of enemies in a wave or your purchasing power of weapons and upgrades. 

For the player, the tower defense map will be loaded into the left side, while a preset sidebar location will be set on the right. In the sidebar, the towers will be displayed and the user can click and place towers onto the map. From that, the user can select individual towers to be upgraded to one of the subsequent towers on the next tier of the hierarchy. The game can be fast forwarded, played, and paused via a button that exists at the bottom of the sidebar. 

Overview
---
###Authoring Environment and Player

The Player class will serve as a thin client for the Game Engine. The Player is responsible for reading in the data files created by the authoring environment and providing an interface for the user to select the game to be played from the data files. Gameplay is completely delegated to the game engine; the player class creates the appropriate JavaFX graphical representation for each game object represented in the backend game engine and updates them according to observed changes in the engine data. Once the user has started a game, the player provides an interface to allow the user to pause and start a game. The Player keeps track of high scores, game preferences and game save data and contains functionality to write and load this information to/from data files.

In the Authoring Environment, the user will interact with multiple editors, each of which exists in its own tab, in order to create their game.  They will build towers, projectiles, units, and maps in separate editor tabs.  Additionally, they will build waves out of the Units they’ve created, and then levels out of their Maps and Waves (and towers if they want) in two other tabs.  All in all, the user will have six editors (currently) to use: Towers, Units, Maps, Projectiles, Waves, and Levels.

While the user is working on each kind of part, they will be able to save it.  When a user saves just a part, not the entire game file, the data will be stored in an InstanceManager class.  This class will keep all the data of parts of the game the user has created, and then write it all to an XML file when the user saves their game. 

###Engine and Data
The engine sets up and monitors the gameplay environment. At the top of the hierarchy, there is the Game class. This structure contains a Persistent Stat Manager object, Tower Manager object,  and a list of Level objects. The Persistent Stat Manager keeps track of persistent player stats such as score and money. The Tower Manager keeps track of available towers and their upgrade paths. 

The Level is the next step in the hierarchy. Level objects contain win/lose Conditions, layout, Transient Stat Manager, and list of Wavegroup objects. Win/lose conditions help the engine determine when to consider a level won or lost. Layout is the map that the player sees and builds on for the duration of the level; it is a list of grid cells. Transient Stat Manager keeps track of player stats that can be changed and/or reset in between levels such as health and lives count. For example, player health can be reset back to full after each level or the number of lives may change depending on the level difficulty.

The next step is the Wavegroup object. Within each level, there can exist multiple wavegroups and users have control over when each wavegroup starts so they have as much time as they want to build towers in between wavegroups. Each wavegroup object contains win/lose conditions, send rate, and list of waves. Similar to levels, the win/lose conditions determine when a wavegroup has been cleared and when the player has failed. Send rate determines the amount of time in between waves of enemies; the waves of enemies are stored in the list of waves.

At the bottom of the hierarchy, a wave object contains a list of enemies to send and the spawn rate, which determines how quickly the enemies are spawned. It also includes start and end points indicate where enemies can spawn and where the enemies need to go to hurt the player, respectively.

###Integration
The game engine provides the class definitions that are used to represent the different elements of the game, including towers, enemies, moving objects, levels, etc. The engine also includes a game controller, which can be used to run a given game and animate the levels/deal with collision/run the game loop. This represents the bulk of the backend portion of the game. The game player is used as a front end to running the game, and will run the game through the engine, but displays game controls (start/stop/next wave) and a GUI for the layout of the game screen. It lays out where the map is seen, where tower upgrades are seen, and where other text elements, like score and money are placed.

The game authoring environment uses the game engine as a guide to see which classes and game elements may be edited. These possible classes and parameters will be given to the authoring environment through data files. The authoring environment can then create a way for the user to change any parameters for any data type, and then create the objects that those changes represent. The specific classes with chosen options are saved through data files. The game options like levels and which enemies appear on which levels, are also saved through data files. The game engine may later load these data files to create the state of a game, which it can then run.

User Interface
---
Our game authoring environment will contain a GridPane holding multiple components. The main view will hold a MenuPane and a TabPane, while each TabPane’s components will differ (ie. Map, Waves, Levels, Projectiles, Units, Towers). The MenuPane will allow the user to save the game as a data file, load a separate datafile to continue editing a previously made game, and to exit the editor.

The Map, Waves, and Levels tabs will be similar in structure to each other. In the Map tab, the user will view a workspace in the form of a grid as well as a sidebar containing all editable features of the grid/map. From the sidebar, the user can set the name of the game, its dimensions, and the base number of lives that the user starts with. Additionally, the user can upload custom tiles into a tile display that shows the currently created tiles to the user. These tiles can be placed onto the grid first through tile selection and then through selection of specific components on the grid (ie. user would first click on a custom tile image. The image would have reduced opacity to show that it has been selected. Then the user will will select individual cells of the grid that will update to the tile). The user will also be able to create custom paths that units will later follow. Creating these here will allow selection in the units tab later on that will match individual units the the specific paths on the map that they will follow (also by specifying a start and endpoint to the line on the units tab). The user will specify the path by drawing a line on  the map and connecting consecutive points to each other. Lines will be removed by clicking on the points/lines themselves. The user will specify that he or she is done drawing the path by clicking a “Done” button that will appear at the bottom after the “Draw Path” button has been pressed.

Waves will follow a similar structure, containing a map and a sidebar. The user will be able to add a new wave, view already created waves. User will be able to select specific units that they want in a wave, the specific order, and the specific time between each unit.

Levels will contain a map and a sidebar, similar to the Maps tab. In this tab we will allow the user to create their game’s levels. To make a level, the user chooses a map from the ones he’s created, and selects waves to be spawned on that map, specifying their spawn points and setting delays between when those waves spawn. To select spawn points the user hovers over the available paths on the map and selects one. While each wave can only be spawned from one location, the user can spawn multiple waves from the same location, mix and match waves at different locations, etc. The user will be able to see a list of their created levels (shown by their corresponding map) in the sidebar.

The Projectiles, Units, and Towers tabs will also be similar in structure to each other. In these tabs, the display will contain a selection of thumbnails of the respective elements. An overlaid window would be centered in the screen upon user selection in which the user can modify specific parameters of the chosen element. A plus button would allow the user to create an entirely new element with chosen parameters, which would be added to the selection after creation.

For different tabs, the content inside the overlay window will be different. For projectiles, selection and editing follows a similar pattern. The entire display will contain the list of projectiles that the user has created or loaded. From this, the user can click on a “plus” button or an already existing projectile and an overlay will appear containing the hierarchy of projectile upgrades, along with the properties of each projectile (ie. image, speed, honing, slow percentage). 

For towers, the user can design the tower’s upgrade hierarchy in this window by adding and rearranging upgrade order, specifying the upgraded tower’s parameters (higher range, higher firing range, etc.), uploading a new tower image, etc. To create a new tower, the user needs to specify the tower’s projectile (choose a projectile from the ones currently available) and its firing rate, image, range, cost, and when it becomes available to the player (which level). We may add required parameters as we go along as well (i.e. tower health). In the overlay we might have two “tabs”, one being for editing the tower itself, and one for displaying and editing the tower’s upgrade hierarchy tree (used to define different upgrade paths for a single tower type). The hierarchy will be displayed vertically in tiers, where the first upgrade will be tier 1, second upgrade tier 2, etc.


The user will be able to add individual units to the workspace, and each unit’s image and name will be displayed. Clicking on a unit will cause an overlay to appear that will cover the workspace. The overlay will contain customizable features (ie. hitpoints, speed, etc.) that can be entered in textboxes, and the hierarchy of unit upgrades will be displayed similar in the way to towers. 

The components of the property tabs (projectiles, towers, and units) will interact with each other and update the necessary areas in the wave and levels class.

java files:
Game Authoring Environment
public interface MapEditor(){
	public Cell[][] getTiles(){}
	public ArrayList<Path> getPaths(){}  //overlayed smaller-celled grid
}

public interface ProjectileEditor(){
public ArrayList<Projectile> getProjectiles(){}
}

public interface TowerEditor(){
	public ArrayList<Tower> getTowers(){}
}

public interface UnitEditor(){
	public ArrayList<Unit> getUnits(){}
}

public interface LevelEditor(){
	public ArrayList<Level> getLevels(){}
}

Game Player

Design Details
---
###Authoring Environment and Player
Our authoring environment will be split into two types of tabs: “MainEditor” tabs, which hold a map and a sidebar with settings, and “PropertyEditor” tabs, which consist of one window displaying currently created sprites that, when clicked, transition to an overlay menu that allows for individual settings to be customized.  The reason for this split is the difference between these two kinds of editors.

When editing a Level or Map, the user needs to see the map while he makes changes.  Editing a Map, the user wants to see what he’s doing (obviously) and editing a Level means assigning waves to different routes on the Map he’d dedicated for that level.  In this case, also, it is convenient for the user to be able to see the Map he’s working on.

In the other tabs, though, (Projectiles, Towers, Units, and Waves), the user need not know about any map while editing.  Therefore the space will be used to display all the different kinds of that part that the user has created, via sprites, so that they can easily switch from editing one to another. 
In all 6 editor tabs, the user has the ability to change parameters of the part he’s editing and save those changes.  Until the user saves his entire game, these changes will not be saved to an XML file, but rather into a list of state variables.  A property file will map the high-level name (Tower, Enemy, etc.) to a list of parameters it takes, while a second property file maps the parameter name to its type.   Each time the user of the Authoring Environment wants to make a new Tower, Unit, Projectile, etc, the authoring environment finds out what parameters that back-end class needs and gets them from the user.  This data will be sent to an XML file when the user decides to save his game by reflecting the proper instance variables and saving them in a List<Object> in the Parameter class.

Additionally, the overlay editor window used for the SpriteEditor’s will be uniform throughout the 4 editors.  The window will get its information on what to allow the user to change from the InstanceManager class.  This way, only one overlay class needs to be written, and it can be reused for as many different SpriteEditor’s we’d want to create.  This is another benefit of using the InstanceManager to store data while the user works, namely we can get what data is needed from InstanceManager so that it doesn’t have to be stored in a front end class like the one creating the overlay.

While the user is editing the parts of his game, the Instance Manager class will store the data.  When the user saves his game, this data will be written to an XML file via the xStream.toXML(Object o).  The object that is passed to this method is a List<Map<String, Object>>.

Two properties files will store the information needed to define each part of the game (Tower, Projectile, Unit, etc.).  The first will have keys that are the kind of part, followed by a space separated list of that part’s parameters.  For example:

Tower = Name, Hp, Range
Projectile = Name, Damage, Speed
Unit = Name, Hp, Speed, Damage

In this file, Towers need a name, hp, and range, projectiles need a name, damage and speed, and units need a name, hp, speed, and damage.  When the user, clicks “create new tower” in the UI (it may just be a + button), the string “Tower” is used to index into this properties file to tell the InstanceManager class what parameters a “Tower” needs to be defined, in this case name, hp, and range.  Now, the instance manager creates a Map<String, Object> where the keys are the parameters that the part needs, and the values are the default data for those parameters.  Where the defaults will be stored is currently undecided.  Now the InstanceManager has a framework in which to store the data the user enters as he makes changes to parts of his game.

The parameters information is also used to give the user an interface to change the parameters.  In this example, the user would see something like TowerName: followed by a text box, Hp: followed by a text box, etc.  Exactly how this UI will be created is currently undecided, but we do know that for at least Tower, Projectile, Unit, and Wave it will be displayed as an overlay.

After the user enters his desired data and clicks “save changes”, the InstanceManager class needs to update the map.  To do this, we need to know how to parse the String inputs we get from the user.  This is where a second properties file comes into play, one whose keys are the kind of parameter, and whose data is what data type that parameter should be.  For example:



HP = Integer
Damage = Double
Projectile = Projectile
Range = Integer
Name = String

We’ll then parse the data using reflection so that when we update the Object’s in the Map, the data is already of the correct type.  For example, Hp is parsed as Class.Integer.  

The final representation of a part in the InstanceManager class will be a map that looks something like:
[“HP”, “(Integer) 3”]
[“Name”, “(String) Tower1”]
[“Range”, “(Integer) 4”]

A list of Maps like this will be sent to the toXML(Object o) method and retrieved by the back end.  Each Map will be used in the back end to create actual Objects corresponding to the parts of the game the user defined in the editor.

###Engine and Data

Elements within the game, such as towers, enemies, projectiles, grid cells, and other objects will be represented by objects as part of a larger class hierarchy. A series of abstract superclasses will be used as a framework upon which to build these classes. The `Sprite` is the most general superclass and will require two abstract methods, `isCollidable(Type t)` and `isTargetable(Type t)`. These two will take in a type of object as an argument and return a boolean representing whether the given type can collide with the object, or if it can target the object, respectively. The data on which types will be able to collide with a certain sprite will be contained within a data file or within a general table where collision events can be searched. The objects will either make a call to the general table or store a copy of relevant information within the object itself. Each sprite can also contain an instance variables which stores a string of the type of object it is. These can be static variables for each class which can be used to reference the tables which contain collision events. 

Every Sprite is constructed by passing in an instance of the Parameter class from the backend.  This class contains parameters specific to that Sprite, and thus allows the parameters accepted by the method to be as dynamic as possible.  Adding an additional parameter or even high level function that extends Sprite is relatively simple.

The `GridCell` will extend `Sprite` directly and represents a square on the board, a single grid space in the game. This grid space will have information on what types of objects may be placed on it, such as if the player will be able to place a tower on the space, or if enemies will be able to travel over the space. This information could be added as instance variables, or they could also be contained in the same way as the collision and target information as noted above. 

Next, the `MovableSprite` is another abstract class which extends `Sprite` and adds information and methods which deal with movement and animation. The moveable sprite will contain instance variables which represent movement speeds, movement ranges, location, and heading. These objects will also add an `update()` class, which a general manager class can call in order to animate the sprites. 

A `Projectile` object will be created as a subclass of `MovableSprite` as it needs to be able to move and animate to hit an object. These projectiles will also have instance variables which store attack power, attack range, and other data related to the collision event. A `ResourceItem` object can be subclassed the same way, and be used to represent things like money dropped from enemies, or health blocks dropped from certain towers. These will make use of the movement range variable to indicate how far they will go from the drop point before they stop moving. These may also need a disappearance time to indicate after how long they will persist before disappearing if the user does not pick them up.  

The final abstract class, `GameSprite`, is used to represent the most significant parts of the game, the towers and enemies. The game sprite contains many variables that are shared between the tower and enemies objects, such as health, attack speed, attack power, attack range, cost, target priority, ability to hurt player, defense, etc. The `Tower` and `Enemy` objects will extend this game sprite and add on specific variables that may be needed for those classes in particular. For example, towers may include a build speed to represent the time it takes to build a tower where it cannot attack. 

Both enemies and towers may need to upgrade to degrade into other enemies and towers. This is taken care of by manager classes for each. The `TowerManager` class contains a tree of the full tower hierarchy, as well as a map that maps a certain tower type to a reference of the object in the map that it represents. Methods include finding the next tower choices when given a specific tower type. The `EnemyManager` contains a list of the order of how enemies degrade. When an enemy dies, it may spawn enemies of a different type and a different number of them. The nodes in the list will contain this information, so that when a method is called, it will return what enemies to spawn when a certain enemy type dies.

Towers will be able to specify a projectile type that it shoots. It can do this by saving a string of the name of the projectile that it shoots. These projectiles will be loaded into a table and then an instance given back when called. This is how a tower can be used to spawn projectiles. 

Game control, as described in the overview section, generally does not follow a class hierarchy. The Game class is the first thing instantiated in the game engine. The persistent stat manager class contains fields for player score, money, as well as methods for changing and retrieving them. The tower manager class contains a tree for every tower and its upgraded towers and a map that maps each tower to its corresponding node on its upgrade tree. Methods exist for getting the next tower in the upgrade sequence given a particular tower. Finally, the Game class contains a list of levels it contains and methods for getting those level objects.

Within the Level class, Win and Lose conditions are both Condition objects describing the state the game has to be in for it to be declared won or lost. Methods exist for finding out thresholds for specific parameters that need to be met for a condition to be met.  The condition class contains method that determine if these certain thresholds are met, by taking in relevant game parameters, such as an object representing the game state that contains just as much information as needed. Layout is essentially just a 2D array of gridcells and transient stat manager behaves similarly to the persistent stat manager, only with different parameters. Level contains a list of Wavegroups and methods for getting those.

IWave is the interface implemented by the Wavegroup and Wave classes. Parameters that they have in common are spawn rate and spawn list. Wavegroup spawns waves from the waves stored in its spawn list while Waves spawn enemies that are specified in its spawn list. In addition to those parameters, wavegroup contains win/lose conditions. The wave class contains spawn point(s) and end (target) points for enemies.

Example games
---
[Desktop Tower Defense] (http://www.kongregate.com/games/preecep/desktop-tower-defense) represents a very general tower defense game where the user has the freedom to create towers anywhere, and then have those towers define the path upon which enemies travel. The board space is open until the user places towers, which then block off parts of the board. Enemies move is the shortest path from start to finish. This game introduces strategy in deciding where to place towers, as mazes can be created to prolong the path of enemies. Tower types include single attacks as well as area attacks. Flying enemies are also present, which can only be targeted by certain towers. 

Different tower types and different enemy types may be able to be targeted differently. This is accounted for by the use of data files to store which towers can hit which enemies. Each of these will contain a type which will be used to find the relevant table entry to see if two specific objects can attack each other. The use of grid cells allows the user to build towers anywhere, which is an option that can be set by the grid cell. Enemy paths will find the shortest path via the AI that will be used in the game when no enemy paths are specified. The game here will be represented by a single level, which contains many waves. 

[Bloons Tower Defense](http://ninjakiwi.com/Games/Tower-Defense/Play/Bloons-Tower-Defense-5.html) is a tower defense game with predefined paths for the enemies, as well as enemies which spawn multiple other enemies. Each level has one or multiples paths that the enemies follow. Towers can be created on non-path spaces on the game screen. Enemies in the game, which are balloons, may spawn multiple other balloons when defeated, until the lowest level balloon is defeated and does not spawn any more. The game also adds extras such as abilities for the towers, which are larger attacks that run on a longer timer, as well as bombs/tacks, which are attacks which the player can use at any time during the round. These are one-time use attacks. There are also different terrains on the map, so certain towers can only be placed on certain areas, such as pirate ships only being able to be placed in water. Tower upgrades are not necessarily defined as a set path in Bloons TD, but the user has the option of choosing between upgrade paths.

Paths can be defined in objects created by the authoring environment and attached to each level. This means that different levels can have different paths. The grid cells contain instance variables representing what towers can be created on them, to simulate the need for water and land squares. Just as towers can be upgraded, enemies can have children that spawn when they are removed from the game after being defeated. This is taken care of by the enemy manager, where the sequence of balloons can be defined. The game is represented by multiple levels, each with multiple waves. The one-time use attacks and abilities can be made as special projectiles that the user may place throughout the running of the game. 

[Plants v. Zombies](http://www.popcap.com/games/plants-vs-zombies/online) is an example of a non-top-down tower defense game. Instead, enemies and towers are placed on paths in a line, so that the game works as almost a 1D tower defense game, albeit with multiple lanes. The acquisition of cash used to buy new towers is also different here, as it does not come from defeated enemies, but rather appears randomly throughout the game, and towers can be created which produce this cash. The win condition of this game is that all enemies are defeated in a round, but the lose condition differs from other games. Instead of a set number of lives, the player loses when enemies reach the end of a lane twice. Progress in this game can be saved after each level, and each level can be tried multiple times. Enemies can also destroy placed towers, as they are placed on the same path that the enemies take to reach the end of a lane.

The setup of this game is very different from the top-down shooter that is being created in the initial sprints. For this, a new framework for the view will need to be created which shows horizontal paths for the enemies. In this game, enemies are able to attack towers, and there are multiples paths per round. The game is represented by multiple levels, each of which contains multiple waves. Each wave have the same paths, which are the paths for each lane.

Design Considerations
---
The use of grids was discussed at length in all subteams. The use of grids introduces more complexity in how objects are placed, where spaces can hold which objects, and what the grid’s responsibilities are. Without a grid, objects would function at the pixel level, which reduces the need for code that handles grid spaces, but also makes writing an AI harder. The grid was debated as to what methods it should hold and if it should know what is on the grid space, or if the object on the grid space should know which grid cell object it is on. The issue of determining which spaces on the board can be used to place towers or enemies could be done either way. With pixels, we would use a set of pixels or coordinate spaces which could contain each type of object. Animation is also better when using pixels, as it will be smoother.

Lots of ideas were bounced around for how to store data before the user saves his game, when the data would be stored in an XML.  Our goal was to pick a data storage method that was simple but also very easy to extend.  We decided on an InstanceManager because it served the useful double purpose of not only storing the temporary data we needed to store, but also it can inform the front end of what parameters are needed to define an object.  This allows us to design the front end overlay class closed to modification; if we ever need to add a new kind of item and editor tab with an overlay, we won’t have to change the class creating the overlay since the class gets its information from InstanceManager.

We might want to allow the user to write his own setOnCollide methods for two parts of his game.  We’re probably going to represent “range” as a circle that’s invisible to the player of the game, but that defines the range of the tower.  When things collide with the circle

Team Responsibilities
---

Callie, Johnny, Kevin, and Megan will be responsible for designing the authoring environment.

Greg, Bojia, Qian, Michael, Janan and Sean will be responsible for designing and implementing the game engine.

Both groups will contribute team members to work on the player, to be determined by the members of the team who implement the xml data handlers.
