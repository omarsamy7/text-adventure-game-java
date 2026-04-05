package org.uob.a1;

public class Map {
    // this class is used for to create and show the game on the map
    // the map is made of a symbols that shows the room the player visited

    private char[][] grid; // this is a 2d array that stores the map and the rooms symbols
    
    private int width; // this is how many columns x the map has and goes left to right
    
    private int height; // this shows the rows y the map has that goes from top to bottom
    
    final private char EMPTY = '-';  //  this is for the symbol used for the empty spaces on the map

  // the constructor sets the map size and fills it with empty dots it is called when a new map is made it sets how big the map is width and height
    //and it fills all the spots with dashes "-" to show empty spaces
    public Map(int width, int height) {
        
        this.width = width;
        this.height = height;
        grid = new char[height][width];

     // // this is a double loop that goes through every row and column in the map and fills each spot with a dash to show an empty space
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                grid[r][c] = EMPTY; // fills this grid cell with the empty symbol which is a dash 
            }
        }
    }
 // this method puts a room symbol on the map using its position 
    public void placeRoom(Position pos, char symbol) {
        
        // this checks that the room position is inside the map before placing it
        if (pos != null &&
            pos.y >= 0 && pos.y < height &&
            pos.x >= 0 && pos.x < width) {
            grid[pos.y][pos.x] = symbol;
        }
    }

    // this method shows the map as a text on the screen for the player
    public String display() {
        String out = "";
        
        // this goes through all rows to build the full map
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                out += grid[r][c];
            }
            out += "\n"; // this make a new line for each row
        }
        return out;
    }
}
