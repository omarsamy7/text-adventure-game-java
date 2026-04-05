package org.uob.a1;

public class Room {
    // this is a class which is used for creating rooms in the game
    // so each room has a name and it's description and a map symbol and a position (x,y) 

    private String name;  // the name of the room like dark cave or camp
    
    private String description;  // this gives a text describing what is inside the room the player is in
    
    private char symbol;  // this is a one leter symbol that shows the room on the map and each each room is assigned a symbol
    
    private Position position;  // where the room is placed on the map/grid ysing x and y whichis from position class

    //the constructor here is used to set up the room with its name , description and a symbol and a position 
    public Room(String name, String description, char symbol, Position position) {
        
        this.name = name;
        this.description = description;
        this.symbol = symbol;
        this.position = position;
    }
//this part returns the room name when needed for the player
    public String getName() {
        
        return name;
    }
    
//this part returns back the room description for the player
    public String getDescription() {
        
        return description;
    }
    
// this returns the symbol of the room n the map
    public char getSymbol() {
        
        return symbol;
    }
    
// this returns the position of the room for the player on the map
    public Position getPosition() {
        
        return position;
    }
}
