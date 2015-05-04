roject Journal
===

Time Review:
We started this project a couple days after the planning date, on Friday March 20th, and ended around the morning of the day of the demo, on April 30th. Personally I spent probably around 50 or so hours on this project. I don’t really keep track of the hours but it was definitely a lot.
A lot of this project was spent discussing how to pass information around, designing the data flow, and ensuring that there was uniform deliverance of data. I worked on the authoring environment, and ended up doing a large portion of it. Another huge chunk of my time was spent testing and designing new features, as well as improving the interface for intuitive use and such.
I generally waited until I completed a certain feature before pushing to a branch dedicated to that feature. I made pull requests to master once the features I was working on were completed.
It was easiest for me to layout the visual components of the authoring environment and prepare the buttons/fields/etc. for their correct functions. The hardest tasks mainly had to do with making sure that each new feature I added could be reused anywhere, because of how interconnected our editors and sprites were. We also wanted to maintain consistent visuals throughout the environment, so things like overlays and button layouts needed to be extracted and made reusable. In hindsight, I should’ve spent more time up front making things functional before I started spending effort making it look nice. A lot of time was wasted making sure things looked good which sometimes delayed other features from moving forward.

Teamwork:
At first we met fairly frequently. We had design discussions throughout the project when the need arose, and I would like to believe our authoring environment design progressed well and became something that I’m proud of. As the project progressed, these meetings continued to go on, but less people showed up to them. Some were only present at a very small fraction of the meetings.
In the authoring environment, I was in charge of creating the sprite editors. This included the objects that were created when the user actually created a sprite, the overlay that popped up allowing the user to tweak the parameters of that sprite, and the saving and loading of a user-created sprite. I created the interface for tagging these sprites, tracking the tags that have been created in the environment, and allowing the user to create and remove tags as desired. I was also responsible for assembling the splash screen that’s displayed upon opening the application, and creating the Settings object hierarchy that was used for editing and parsing sprite parameter data on save. Consequently, I frequently worked in tandem with Johnny, who was in charge of assembling data files, managing the data flow between editors in the authoring environment, and ultimately between the authoring environment and the engine. He designed the controller in the authoring environment, which contains a map of the currently existing parts of the game and passes needed information to editors that require them. Near the end of the project he also did the level editor, which assigns rounds to a level and ultimately passes that information to the backend to dictate the order of the enemies that the player faces in a given round. Megan worked on the wave editor, which put together enemies and set delays between them. She also worked on a little bit of the round editor, which is the interface that allows the user to associate waves with paths on a map, and set the spawn delays between them. Callie was responsible for creating the map editor where the user designates the terrain of a certain map, lays down paths for enemies to follow, and sets the background image. In the game engine, Bojia was in charge of organizing levels, rounds, and waves, as well as the sprite factory and the bank. Greg created a high score utility, and wrote the class to pull parameters from back-end code using annotations and reflect on it to create authoring environment Settings objects. He also designed the modifiers idea, worked with setting up sprite objects, using the quadtree for collisions, and wrote the game player. Sean worked on pathing and win/lose conditions for the game. Janan did sprite interactions, and helped setup test files. Michael worked on targeting and collisions. Qian focused on backend design and code structure, integrated the core functionality of sprites and other parts of the game, and helped with game loading and writing test files.
Communication with the team was a bit lackluster this project. A few people never showed up to meetings (Michael, Sean), and others simply didn’t respond to requests for progress updates. Meetings typically consisted of 4 or 5 people working in one place, but never much more than that. Sometimes people that were needed for integration of authoring/engine were not present at meetings, which halted progress for almost days at a time.
Our plan for completing the project didn’t go so well. We never fully integrated until the very last few days, and at that point could not completely work out the bugs in the code that we experienced. We should have started writing data files for testing, and more importantly, we should have started on creating the game player much earlier than we did. For some reason we never prioritized the player, and as a result the engine couldn’t even test their code until very late in the project. Updating the project with new features went fairly smoothly though, despite the rest of those mishaps. We stayed up to date on what was present in the project, what was being worked on, etc. The problem was that a couple people just never contributed enough, and the burden of the work lay on the shoulders of only a handful of the group, when there was simply too much for those few people to handle. The main source of our problems, though, was the fact that we did not set up the game player until it as too late. This hindered our progress with testing and actually getting a game up and running, and I think also psychologically not being able to see your code working discouraged several people (myself included) from remaining passionate about the project.

