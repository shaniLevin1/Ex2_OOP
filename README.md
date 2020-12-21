# README- Ex2 OOP Shani Levin- Batya Ashkenazi  
![alt text](https://i.ibb.co/3pqvWVr/Whats-App-Image-2020-12-21-at-22-34-18.jpg)


our project  contains two parts:implemention of weighted directed graph with all its properties, and game algorithm functions in order to make the game move smartly.
## part 1:api
### classes:
#### Node_Data:
This class implements node_data
description:
we build graph using nodes (vertices) and edges between them. any node has its information as: tag situation, type, weight of the node, location in the graph and unique id\key associated with that node.

#### Edge_Data:
This class implements edge_data.
description:
represents an edge in weighted directed graph. any edge has source and destination, weight, tag, and information.

##### DWGraph_DS:
.This class  implements directed_weighted_graph
description:
Represent a directed weighted graph and the characteristics.
Contains main functions like:
Connect-make an edge between two nodes.
RemoveNode-remove a node from the graph
RemoveEdge-remove an edge from the graph
AddNode-add node to the graph
 
#### DWGraph_Algo:

README BATYA ASHKENAZI-SHANI LEVIN

our project uses weighted directed graph with all it properties.

we can check if two nodes share an edge between them, if a specific node exists in the graph, we also can do operation as connect two nodes, update an edge's weight and  also remove an edge .

We represent the algorithms in a directed weighted graph:
description:
in that class we define all of the operations that we can check on a graph- as : check if the graph is connected, check if two nodes share an edge between them,  check what is the shortest path between two given nodes (using DFS algorithm's idea).
in addition- we can save a JSON file and load JSON file from thike computer.
Contains main functions like:
Copy-deep copy of the given graph

isConnected method -checks if the graph is connected.

shortestPathDist - checks what is the shortest path between two nodes in the graph, by DFS algorithm. DFS algorithm takes each node in the graph and saves the way to all of the other nodes , 
using a deep search through the neighbors. the method calls itself recursively each junction it visits and updates the shortest distance with the ascociated with the current node.

shortestPath - checks what is the shortest path between two nodes in the graph, by DFS algorithm and saves the actual pattern. DFS algorithm takes each node in the graph and saves the way to all of the other nodes , 
using a deep search through the neighbors. the method calls itself recursively each junction it visits and updates the shortest path with the ascociated current node.

save/load-save and load the graph by using json string.
part 2:gameClient
### classes:
CL_Agent- This class represents an agent.
description:
In every level in the game we have different number of agents, who should eat the pokemons.
The grade depands on how much pokemons the agents eat in this level and the value of the those pokemons.
Every agent has a pokemon he need to eat and it depands on the ratio between the value of the pokemon and the weight of the edges.

##### CL_Pokemon- This class represents a pokemon.
Every pokemon has a value, type, and more characteristics.
 
#### Arena- This class represents the arena of the game, which contains agents, pokemons and the game graph.
The Arena is the class that store all the information of the current level.


