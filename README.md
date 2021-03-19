# JavaMSTGenerator
Creates a minimum spanning tree using a bespoke algorithm (not primms or kruskals). It works by keeping track of which vertexes are in specific compoenents through the use of the ComponentRep int array, 
The position represents the vertex and the value represents the representative (the identity of the compoenent). When we add edges, we merge components. 
We also make sure no two vertexes we are adding are in the same Component, through the int array.

# How to run:
1. Download files into one package, 
2. Use IDE or compile on command line through javac 
3. Run the code and edit it to change the number of different nodes in the maze, It can be different values for height and width.


