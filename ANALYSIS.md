CS 308: VoogaSalad Analysis
===================
Project Journal
=======

###Time Review
I worked on VoogaSalad 3/20 to 4/30 and spent on average 25-30 hours a week (with the exception of one midterm-heavy week) designing/planning, meeting, coding, and debugging. I worked predominantly on the front-end, creating the base foundation code for the basic layout template, editor abstraction, and controller, and coding all of the map editor, a portion of the level editor, and working on saving/loading parameters from the Game Editor, paths, tiles, and map, while sending these to the back-end. 

Approximately 60% of my time was spent coding, 20% of my time spent discussing how each component interacted together with the team and how to best use the saved data/information to integrate/link the different parts together, and the remaining 20% spent talking through design decisions or debugging issues that came up along the way. 

I developed, tested, packaged, and pushed the majority of the code that I wrote with the exception of the few classes that were further refactored by other team members (Controller).

The easiest tasks to do were general coding of the UI, which did not require much thought on design but were simply a result of how we wished to design our authoring environment visually. The most difficult part was determining how to share parameters/properties between the front-end and back-end without duplication, and many hours were spent discussing potential solutions (this will be discussed more in depth within the design section).  Another difficulty was passing information within the UI in a way that would not be replicated multiple times over the editors. Information passing itself was solved with a controller that concurrently wrote to XML which designing ways to display this updated information to the user without duplicating visualization was with sidebars that utilized composition to instantiate UpdatableDisplay objects with largely similar functionality. Coming to these solutions took lots of discussion and time in weighing the pros and cons, and thus were the most difficult parts of the project. 

###Teamwork
Our team planned several meetings early on and met frequently to determine large-scale design choices - most notably the use of the properties file to create front-end settings and to set back-end parameters. We discussed through many design options and alternatives, although some were tweaked slightly as we continued through the process. Our initial design in sharing properties files and utilizing reflection was kept throughout the project. As part of the agile development process, we did end up making changes to our original plan and adapting as well went along. We spent around 50 hours together as a team in total, although we had many sub team meetings and general meetings not including all members. I would say that at least approximately 1/2 of the time I spent coding was within the vicinity of another team member (not necessarily within my sub team) so that we could quickly bounce ideas off each other or discuss a change before proceeding.    

**Team roles:**
I have listed the major general and specific issues (outside of small code changes, bug fixes, etc.) that I tackled while working on this project. While I am not clear on the details of the exact specifics of what each person did, I have listed the general responsibilities that each person worked on (their specifics range much further than the brief summary that I have written):

**Frontend**

