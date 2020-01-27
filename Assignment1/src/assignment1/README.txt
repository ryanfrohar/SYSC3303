SYSC 3303 Assignment 1
Ryan Frohar
101029053

README

FILES: 
-AgentThread.java: randomizes two items from the Ingredient.java class and "puts" them on the table by updating the array in table.java each time.
-ChefThread.java: creates a sandwich if the two ingredients currently on the table are enough to create a sandwich for the respective chef.
-Ingredient.java: enumeration class with jam, peanut butter and bread.
-Table.java: consists of synchronized methods that are invoked on by the AgentThread and three ChefThreads
-Main.java: creates a table object, agent object and three chef objects and invokes the start method (classes are thread implementations)

SETUP: 
-Compile files
-Run Main.java
-View Console output