Issues
I think my commit messages may not have accurately represented my contribution to the project. I think I did more than 70% of the authoring environment, visuals in particular, and I had to set up the entirety of what was the most important part of the authoring environment: the interface for creating sprites (projectiles, towers, enemies, and tiles). I, however, may not have committed as often as I should have. Some of my commits were several hundred lines long. Most of this was the result of me not wanting to push something before I completely finished it, and sometimes I just forgot to commit.
Our team didn’t utilize github’s issues very much, so I’ll go ahead and describe one of the actual issues that Johnny and I worked on fairly extensively, and that is the SpriteSetting object. When the user creates a tower, he has to select a projectile sprite for the tower. The purpose of the SpriteSetting is to get the projectiles currently available in the authoring environment that the user has either just created or previously created in a different session, and display them in a menu-type object that allows the user to click on one to select it. The biggest problem here is, how does one obtain the projectiles, and how can it be updated real-time? Does the tower editor hold an instance of the projectile editor and query the list when the user opens up the settings overlay? Johnny and I decided to create an authoring environment controller, which would keep and update a map of objects in the authoring environment. Each editor would then keep an instance of this controller, and upon adding a sprite would add the sprite’s components (parameters and corresponding data) to the map. This way, any information required for creating an object in any of the editors could go through a Controller method. The main design choice here was in dealing with how information gets passed between editors that shouldn’t necessarily know everything about other editors. Having a controller that has knowledge about everything that is currently present allows each editor to be independent and also makes the data flow a lot clearer.

Conclusions:
I think we underestimated the size of this project, but that wasn’t our main problem. People never really had a sense of direction; no particular goal to work towards, especially because there was no Player. Engine code couldn’t be tested, and it felt like people lost interest. Also, as development went on, more and more features that needed to be implemented arose before we could continue to move forward. These built up and created setback after setback after setback. I did the absolute most that I could on the authoring environment. I’m fairly certain I wrote almost all of the front-end for the authoring environment, and did a fair majority of the designing as well. I made sure to keep my team informed of my progress and sometimes demo new features that I just recently implemented.
A lot of the new features implemented were in SpriteView and SpriteEditor, the two main cogs of the Authoring Environment. The bulk of my editing thus took place in these places. I also spent a great deal of time refactoring Callie’s code, because her design frequently didn’t align with how the rest of the authoring environment had previously agreed to pass data around; she insisted on passing around objects, whereas the rest of the AE revolved around accessing the map contained in the controller for information. In fact, a lot of our time was sunk into rewriting and refactoring Callie’s code to make sense with how the rest of our stuff was designed.
I need to abstract first, and write subclasses later. The way I approached the authoring environment was I first created the TowerEditor where users could create towers, and then pulled code out of TowerEditor to create the SpriteEditor superclass. I should also get into the habit of fully thinking through design and laying out skeleton classes before actually writing code. This way, common methods and instance variables are thought of before the class is actually written. It’s always easier to go from top down than from bottom up.
I needed to pick up my teammates and motivate them to do work. I repeatedly suggested we set deadlines, work towards goals, and meet more as a team, but some team members just wouldn’t show progress. I felt like I could have done more to encourage more work to be done, and maybe I could’ve taken on even more of the Authoring Environment work, if that meant other people were working towards finishing their parts and creating new features. I could also have been a bit more patient at times with explaining the design we set up. Sometimes I was also a bit blunt with my frustration that other people weren’t getting their work done, and perhaps instead of being hostile I could’ve used my excitement to inspire their contributions.
I would work on the Game Player; make it look nicer, give it more features, and overall make it more functional than it currently is.

Design Review:
===

Status