* **Callie**: 
	* Setup structure and abstraction of all general classes/tabs/workspaces (GameEditor, SpriteEditor, etc.)
	* Created layout of menubar and tabs for multiple editors 
	* Wrote the abstraction for the MainEditor that LevelEditor and MapEditor extend (sidebar and main layout format)
	* Created the main editable map in MapEditor
		* Made map interactive in the MapEditor: each tile can be selected and changed according to the tab selection in the sidebar 
		* Made map dimensions and tile size changeable while retaining previously placed tiles
		* Allowed for map saving and deleting; any changes are subsequently shown in the updatable view
		* Created sidebar that allowed for these interactive changes to be set and updated directly into the map
		* Background selection for map
		* Tile size adjustment that maintained properties of the map
		* Updating of visual inputs (textfields and graphic display) upon map switches
		* Saving values to the controller and to XML
		* Saved text inputs, dimensions, lists of keys to tiles of the array, lists of keys to the paths of each map
		* For pathing, saved keys to the paths along with data of their text inputs and list of coordinates
		* Created tiles and TileMaps
		* Differentiate saved maps that are active from inactive maps
		* View and store properties by hovering over tiles/paths/tilemaps
	* Created MapWorkspace, that centers and displays an interactive map, deletes a map from the view, and creates a new map to be edited
	* SpriteEditor
		* Most of this editor was Kevin’s work; however, I abstracted a small portion of SpriteEditor and refactored some duplicated code 
	* GameEditor
		* General textfields and user inputs
		* Saving values to the controller and to XML
	* LevelEditor
		* General setup and map selection from the UpdatableDisplay
	* Controller/Updating: Allowed for passage of information between authoring environment classes (especially for passing objects/information and updating tags upon selection/switches)
		* Allowed for tabs to be updated with necessary dependencies from other tabs upon each click.
		* Wrote initial draft of the controller (object updating); this was later refactored into a parameter map after design continued to be discussed
		* Wrote deletion (and subsequent deletion from the instance manager and controller) and image saving (utilizing snapshots/screenshots of current active nodes) parts of the controller
	* Created Map Workspace success and error message displays
	* Scaler
		* Simple util that “animates”/scales nodes as they are created. For example, creating or deleting a map results in a smooth transition from 0.0x to 1.0x (or 1.0x to 0.0x). This also occurs with the sprites as they are created/deleted and can also be used further in many of the other editors.
	* UpdatableDisplay and its subclasses
		* Displays objects within the same TileEditor with all properties attached (this is mainly for the MapEditor during the editing process). These are updated dynamically upon deletion, creation, and saving.
		* Active objects have a different visual appearance, and hovering over objects to be selected also display differently.
		* Deletion/creation of objects can update dynamically and will populate a VBox of HBox's
		* Reconstructs GameObjects based on basic parameters (keys, images, and names). This keeps data encapsulated and does not pass along information that is not needed
		* Created UpdatableDisplay subclasses for tiles, maps, and maps to be reconstructed on levels.
	* Pathing
		* Created Bezier/Cubic curves bounds to anchor points that would be added in order upon click
		* Control points appear along the slope of the line to default to added paths to straight lines
		* Setup spawn and end points based on path click order
		* Path saving to controller and XML
		* Path connection to individual maps so that only paths associated with a specific map will be displayed upon map selection
	* Created pathing and map test cases to test data files and to send to backend for testing
* **Megan**: 
	* Wave Editor 
		* Loading and selection of waves
	* Part of Round Editor
* **Kevin**: 
	* Sprite Editor
		* Draggable tags for all sprites with updating and deletion
		* Settings objects
		* Overlays
* **Johnny**:
	* Refactored controller to save object parameters and to write them to XML
	* XML Writer
	* Instance Manager

**Back-end **		

* **Greg **
	* High score utility
	* Annotations
	* Modifiers
	* Setting up sprites
	* Quadtree
	* Player
* **Qian**
	* Setup/refactored backend structure
	* Integrated parts
	* Back-end sprites
	* Game loading
	* Test files
	* Player
* **Bojia**
	* Setting up levels/round/wave
	* Factory
	* Bank/Shop
* **Janan**
	* Interaction
	* Test files
* **Michael**
	* Targeting
	* Collisions
* **Sean**
	* Pathing - parsing coordinates and implementing back-end Bezier curve
	* Conditions

Our team communicated online, but arranged many meetings each week. Most meetings composed of anyone who could make them at that time to allow for some joint programming and quick answers ofr questions. Larger meetings were much more difficult to setup with all team members but did occur on occasion when there were large design decisions or project implementations to be discussed. I feel that communication was on the low side especially because many people did not show up to meetings or would not update the team during hiatuses when other team members were dependent on their code. Some front-end team members were also less approachable and did not want to discuss design decisions further (or if the topic was brought up, all alternatives were quickly shot down as "bad" before discussing pros and cons), so it was sometimes difficult to have constructive discussions. Regardless, full team discussions were largely productive and effective; it would have been beneficial to have had higher attendance at them to get a better idea of how all working parts connected. Regardless, our team worked very efficiently during the time spent together as a team and it was very beneficial to be able to discuss through many of the decision factors together with them as well as peer program through bugs. 

