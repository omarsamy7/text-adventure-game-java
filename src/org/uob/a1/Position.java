package org.uob.a1;

public class Position {
    // this class is used to store the location of the room on the map
    // it uses x and y to tell where each room is placed on the map
   
    public int x; // this is the horizontal position from left to right
    
    public int y; // this is the vertical position from top to bottom

    //this is the constructor  which sets the x and y position for the rooms 
    public Position(int x, int y) {
        
        this.x = x;
        this.y = y;
    }
}