Generally, yes, naming conventions and descriptiveness of method names and instance variables were consistent throughout, with only a handful of exceptions.
As far as readability goes, some of the front-end code might be a bit confusing. There are a few instances where the properties of StackPane vs. Group in JavaFX caused me to do some relatively confusing things (object translations to make things look nice, etc.), but that’s just JavaFX code. Callie’s code was a bit hard to understand though; when I went to help her with TileMap and her map editor, I had a hard time figuring out what her methods did and how things were being operated on. She had a lot of extraneous methods as well, and some dependencies that should have been eliminated. Also, the way she wrote her methods made it so that it could only be used in the specific way that she was using it.
The dependencies in other places were generally reasonable and more manageable. For some cases, JavaFX necessitated type requirements in the front end. For example, requiring a StackPane vs. requiring a Group – apparently Node does not have a getChildren() method, so I couldn’t generalize methods that acted on either object (I used StackPanes and Groups for content root nodes depending on desired alignments).
Adding a new type of sprite for the user to add is extremely easy; create a new editor and sprite class for it using the naming convention (SpriteEditor and SpriteView), add it as a key to the main_environment_english properties file, add its parameters to the proper properties file, and that’s all you need with our current design. Another relatively easy feature to extend is the Setting object. If the back-end were to require a new type of Setting, i.e. something like a list or a map, I would write a new Setting object with that type as the first part of the class name, implement the methods required of any Settings object, and override others if necessary.
Review three classes in the program you did not write or refactor in detail:
The first class I will choose to talk about is the GameElementFactory, a class in the Engine that is pretty cool to me. Qian and Bojia wrote this class to generate clones of certain game elements for situations where, for example, multiple copies of a tower need to be placed on a map or multiple enemies need to be placed on the path. This uses a prototype manager design, which, quite frankly, I don’t know much about, but provides the engine a very clean and easy way to clone objects when needed. Most of the inner workings of the class I’m not all too familiar with, because I haven’t actually seen much of the Engine code, but from the description of this class that I was given, it solves the problem of having to potentially create a new object each time an instance of that object needs to appear on the screen, when we can just have one original object, put it somewhere (like the shop, for example, for towers) and then create clones of it when needed. This code is almost completely geared towards created GameElements for this project, so it doesn’t seem reasonable to reuse it in a completely different project. Methods would have to be rewritten to return different types of objects. A Factory class can’t really be put into a different project and reused when it is this specific in function.
The next class I am going to review is the Controller class. Johnny mainly wrote this class, and as I will mention later I really appreciate this class from a design perspective. It is interesting because it simplifies all the parts of the game created in the authoring environment into the data that it represents, and stores this in a map. This function allows for easy access to any information in the environment from anywhere that it is needed, and solves the overall problem of how to pass around information. One thing I will say about this class, however, is that there are too many overloaded instances of the addPartToGame method. These could ultimately be compressed into two or three methods: adding a part to a game at a certain key, and adding a part to a game without a key. I would clean this part of the class up. Also, the part that Callie wrote that stores a List of GameObjects should be deleted from the class because it interferes with the elegance of the Controller design (and it really shouldn’t be in there anyways). A controller class like this where information is needed across different classes could certainly be beneficial in other projects. There are some dependencies on the InstanceManager in this class, but certainly any other project that stores data across multiple classes using labeled data values could reuse a class similar to this.
The last class I will review is the GameObject class. Looking at this class I find it quite interesting that there are only getters and setters. This is essentially the epitome of a useless class, and I’m not entirely sure why the instance variables myKey, myName, and myImageView couldn’t be stored in the classes that extend GameObject. As far as I know, the reason this GameObject hierarchy was created was so that Callie could loop over objects that required an UpdateableDisplay (like TileMap and Paths) without having to specify types. The main problem with GameObject is that it is duplicated functionality of the Controller. Callie should really be getting the name and the image for a certain object like a TileMap or a Path using the Controller methods, rather than setting up an object like this. In fact, the controller has methods called getImageForPart and getNameForPart already in place, making over half the GameObject class duplicated code that also violates our design. GameObject demonstrates Callie’s lack of understanding of the Controller, and hence the divergence of her code from the rest of our Authoring Environment design; a divergence that ended up causing a lot of problems throughout the project, and costing us time that could have been spent elsewhere. This code should not be reused in a different project; in fact, it really shouldn’t be in our project either.

Design