The team's plan and design was very solid. The back-end to front-end properties and reflection implementation that was discussed prior to coding was held throughout, with the addition of a few things like annotations and modifiers to general project functionality. Deadlocking was generally avoided; however, the critical connection component, the Controller, was not completed or fully explained to both ends until towards the end of the project (a similar hiatus as mentioned above) which slowed down both the back end and front end considerably (the former relied on data files while the latter required access to parameters to pass data and reconstruct objects/information).

###Issues
I developed, packaged, tested, and pushed my own code in addition to refactoring small parts of other classes that I did not initially write. Looking back over my commit messages, I feel that they are an accurate representation of my contributions to the project (although some commit messages could have been a bit more detailed when I completed several tasks but only reported the most recent) and show a timeline and a small handful of the issues that I worked on or tried to solve. I took closed 14 issues listed on Github; however, our group did not utilize Github issues heavily and I do not believe that they fully reflect the responsibility of each team member accurately. A more general list of issues that I worked on is in the above section on team roles/responsibilities. I also worked on map size bounding error checking but did not close the issue. 

A major issue that I had was bringing up complex nodes from a previous tab into another tab. JavaFX does not allow a node to appear in multiple places at once, and even passing the entire object around to different tabs did not seem like an ideal solution - especially since the other editors were predominantly geared towards displaying the objects and the basic parameter as opposed to allowing the user to adjust and update its specific parameters. After discussing it through with the team and understanding the full functionality of the controller, we decided to "reconstruct" the object among tabs as a simpler version of itself consisting of only the needed parameters (ID, key, and name).  I constructed an UpdatableDisplay following a Template Method Design Pattern while creating a GameObject super class to make use of. After the issue was closed, the UpdatableDisplay still needed to be extended to different situations of not just reconstruction but display and updating workspaces when entire lists of GameObjects were passed in; by overloading the constructor, this was easily extended.

###Conclusions

I believe that our group in general underestimated the size of this project. Regardless, I still believe that we made considerable progress despite the setbacks in time of team member's absences. Within my team itself, I took on a fair share of responsibility in building the front-end implementation, but also in saving and sending data to the back-end in how objects were saved to the controller and helping to connect the two different parts. The objects that are now subclasses of GameObjects took the longest time to edit and update, largely because we did not clearly define how tiles would be set until near the end when I proposed the need for a TileEditor that would be similar to a SpriteView/Editor. A new feature of tags had to be implemented for this (we had discussed this at the beginning of the project but it had somehow slipped as we built it), and quite some time was spent changing how elements were saved (keys to each object) to XML and the controller as well as changes to how objects would be loaded from a different editor.

To be a better designer, I should find ways to increase abstraction and utilize design patterns. Increased abstraction helps extendability and is the main facet of the dependency inversion principle and makes code more extendible. Prioritizing things like composition would be helpful for reducing the fragility and dependency of code (ie. subclasses depending on a method of the superclass which could break all code if changed). Taking time to discuss with teammates both before and during the process have been vastly beneficial and is something that is very important to keeping everyone in touch with each other. 


Design Review
=======

###Status

The code is generally consistent in its layout, naming conventions, descriptiveness, and style. With the exception of some heavily UI layout areas of code, it is generally readable and understandable, although some additional documentation would not hurt. Several getters were used in classes that functioned as data structures, although these were mostly limited to simply getting values as opposed to setting them, and thus adhering to "closed to modification" facet of the open-closed principle. There are several dependencies passed through as parameters such  aswithin the different UpdatableDisplays, but the dependencies that do exist are clear and easy to find. Type requirements were made to be general such as with the GameObject class, although this was sometimes limited by the functionality of JavaFX classes (for example, setWidth() on a node does not set the width the same way that setFitWidth() on an ImageView does). 

