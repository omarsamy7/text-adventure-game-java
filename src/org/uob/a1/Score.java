package org.uob.a1;

public class Score {
    // the next part is to keep track of the player score , rooms he entered and puzzles solved 

    private int startingScore; // this is for the score at the start of the game
    
    private int roomsVisited; // how many rooms the player have entered
    
    private int puzzlesSolved; // this is for how many puzzles the player solved
    
    private final int PUZZLE_VALUE = 10; // player pointss added for every puzzle solved

// the constructor which sets the starting score and resets counters
    public Score(int startingScore) {
        
        this.startingScore = startingScore;
        this.roomsVisited = 0;
        this.puzzlesSolved = 0;
    }
// this add one every time the player visits a new room
    public void visitRoom() {
        
        roomsVisited++;
    }
// this adds one for each time the player solves a puzzle 
    public void solvePuzzle() {
        
        puzzlesSolved++;
    }

    // this part calculate out  the total score using the game's formula 
    // the formula is startingScore + roomsVisited + (puzzlesSolved * PUZZLE_VALUE)
    public double getScore() {
        
        return startingScore + roomsVisited + (puzzlesSolved * PUZZLE_VALUE);
    }
}