Let’s start with the first user interaction necessary in creating a game: the Authoring Environment. Here, the code is split into three main parts: the Controller and its associated classes, the Editor classes, and the View object classes. Let’s start with the View objects. These are basically just visual representations of sprites that the user has created, which will open up an overlay displaying editable information about that sprite (in the form of settings objects) when the user clicks it. In the Editor classes, we create and display these View objects. Whenever a new sprite is created, the editor sends information about that sprite (via the settings objects contained in that sprite and their current data values) along with its part type (tower, projectile, etc.) to the Controller, where that part is added to the map of currently existing parts. The Controller manages the majority of the data flow between Editors, and upon save communicates that information to the instance manager to be written to an xml file. Any and all objects that are part of the game that are created in the authoring environment will be stored in this controller map. Ultimately, each individual part is written to a data file. The collective gamefile, which contains pointers to each of the data files assembled in that game, is then passed to the backend when a game is loaded. In the engine, this data is loaded and unpackaged, and game elements are created from the data specified in this file. The GameController uses this data to run the game; creating sprites as specified by the delay times, checking for collisions, and updating game states. To do so it parses the parameter maps of all the elements created in the Authoring Environment, and then populates the correct game engine parts with that information. One interesting thing about the engine design is how we create the sprites and game elements after they’ve been initialized in the game (a tower has been made available, etc.), using a GameElementFactory to produce clones of the enemies that are spawning, of the towers being placed from the shop, etc.
The data files that store the parameter information for all the sprites and components of the game serve to represent a specific game. This information is contained in a Map<String, Map<String,Object>> called allPartsData in the InstanceManager class, which is accessed by both the authoring environment and the engine. AllPartsData is a map from PartKey to part data, and part data is just a map of parameters to their corresponding data values. The authoring environment populates this map, and the engine uses it to create the appropriate sprites.
I’ll walk through the different parts of my code, starting with one of my later developments, tags. Tag is the base object for this interface, storing the tag’s label. TagGroup is a UI component that displays and keeps track of a group of sprites (i.e. the tags for a specific sprite). This object has a base display, which shows the number of tags present in that group, and then an overlay that pops up when the user clicks it, which shows the actual tags in that group. TagDisplay is the ScrollPane running down the side of each editor that shows the current tags present in the environment. This list observes the map in the Controller of all currently present tags, a map that is updated whenever a tag is added to the TagDisplay as well as whenever a tag is deleted. All TagDisplays are synced to the Controller, so that when a tag is deleted in one editor, it is simultaneously deleted in all other editors. Setting objects are also important to each editor. As previously mentioned, these objects exist in a hierarchy. Each one has a parseField method that updates the object’s data value and checks for errors. Each Setting also has the capability of displaying an error and an error message, which is pretty cool. Creating a new Setting simply requires creating a new class with the appropriate type name, and overriding appropriate methods. SpriteEditor is the superclass for the editors that allow the user to manufacture sprites for the game. Each SpriteEditor contains a TagDisplay, from which users can drag and drop tags onto sprites that have been created in the environment. Each of these sprites is visually represented by a SpriteView object, which contains a TagGroup to display that sprite’s tags, and an overlay that displays a layout of Setting objects that has been populated through reflection on backend annotations. The SpriteView object saves each sprite that has been created to the Controller, which in turn adds it to the map of currently existing sprites and writes it to the InstanceManager when the game is saved. When the user presses ‘save’ after editing the parameter fields of a particular sprite, the SpriteView updates the Controller with the new information.
The way our code is designed minimizes dependencies and allows individual components to perform their functions independently. I can’t speak directly for the Game Engine code because I am a bit less familiar with their design, but the Authoring Environment design was fairly robust and I personally liked it. Originally we thought there would be some distinguishing factors between creating towers, projectiles, and enemies that necessitated specific editor and view classes that extended SpriteEditor and SpriteView, but once we realized they were all just sprites and creation could be centered around Setting objects with tags and modifiers to generate their distinctive behaviors, those extra classes became unnecessary. They remain in their respective packages but should probably be removed, along with the reflection code required to generate them. The Controller for the Authoring Environment helps to maintain each editor’s functional independence. Each Editor has an instance variable containing an instance of the controller, and each game has a singular Controller object, such that Editors requiring information from other Editors and other things present within the Authoring Environment simply have to call methods on the Controller class. This way, a SpriteEditor for towers doesn’t need a hard-coded instance of the SpriteEditor for projectiles to get the currently created projectiles, etc. I also like how we utilize modifiers and tags. Modifiers allow the user to specify certain behaviors between sprites that have certain tags, and tags can be dragged to and added to any type of sprite. This allows for maximum flexibility in terms of sprite creation and behavior assignment. For large part, we eliminated the interdependencies between editors. Objects, when they are created, store their information in the Controller. If other editors want to display sprites created in other editors, they can recreate display objects using the information obtained in the controller. Objects used specifically to display certain sprites never need to leave the editor in which they were created.
One of the features that we did not implement was loading a full game into the authoring environment. To do this, we needed to write methods in SpriteView and all other visual display classes to load the object given a Map<String, Object> as a parameter. In other words, given the data parsed from the data file, be able to recreate a sprite or tile or path with that data initialized. I was able to get this done for sprites and tiles, but unable to do so for other components (rounds, waves, levels). If such methods were in place, implementing the loading would be fairly simple. Each editor could have a method that takes in a Map containing all the parts of that editor type in the game, and loop through and load each one into the editor. We could’ve done this fairly easily using the InstanceManager for the allPartsData map, and putting each part into its respective editor based on its type. This design would be closed, and make the assumption that each of the parts in the InstanceManager are correctly formatted and contain all the necessary data for recreating that object in the Authoring Environment. Loading requires reverting the data stored for an element in the environment back to its original object. With how I designed SpriteView and TileView, this loading is simple, but for things like waves and rounds and levels that are compositions of the sprites that the user has created, loading can get quite difficult.
Another feature we didn’t end up implementing was being able to assemble multiple levels into one complete game, and allowing for the user to customize game objectives in the authoring environment. This would be done in the GameEditor class, which right now is just a placeholder class. To implement this feature we would simply obtain the existing level keys from the Controller, display them in some way (slightly minimized) in the GameEditor, and allow the user to order those levels, specify certain objectives, and ultimately complete the creation of a game. This feature would be implemented in accordance with our controller design, and elements of the GameEditor would not need to be accessed anywhere else. This design would be closed to modification, and encapsulated in the GameEditor.
One of the features we did implement was allowing the user to edit various parameters of sprites and elements of the game via Setting objects. This feature required the Setting hierarchy of classes, as well as the ProjectReader to generate the list of settings for a given sprite using annotations in the Game Engine. Setting objects were closed for modification and very open to extension. For example, to add a new type of Setting object (like the BooleanSetting), one simply has to create a new BooleanSetting class that extends Setting, and override the correct methods to save and parse a Boolean parameter. Editable parameters are encapsulated in these objects. Certain assumptions about how these Setting objects will be used are made; for example, StringSetting imposes a 1-25 character limit because I sort of assume that any string the engine needs will not be outside of that length range. Methods could be written to allow someone to adjust those limits, but that wasn’t a particular priority of our design.