GameObject is an easily extendable class that allows any user-created object to be "reconstructed" and redisplayed back to the user as an encapsulated object containing only the viewable image (thumbnail), unmodifiable key, and unmodifiable name. This allows the user to create the object in one specific tab but then be able to view a simpler version of the object in another. This was done partially to sidestep JavaFX's limitations on displaying a node in more than one place, but also primarily to ensure that only necessary parameters are re-revealed to the user and remain closed to modification within each GameObject (currently functionalities to set do exist, but setting will not write the data to any parts of the controller or XML file. Although this does not pose a danger of changing code or results unnecessarily, this class should be modified slightly to ensure that unnecessary setters are removed) . Instead, all parameters are sent to the controller and written to the XML data file and can only be edited within the proper editor by the user. Code utilizing reconstructed game objects will also be unable to directly access additional parameters (which they should not require access to). Objects extending GameObject are generally set by the user specifically and these grant flexibility to the user to edit and save parameters/properties. 

Another easily extendible feature are the properties files that are shared between front-end and back-end. Instead of hardcoding settings into each editor (ie. "HP", "Range") that is specific to a sprite, parameters can be easily added and removed by part_parameters properties file. These are applicable to any sprite and work efficiently for easy code extendibility without having to modify or change any existing code. These differ from GameObjects because of their inherent behavior. Objects creating from settings/parameters in the SpriteEditor classes require simple text input or color selection; however, creation/editing of GameObjects (ie. TileMap and Paths) require user interaction, drawing, and specific placement to be created that settings in a properties file cannot fully capture. This makes these objects slightly harder to extend; however, the GameObject hierarchy serves to help provide structure to extension. 

