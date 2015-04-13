Use Cases
===

Engine Cases
---
1. The player clicks on a tower and upgrades it with money/exp, giving it new functionality.
2. The player earns experience from killing enemies
3. An enemy reaches the goal area, causing the player to lose the game.
4. The player places a tower.
5. A tower is placed that gives stat bonuses to nearby towers
6. The user kills all enemies in a wave, triggering a new wave begins.
7. The player starts a new game and places towers until clicking the start button.
8. The player surrenders.
9. An enemy is spawned and finds its way to the goal.
10. The player places tacks/bombs on the track to help towers (tacks can help to reduce enemy HP or kill enemies)
11. A tower spawns defenders that will attack enemy units
12. A player launches a superattack from a capable tower
13. The player uses a global power
14. The player saves their progress in the game
15. The player loads their progress into the game
16. A player sells upgrades/towers
17. An enemy comes in range of a specific tower.
18. An enemy reaches the goal area.
19. The player attempts to place a tower in an unplaceable area, but will not be allowed to. 
20. The player starts a new wave before the last wave has finished (new units will appear before all units from the previous wave have been killed).
21. The player upgrades their abilities in the shop after the round ends.
22. The player changes the speed of gameplay
23. The player changes targeting priority of a tower (ie. to target different units)

Editor Cases
---
24. User creates a new base tower type
25. User changes a tower’s parameters (image, HP, Damage, Range, Accuracy, Selling %, Cost, Projectile)
26. User changes a projectile’s parameters (image, reduction amount, enemy property affected, crits, type)
27. User defines an upgrade hierarchy for a tower
28. User places a tower on the map
29. User creates a new base enemy type
30. User changes a enemy’s parameters (image, HP, damage, type, movement speed, path followed)
31. User selects custom or automatic pathing for enemies
32. User draws a path that his enemies can follow
33. User picks editing or playing from the splash screen
34. User creates a new ability item
35. User adds an upgraded tower to the tower hierarchy
36. User loads a data file to continue editing a game
37. User saves the game (into a data file)
38. User defines multiple levels with specific quantities and types of enemies for each level
39. User creates enemies with default parameters
40. User defines player’s starting resources
41. User defines super-attacks for a tower
42. User sets enemy spawn points
43. User assigns enemies their spawn points
44. User assigns enemies the path they will follow

Second Sprint Implementation Use Cases
User creates a new wave
User uses previously saved wave in creation of a new wave
User adds a unit to the wave
User determines time delay between units/waves in a wave
User sets background image for map
User sets grid size of map
User sets tile size of the tiles that make up the grid of the map
User sets tile color
User names a new game
User determines how many lives the player has in a game
User adds a description to the game
User defines a new tile type for the Map
User sets towers to only be set on certain types of tiles
User goes back to menu from inside the authoring environment
User deletes a map
User dictates a unit as friendly
User changes lose condition for player
User creates paths on his map
User assigns waves to those paths
User builds a round out of waves
User builds a level out of rounds
User specifies what happens in between rounds 
User adds level to tower hierarchy
User specifies changes of this hierarchy level
User recycles this hierarchy on another tower
User creates a new item
User updates item’s parameters