Alternate Design

The original design with the SpriteEditor and transferring data with Setting objects worked fairly well, except for how we got list of Settings we needed to generate. Originally we planned on having this list reflect off of a properties file, which would be manually updated when the Engine required new parameter information. This became rather tedious, as the required parameters shifted around. As the project progressed, we realized a better solution would be to use annotations to do this generation. Now, whenever the backend wants an instance of a sprite to be set and editable in the creation of that sprite within the Authoring Environment, all it needs to do is append an annotation to that instance variable. Passing information around the Authoring Environment also evolved. Originally we would have a getter method that would return the list of currently existing sprites (i.e. getTowers() or getProjectiles()), and then parse the information needed from the list returned, but that proved to be rather clunky in that it required certain editors to have instances of all other editors. The controller evolved to solve this problem. Any original API methods that entailed retrieving data in this format were deprecated, replaced with Controller methods and InstanceManager methods for obtaining that data directly in the form of Strings and Java Objects (int, double, boolean, etc.). Objects in the front-end now do not exist in any other place other than the place in which they’re created (TowerView only exists in the TowerEditor).
The Controller design was one of the decisions we made early on in the project when we were struggling to pass data around in the Authoring Environment. An alternative was close to our original idea, where we have a controller class that instead contains an instance of each of the Editor classes so that information required from a different editor can be obtained through the controller. It is a similar design, but is strictly worse than what we have now because keeping a list of Editor objects is holding a lot of extraneous information. Editors really only need 1 or 2 bits of information to display a different kind of sprite. For example, for the Tower, to display a menu-type selection for projectiles to choose from, all you really need is the image associated with those projectiles and their names. Our current design with a Controller keeping just the raw data from the sprites of each editor is, in my opinion, the best possible design for information flow in the Authoring Environment.
Another design decision we made was our method of obtaining the list of parameters from the game engine that would need to be made editable in the authoring environment. Originally, as explained above, we had properties files that we reflected off of for our Setting objects. Our current design, with annotations, is easier for the engine to update, and requires no manual changing of properties files, which our previous design required. It also eliminates the dependency on a properties file, and generates the Setting objects directly from engine code via those annotations. Overall, this is a much cleaner design. One of the drawbacks is that I can’t create Setting objects like PartNameSetting or something else that doesn’t use a Java type, because the annotations use the parameter type in the engine to generate the Setting object. This allows for a little bit less flexibility in the Setting object hierarchy, but the upside in convenience and overall more streamlined code outweighed this slight disadvantage. Plus, there were workarounds for this small setback. I prefer our annotations.
A third design decision we made was in assigning a PartKey (for example, ExampleGame_part1.projectile for a projectile) to each part of the game we created (map, path, tile, tower, projectile, etc.). This allowed for easier storage of data, and eliminated the need to keep references to parts created as file paths, which are lengthy and unwieldy at times. This way also, each new part created in the authoring environment will have a unique key, so that changes to the name (which ultimately results in a change in the filepath) of the part do not affect any method references to that part. This allowed for us to keep parts together in a map using PartKeys, and allow for easier renaming of any part of the game. I prefer this usage of PartKeys to reference a part of the game over something like a filepath or the actual JavaFX object that was created.
I personally believe the Controller class is one of the best features of our project’s current design. It allows for the independence of each editor to create only its own objects, and any information needed is accessible from any location in the authoring environment via this class. The controller also keeps and maintains all data generated through sprite creations and such, and creates an organized map that allows for easy exportability, importability, and really any modification of game parts. I really like how the controller removes the necessity for any component of the authoring environment to have direct knowledge of any other component, and at the same time provides that knowledge for any component that requires it. It doesn’t store instances of actual objects – only data – because the actual objects aren’t needed. This removed the need for extraneous getters and setters, and put all the data for every part in the game in one, organized place. Overall, it made the authoring environment extensible and easy to expand.
Another one of the great features of our project’s current design is our tagging and modifiers idea. One of the most difficult problems to tackle in the Authoring Environment is how to allow the user to specify any sort of interaction between components of the game that he creates. Via tags and modifiers, we give the user very many options and at the same time provide such an easy and usable interface. Tags are used to apply certain modifiers, and adding a tag to an object is as easy as dragging and dropping it on. You can stack tags onto sprites created, and the functionality of the modifiers is limited only by what the engine can support. This was an elegant way to manage interactions between sprites of a game, and made this important part of any Tower Defense Game creation very simple.
Most of our issues with the project and its functionality simply arise from not having done the GamePlayer early enough, and not being able to test code early enough. Functionality and integration was our main problem. I personally think our design was fairly good, albeit some lapses in the AuthoringEnvironment due to misunderstandings. The Controller structure in the AuthoringEnvironment is rather robust, and manages the data in the environment very well. I can’t think of two important issues that remain, other than the fact that we just have to spend the time to implement the features we didn’t get to.