The part_names_english.properties properties file also similarly using reflection to construct classes that do not require much modification to the code. For example, adding on an ItemEditor would simply require an extra line in the properties file along with a basic creation of an ItemEditor class that extends Editor (or any of Editor's subclasses). The major drawback of this approach is that we are currently left with many empty classes that have no functionality. This can be fixed by reflecting ItemEditor to, say, the SpriteEditor instead, where it would more closely resemble. 

Classes that I did not write or refactor that are of interest in this analysis are the following:

* **Controller/InstanceManager**
	* (Note: I wrote a portion of the Controller class, but this section discusses the combined interaction of Controller and InstanceManager, the latter of which I did not write)
	* The controller was originally made to pass around objects between tabs/editors and would be utilized by calling an update() method that responded to a change listener on each tab. However, after much discussion, this approach was altered considerably to instead write parameters directly to a Map. These maps of parameters were then sent to the InstanceManager to be directly written to an XML after calling addPartToGame()
	* This design was discussed heavily and it was agreed upon that sending parameters to the controller would close them to further modification since there are no specific setters on the specific parameters. However, this design is still not perfect, since the entire map can be retrieved, values can be updated, and sent back to the controller to repudiate the XML.  While this approach still encapsulates information, it requires overwriting the entire map and XML file upon each change, which I feel is both inefficient and unnecessary when XML writing can simply be done at completion of editing the files. Updating a current parameter now involves retrieving all previous parameters, recreating a Map, and then setting that map to the same key within the controller. 
	* To improve this, perhaps an overloaded addPartToGame method can be created that will take the part key, the parameter name, and the actual parameter itself to update the controller and write to the instance manager. This falls in line with the Delegation design pattern by allowing a different class to call this method and update part/XML writing without the caller needing to know or have access to this information.  
* **FlowView**
	* FlowView is a longer visual class that caught my eye.
	* The first thing I noticed is an area of slightly duplicated code, where the showArrowAnimation method expands from 0 to 1 in a ScaleAnimation, nearly exactly what the Scaler class's current method is coded to do. These arrow objects could have utilized this static util to decrease code duplication (and the util itself could have been documented better to ensure eease of use). 
	* The createOptionSelector() method also returns null, which seems to be an odd choice to code a method that does nothing. Looking at the subclass, it functions as a placeholder to be overridden by subclass functionality. Perhaps in this situation an abstract template method design pattern may have been more appropriate since FlowViews can currently be instantiated as concrete classes but have no functionality in a method that seems to define it. (getWaveKey() and getPathKey() also return null values; it may have been better to abstract here) CreateOptionSelector() does not do as it does for an easily understood reason and does not seem appropriate in the class it is in, and while RoundFlowView does, it is extending an empty method and seems to suggest an issue similar to Liskov's Substitution Principle  (although not quite the exact hierarchy extension issue)
	* The constructor of the FlowView automatically assumes that the functionality is for waves, and while this is true for the situation that it is written for, it is not very extendible if different programs were to utilize it. For example, the initial director is hardcoded in as myController.getDirectoryToPartFolder("Wave"). These assumptions that limit flexibility can be passed in as parameters instead to allow the class to be utilized in other areas other than just for displaying round views directly within the round editor. 

* **GameElementFactory**
	* GameElementFactory looks to be an interesting implementation of the Prototype Manager Design Pattern, which acts as a factory but clones instead of instantiates objects.
	* The fillPackageMap method seems to be an exact replica of the class_list.properties parameter file. It appears that an attempt was made but needs to be finished in getting the actual values from the parameter file. This ensures that any additions or changes to the code will not result in having to change the values in multiple places and instead only have to change them in a minimal number of places. This will also allow for reuse in different programs other than those hardcoded into the class and allows the class to be utilized with many different properties files in many different combinations. 
	* Using reflection, game elements are "cloned" and created, which ensures a template is created with the same parameters as the template sprite and is fairly flexible (albeit slow) under reflection. 

###Design

The graphical authoring environment is composed of individual editors that interact with each other via the controller. The editors are divided into two types: SpriteEditors which are populated by settings objects created from properties files and all other editors which the user interacts visually/directly with to set properties. The controller connects the data from each editor together while also saving final object parameters to XML data files (via the controller and instance manager). In the backend, the game controller reads in the game file and parses all element parameters, and then routes the data to the proper classes (into their own maps of GUID to parameters). Then a new game is created and the maps of information are individually passed into the game. The game also leads to the layout, which places images into an observableList that the player uses to display the game.  

In order to represent a specific game, the user will begin by creating and saving objects within the authoring environment. Saving goes directly through the controller; doing so will save the results in XML data files, which will be opened by the control in the back end. Backend's GameElementFactory will utilize the properties files to reflect and clone instances into the backend. The game controller will create the game, which will create and specify levels, rounds, and waves subbsequently. The game will specify layoutt which uses observableLists to display data to the player.

The first bullet in the alternate designs section tackles one of the major design decisions made in how parameters would be shared between front-end and back-end, and became a fundamental part in how our controller, settings, and sprite editors would be built. GameObjects are differentiated from this because of their differing functionality and different creation/setting that also allow them to populate an UpdatableDisplay. As of current, only GameObjects can populate these displays, but as mentioned in the "Important Issues remaining in our Design" section, being able to relate GameObjects with ObjectViews to allow both to be constructed in UpdatableDisplays will be the next step in reducing dependencies and ensuring that all "objects" created and set by a user can be displayed in a similar manner. 

The controller maps keys to objects and in doing so allows XML data files to be saved uniquely by their key names, while also allowing objects to be able to refer back to a key from a previous saved game in order to run. Doing this also allowed for objects to be loaded from data files straight into the authoring environment as opposed to needing to be reconstructed multiple times. Writing to XML immediately after saving a specific object as opposed to saving an entire game was a design choice to ensure the safety of user work and ensure that data files are not lost after an accidental break in the program or if a computer were to suddenly shut off. While this last choice is debatable since creation and deletion of files constantly can create a lot of overhead, it allows the InstanceManager to be general enough to write all saved files to XML immediately as opposed to having to check for special instances of "saving".

**Three Features from the Assignment Specification**

* **Pathing**
	* Pathing is based on JavaFX Cubic Curves based upon the 4-point curve settings of Bezier curves. To create these, BoundLines and anchor points are required to setup the endpoints and center curve connecting the two points. The PathView object then constructs the curve based on the points that the user selects in sequential order. In order to determine selected anchor points from a newly created point, boolean values are encapsulated that specify whether a listener for each anchor point has been clicked. Additionally, composition is utilized to create a group (myRoot) as opposed to extending a Group directly. This allows PathView to instead extend GameObject and reduces the possibility of fragile subclasses due to limitations or changes of the superclass (in this case, a JavaFX object is not likely to change; however, the idea of composition over inheritance still applies here) while also providing flexibility if the composition is changed to say, a Node instead of a group. Because a PathView is broken down into individual encapsulated components, all three can be used separately to build different objects (ie. Anchor can be used on its own to get a draggable circle, Boundline can be used on its own to bind it to two other components/coordinates, etc.)
 
* **ProjectReader**
	* Project reader reads in the main_environment_english properties file and utilizes reflection to construct the various editors that are needed to undergo tile, sprite, map, level, etc. editing. This class is dependent on a properly formatted properties file that maps the name of the editor to be created to the tab name. It is also dependent on the class_list properties file to generate settings objects that will populate the parameters of the sprite view and prompt the user for input to be saved to the object/controller. The class_list properties file refers to the respective back-end classes that the fields are retrieved from. The design for this feature is fairly extensible since adding on more functionality would be the equivalent here of adding on additional classnames (or editors in the case of the other properties file) to the properties file to extend the design further.
* **Settings objects**
	* Settings objects allow for the user to pass in parameters to the SpriteViews to update them. The types of settings to be populated are determined by the ProjectReader, while the settings themselves are UI elements created to allow the user to update information. The settings objects seem fairly extendible, namely because the main class is abstract and allows for multiple subclasses to build off of its functionality. The settings class itself is dependent on controller, part, labels, and values. However, the simpler settings objects (ie. IntegerSetting, BooleanSetting) do not need this dependency and it may have been better to further abstract the Settings objects to differentiate between those that did and those that don't. This hierarchy implements an abstraction method design pattern that builds up the interaction layout and parses fields. 

### Alternate Designs

As mentioned above, the project's design was very flexible and could handle extension in parameters, multiple editors, etc. very easily. The original API's did not change significantly over the course of the project, although new features such as modifiers and annotations were added in that provided further implementation and extendibility to our project. Interaction between editors and the controller should have been discussed more extensively before proceeding with the project, as some time was wasted in key saving/loading as well as creation of TileMaps from String keys as opposed to default Tile objects. 

**Design Decisions**

* A major decision that was tackled in our code was how to initialize / share parameters of objects between the front and back-end without duplicating code. We initially considered an InstanceManager that would utilize composition to create the data structures in the front-end from  a list of parameters/properties listed within the properties file.  The parameters would initially be set to default values as dictated within the properties file. In doing so, these classes would store the necessary parameters and provide the necessary get methods in order to retrieve these parameters. The back-end would then also have a Tower, Projectile, etc. class that would instantiate one of the front-end data structures as well as create the back-end'sown dynamic methods accordingly (ie. move(), etc.). These objects would only have protected set methods within the front-end data structure, so that only methods in the front-end would be able to update the parameters (ie. a user changes the HP to 100 instead of 50). However, these changes would not be accessible to the back-end, which would only be able to access the parameters via public get methods. We believe this code would be fairly robust since updating parameters in the front-end would automatically reflect a change in the back-end without needed to change any back-end code. 

	However, this solution was still not optimal since front-end would have direct access to back-end via the instance. Instead, we looked into utilizing properties further in such a way that front-end code would almost never have to change when a parameter was added or changed. Using this method, a single new parameter would also be able to be added to multiple sprites at once simply by adding something like "Mana" to various parameter fields 

* When the controller was first created, it was established as Singleton under the assumption that an authoring environment would only need to utilize one controller. This controller would pass objects between classes and contain getters that allowed access to the entire objects. The would provide easy of accessing variables among all classes and thus being nearly as accessible as global variables without being open fully the same kind of direct access/modification.

	However, this introduces an unnecessary restriction. What about situations where we may want multiple instances of the GAE running at once? What if we do not want to provide access to entire objects across classes. This introduced two resulting design choices: updating parameters and object reconstruction. Object reconstruction follows the prototype pattern to an extent and is discussed in more detail in the lower bullet, while updating parameters is discussed in this bullet. 

	To update parameters, it was decided that passing entire objects was not fully adhering to the Open-Closed principle. All classes that called a getter on the object would essentially be passed the whole object attached to all of its parameters and properties, that could be further accessed with getters and setters on the object. Instead of allowing objects to be passed, parameters would be passed into the controller to receive a key for the specific part type. For example, a part type "Path" would be passed into the controller associated with a list of its parameters; the PathView would then receive a key back that would be its identification for future uses. Similarly, the TileMap would be saved with its general parameters but along with a map of keys for all the unique PathViews associated with it. This way, the number and amount of parameters that can be edited are limited and controlled, encapsulating more details and closing them to modification. 

* Passing objects inherently also had two additional main problems: unnecessary functionality and JavaFX limitations. The object itself was usually also attached to action listeners (ie. TileMap) or to an overlay (ie. SpriteViews) so redisplaying this object was not always ideal without going removing these functionalities by directly updating the object. Additionally, JavaFX limited nodes to appear in only one place at a time so passing an object to be displayed between editors also required all update() methods to update on editor change. While the latter choice is still a viable solution, it increases the number of times that the object is passed around (ie. not only must the object go from the MapEditor to the LevelEditor, but it now must be passed back to the MapEditor yet again on tab change, and then yet again on change to the LevelEditor). If the object is changed at some point in one editor, the other editor may not be notified of this and it becomes easy to run into bug and errors during implementation. As a solution, object reconstruction following the basic idea of the prototype pattern was implemented. In approaching this reconstruction, our main consideration and procedure followed closely with the one listed below and was later implemented with the template method design pattern:
> 1. Create TileMapView in TileEditor (view only) — construct this in the mapupdatabledisplay class
> 2. Store parameters into controller (save image as a thumbnail)
> 3. Update updatabledisplay with just the image (made into a thumbnail) and name from the controller. Attach listener so that clicking on it will do something
> 4. Clicking on mapupdatabledisplay objects will update the connected mapworkspace and reconstruct a tilemapview object based on the parameters (can either do updating on tab change or reconstruction - decided on reconstruction of a simpler object because that's what the rest of the program and other tabs are doing and wanted to be as consistent as possible despite the fact that the mapeditor is fundamentally different)
> 5. The mapworkspace will reconstruct the current map everytime the tab is changed (inefficient but oh well)


**Best Features of Current Project's Design**
* Our code is most extendible in creation of editors and settings because of the flexibility of properties files. 
* Controller saving to prototype reconstruction also encapsulates information very well and allows for reconstruction of simpler objects with limited access to previously set information. Saving capabilities are also limited to restrain opportunities of overwriting/rewriting data and the controller in general does a good job of passing the necessary information between editors. One main con is that passing parameters no longer allows the luxury of controlled getters when the elements of the map are received (although the saving capabilities allows the writer to determine what properties to give to the controller); however, this is a tradeoff that we chose for the benefit of encapsulated information in object reconstruction. 

**Important Issues still remaining in the Design**

* As mentioned earlier, the Java reflection creation of editors is very extendible but has left some glaring issues in our current code. We have multiple editor subclasses that are essentially empty classes, and we should consider revamping our editor inheritance hierarchy to remove these for general editor subclasses that encompass these all. While MainEditor and SpriteEditor currently do this, they are labelled as abstract and should be made concrete because all of their implementation is already defined. 
*  GameObjects and SpriteViews/ObjectViews are currently two very different objects in terms of how they are created and updated by/interacted with by the user. While each function well independently, the former updating via user interaction and able to be placed onto an UpdatableDisplay, SpriteViews are populated using settings from a properties file and cannot be put onto an UpdatableDisplay (but perhaps should also be given this latter functionality so that the objects can be viewed in different classes that may need them). Because of this, the inheritance hierarchy of GameObjects and ObjectViews should be reconsidered, perhaps by implementation of a common interface or an overarching superclass for the objects that can better relate them together. While these objects are interacted with and created differently, they are all similar in that their bare properties (names, keys, and the occasional image).

###Code Masterpiece

In my code masterpiece, I refactored the UpdatableDisplay abstract superclass and two of its subclasses as an example illustration of how the hierarchy works. The UpdatableDisplay was built with the idea of the Strategy design pattern in mind; however, it deviated slightly from it in its use of abstract classes/methods instead of interfaces, to better capture the dependencies of each different UpdatableDisplay types. As I continued writing, it became apparent that the Template Method Pattern was perhaps more appropriate in this situation. I had also considered adding lambda consumers as parameters to be passed into the UpdatableDisplay under the assumption that this would remove the need for additional classes; however, it turned out that it was not possible to specify these from a different class yet have them still act on the objects within the for-loop iteration. As a result, I decided on utilizing the Abstract Template Pattern, which gave subclasses the flexibility to implement differing functionality, control the subclass methods to be utilized, and avoid duplication. While there currently is predominately only the objectSelected() method,  future extensions of the authoring environment can utilize the specific UpdatableDisplays further (for example, hovering over could display properties from the controller, more text in addition to the name could be added for specific displays, etc.), which would require more methods and making the design pattern a more easily useful solution. A similar abstract template pattern concept is used in several other places as well, including within the sidebar class hierarchy, that allows for flexibility when building components of a more complicated hierarchy.

In refactoring the UpdatableDisplay, the SimpleObject and GameObject classes were refactored (but were not included in the final masterpiece as they are not a part of the hierarchy) to also illustrate the functionality of the class. In order to best keep with the Open-Closed Prinicple where objects are open to extension but closed to modification, SimpleObjects do not have any setters accessible by outside classes (and thus once created, the UpdatableDisplay cannot change it - this aligns with the UpdatableDisplay’s functionality since it is simply a view/display and should not provide any access to the user or any code manipulations that can change the SimpleObject’s properties). Alternatively, the UpdatableDisplay constructor was overloaded to also extend functionality to GameObjects, which extend SimpleObjects. A list of GameObjects can also be displayed through the UpdatableDisplay, and these objects have all properties attached so that users can continue to interact with and change their properties (this is built into the functionality of the GameObject to be set by the user - but not changed by the code without user input). Composition was also considered for defining the objects, but SimpleObjects and GameObjects possessed more of an “is-a” than a “has-a” relationship and thus an inheritance hierarchy seemed more appropriate. The concept for SimpleObject "reconstruction" as a simpler clone of GameObject without needing to know more detailed/complicated parameters shares the same idea/premise as the Prototype Pattern. 

Lastly, UpdatableDisplay (and GameObject) is an abstract class, and building these as such focused on increasing abstraction was also a large portion of the design,  follows through with the Dependency Inversion Principle. The sidebar classes that utilize the UpdatableDisplay will not have to depend heavily on lower level classes since the abstract UpdatableDisplay provides a layer of abstraction between each specific display and the sidebar. Additionally, any general change in code to the updatable displays (ie. changing the number of objects iterated through such as limiting to only the first 5), will only need to be changed within the abstract display without further duplicated code or many changes in classes elsewhere. In terms of stability, the subclass pathupdatabledisplay, tileUpdatableDisplay, etc. all depend on UpdatableDisplay, which is more stable than themselves, which supports stability within abstraction within the UpdatableDisplay. 






