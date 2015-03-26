Design
===

Introduction
---
For this project we are making a game engine, which facilitates the creation of games using visual editors. Our game engine will be for the tower defense genre. There are many aspects of gameplay that differ to distinguish one tower defense game from another and our engine should be flexible enough for users to modify any of those aspects when creating their game. Things that should be modifiable include: type/existence of paths, enemy abilities (e.g. flying, path-following), tower types/features, ammo strength (i.e. what objects can each ammo type harm), and tower placement rules.

[TODO: Open/Closed -- does anyone actually understand openess and closed...ness? I could probably write a paragraph ranting about how much I don’t understand it] Sean can do once we have a class diagram

In tower defense games, the player’s life is determined by the health of their base. A destroyed base triggers the lose condition and win conditions are triggered by clearing each round of enemies. The health can be determined by damage caused by direct attacks from enemies, the number of enemies that are able to infiltrate the tower, removal of goods from the tower, and any action that modifies the state of your base. Enemies do not target your players directly (if players are even present) but rather they attack them for the purpose of getting to your base.

To protect the base, tower defense games can provide weapons to attach to your base, towers that attack enemies en route, characters that engage in battle with the enemies, or any combination of those. The availability of those weapons are usually limited by funds and experience points. Difficulty can be adjusted by changing the number of enemies in a wave or your purchasing power of weapons and upgrades. 

Overview
---
###Authoring Environment and Player


###Engine and Data


###Integration


User Interface
---
Our game authoring environment will contain a GridPane holding multiple components. The main view will hold a MenuPane and a TabPane, while each TabPane’s components will differ (ie. Map, Waves, Levels, Projectiles, Units, Towers). The MenuPane will allow the user to save the game as a data file, load a separate datafile to continue editing a previously made game, and to exit the editor.

The Map, Waves, and Levels tabs will be similar in structure to each other. In the Map tab, the user will view a workspace in the form of a grid as well as a sidebar containing all editable features of the grid/map. From the sidebar, the user can set the name of the game, its dimensions, and the base number of lives that the user starts with. Additionally, the user can upload custom tiles into a tile display that shows the currently created tiles to the user. These tiles can be placed onto the grid first through tile selection and then through selection of specific components on the grid (ie. user would first click on a custom tile image. The image would have reduced opacity to show that it has been selected. Then the user will will select individual cells of the grid that will update to the tile). The user will also be able to create custom paths that units will later follow. Creating these here will allow selection in the units tab later on that will match individual units the the specific paths on the map that they will follow (also by specifying a start and endpoint to the line on the units tab). The user will specify the path by drawing a line on  the map and connecting consecutive points to each other. Lines will be removed by clicking on the points/lines themselves. The user will specify that he or she is done drawing the path by clicking a “Done” button that will appear at the bottom after the “Draw Path” button has been pressed.

Waves will follow a similar structure, containing a map and a sidebar. The user will be able to add a new wave, view already created waves, and specify the time between waves. User will be able to select specific units that they want in a wave, select a path for specific units (since units are reusable and intersecting or more complex paths may have units spawn from different locations, and set specific spawn/start and end points.

The Projectiles, Units, and Towers tabs will also be similar in structure to each other. In these tabs, the display will contain a selection of thumbnails of the respective elements. An overlaid window would be centered in the screen upon user selection in which the user can modify specific parameters of the chosen element. A plus button would allow the user to create an entirely new element with chosen parameters, which would be added to the selection after creation.


For different tabs, the content inside the overlay window will be different. For projectiles, selection and editing follows a similar pattern. The entire display will contain the list of projectiles that the user has created or loaded. From this, the user can click on a “plus” button or an already existing projectile and an overlay will appear containing the hierarchy of projectile upgrades, along with the properties of each projectile (ie. image, speed, honing, slow percentage). 

For towers, the user can design the tower’s upgrade hierarchy in this window by adding and rearranging upgrade order, specifying the upgraded tower’s parameters (higher range, higher firing range, etc.), uploading a new tower image, etc. To create a new tower, the user needs to specify the tower’s projectile (choose a projectile from the ones currently available) and its firing rate, image, range, cost, and when it becomes available to the player (which level). We may add required parameters as we go along as well (i.e. tower health). In the overlay we might have two “tabs”, one being for editing the tower itself, and one for displaying and editing the tower’s upgrade hierarchy tree (used to define different upgrade paths for a single tower type).

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

no API


For the 

Design Details
---
###Authoring Environment and Player


###Engine and Data


###Integration



Example games
---
[Desktop Tower Defense] (http://www.kongregate.com/games/preecep/desktop-tower-defense) represents a very general tower defense game where the user has the freedom to create towers anywhere, and then have those towers define the path upon which enemies travel. The board space is open until the user places towers, which then block off parts of the board. Enemies move is the shortest path from start to finish. This game introduces strategy in deciding where to place towers, as mazes can be created to prolong the path of enemies. Tower types include single attacks as well as area attacks. Flying enemies are also present, which can only be targeted by certain towers. 

[Bloons Tower Defense](http://ninjakiwi.com/Games/Tower-Defense/Play/Bloons-Tower-Defense-5.html) is a tower defense game with predefined paths for the enemies, as well as enemies which spawn multiple other enemies. Each level has one or multiples paths that the enemies follow. Towers can be created on non-path spaces on the game screen. Enemies in the game, which are balloons, may spawn multiple other balloons when defeated, until the lowest level balloon is defeated and does not spawn any more. The game also adds extras such as abilities for the towers, which are larger attacks that run on a longer timer, as well as bombs/tacks, which are attacks which the player can use at any time during the round. These are one-time use attacks. There are also different terrains on the map, so certain towers can only be placed on certain areas, such as pirate ships only being able to be placed in water. Tower upgrades are not necessarily defined as a set path in Bloons TD, but the user has the option of choosing between upgrade paths.

[Plants v. Zombies](http://www.popcap.com/games/plants-vs-zombies/online) is an example of a non-top-down tower defense game. Instead, enemies and towers are placed on paths in a line, so that the game works as almost a 1D tower defense game, albeit with multiple lanes. The acquisition of cash used to buy new towers is also different here, as it does not come from defeated enemies, but rather appears randomly throughout the game, and towers can be created which produce this cash. The win condition of this game is that all enemies are defeated in a round, but the lose condition differs from other games. Instead of a set number of lives, the player loses when enemies reach the end of a lane twice. Progress in this game can be saved after each level, and each level can be tried multiple times. Enemies can also destroy placed towers, as they are placed on the same path that the enemies take to reach the end of a lane.

[TODO: how does our code support the differences between these games

Design Considerations
---
The use of grids was discussed at length in all subteams. The use of grids introduces more complexity in how objects are placed, where spaces can hold which objects, and what the grid’s responsibilities are. Without a grid, objects would function at the pixel level, which reduces the need for code that handles grid spaces, but also makes writing an AI harder. The grid was debated as to what methods it should hold and if it should know what is on the grid space, or if the object on the grid space should know which grid cell object it is on. The issue of determining which spaces on the board can be used to place towers or enemies could be done either way. With pixels, we would use a set of pixels or coordinate spaces which could contain each type of object. Animation is also better when using pixels, as it will be smoother.

Team Responsibilities
---