Code Masterpiece
I will write about my Setting object hierarchy for my code masterpiece. These objects provide a visual interface for users to modify certain parameters of a Sprite. IntegerSetting, for example, is an object that prompts the user for an integer as a data value and will display an error if that value is not an integer. The project uses these Setting objects in sprites to allow the user to edit parameters, and populates each particular sprite’s list of settings via reflection and use of annotations in the engine code. This hierarchy elegantly executes an inheritance structure used to create different types of objects that perform the same underlying function: parse a data field and throw an error if there’s an error. Each setting object has a label, which is the parameter’s name, and an error image that can be displayed using the displayErrorAlert method along with a desired error message. The important methods for the purpose of this object hierarchy are, in particular, getParameterName and getParameterValue. The first returns a String, and the second returns an Object. These are the two methods used to assemble a sprite’s parameters into the Map<String,Object> format that the controller uses to store data. Each Setting object that is a subclass of Setting has to override the getParameterValue method to return the appropriate data type (i.e. IntegerSetting returns an Integer, and BooleanSetting returns a Boolean, etc.) so that the Map can be populated correctly. SetParameterValue later became a necessity for when we started to implement loading parts of a game. SetupInteractionLayout and parseField are the two other important methods that are overridden in subclasses of Setting – they are the two methods that set up the interface for users to actually edit the parameter’s value and actually set the value to the new value after it’s been edited, respectively. ParseField is an abstract method because it is required for the parsing of the data contained in any Setting object. Throughout the development of the project, I had to continually add new Setting objects as the backend demanded new types of parameters. Each time, creating a new type of Setting object was so easy because of how the inheritance was set up; all I had to do was create the appropriate class and fill out the appropriate methods. This hierarchy is the definition of closed to modification, open to extension, and demonstrates how elegant and convenient inheritance can make our lives. 