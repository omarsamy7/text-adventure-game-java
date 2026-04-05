package org.uob.a1;

import java.util.Scanner;

public class Game {

    // so this class is the one that controls the whole game the story,map,player,items and puzzles

    private Room[] rooms = new Room[20]; // this is an array to store all rooms in the game which has a maximum capacity of 20
    
    private int roomCount = 0; // this how many rooms that we actually added

    private Map map; // the map object that show visited rooms
    
    private Inventory inventory; // the inventory object which is to store player items
    
    private Score score; // this is to track the player score

    private Position playerPos; // this shows current position of the player on the map
    
    private boolean running; // this is used to keep the game loop running

    // this parts state the flags to remember what the player has done throughout the game
    private boolean chestOpened = false; // true after opening the chest at the Old Oak
    
    private boolean lanternLit = false; // true after lighting the lantern
    
    private boolean ropeTied = false; // true after tying the rope at the Ravine
    
    private boolean runeTaken = false; // true after taking the rune in the cave
    
    private boolean gateOpened = false; // true after using the rune on the Ancient Gate

    // this is the main method which this is where the prgram starts running
    public static void main(String args[]) {
        
        Game game = new Game(); // create a new game object
        
        game.run(); // this starts the game loop
    }

    // the constructor which sets up the inventory, map and score and all the rooms when the game starts
    public Game() {
        
        map = new Map(10, 10); // this create a ten by ten map
        
        inventory = new Inventory(); //this will create an empty inventory
        
        score = new Score(0); // starting score is 0 for the player and then he gains points 
        
        setupWorld(); // this adds all the rooms
        running = true; //tell that the game is running
    }

    // this method is used to create all the rooms in the forest and sets their positions and symbols
    private void setupWorld() {
        
        // x is for column and y is for row
        addRoom(new Room("Camp",
                "You are at your forest camp. Your backpack and a rope lie here.",
                'c', new Position(3, 2)));

        addRoom(new Room("North Trail",
                "A narrow trail leading deeper into the dark forest.",
                't', new Position(3, 3)));

        addRoom(new Room("Old Oak",
                "A huge old oak tree with a mossy chest at its roots.",
                'o', new Position(3, 4)));

        addRoom(new Room("River Bank",
                "A quiet river flows past. You can step east along the rocks.",
                'r', new Position(4, 4)));

        addRoom(new Room("Rocky Ledge",
                "A rocky ledge overlooking the forest.",
                'l', new Position(5, 4)));

        addRoom(new Room("Cave Mouth",
                "A dark cave entrance to the south. It is too dark without a light.",
                'm', new Position(5, 5)));

        addRoom(new Room("Dark Cave",
                "Inside the cave. With light you can see strange carvings and something on the floor.",
                'd', new Position(5, 6)));

        addRoom(new Room("Sunny Clearing",
                "A bright clearing with soft grass. You notice a colourful feather.",
                's', new Position(4, 5)));

        addRoom(new Room("Ravine",
                "A deep ravine blocks the path east. A strong tree is beside the drop.",
                'v', new Position(6, 5)));

        addRoom(new Room("Ancient Gate",
                "An old stone gate covered in vines. There is a leaf-shaped recess in the middle.",
                'g', new Position(7, 5)));

        // starting position is at the Camp
        playerPos = new Position(3, 2);
    }

    // this adds a room to the rooms array and increases roomCount
    private void addRoom(Room room) {
        
        rooms[roomCount] = room;
        roomCount++;
    }

    //this part finds and returns the room at the given position or null if there is no room there
    private Room getRoomAt(Position pos) {
        
        for (int i = 0; i < roomCount; i++) {
            Position p = rooms[i].getPosition();
            if (p.x == pos.x && p.y == pos.y) {
                return rooms[i];
            }
        }
        return null;
    }

    // this is used to check if there is a room at the given position
    private boolean roomExistsAt(Position pos) {
        
        return getRoomAt(pos) != null;
    }

    // this part is responsible for the main game loop it shows room, read the input and handles the command given by the player 
    private void run() {
        
        Scanner scanner = new Scanner(System.in);

        Room startRoom = getRoomAt(playerPos);
        map.placeRoom(startRoom.getPosition(), startRoom.getSymbol());
        score.visitRoom(); // this is for rhe first room visited

        println(startRoom.getDescription());

        while (running) {
            print(">> "); // this line means to print but don't go to the next line yet it is a prompt which tells the plyer it is your turn 
            String input = scanner.nextLine();

            if (input == null) {
                running = false;
            } else {
                handleCommand(input.trim().toLowerCase());
            }
        }

        scanner.close();
        println("Game over");
    }

    // this part reads the player command and decides what to do next
    private void handleCommand(String cmd) {

        // this is for basic commands with no extra text
        if (cmd.equals("quit")) {
            running = false;
            return;
        }

        if (cmd.equals("help")) {
            println("Valid commands: look, move <direction>, look <feature>, look <item>, inventory, map, score, quit");
            println("Extra: take rope, open chest, take lantern, light lantern, take rune, tie rope, take feather, use rune on gate");
            return;
        }

        if (cmd.equals("inventory")) {
            String items = inventory.displayInventory();
            if (items.length() == 0) {
                println("You have:");
            } else {
                println("You have: " + items);
            }
            return;
        }

        if (cmd.equals("map")) {
            print(map.display());
            return;
        }

        if (cmd.equals("score")) {
            println("SCORE: " + score.getScore());
            return;
        }

        if (cmd.equals("look")) {
            println(currentRoomDescription());
            return;
        }

        // this for commands that start with a keyword and have something that comes after it

        if (startsWith(cmd, "move ")) {
            
            String dir = cmd.substring(5); // get the direction 
            move(dir);
            return;
        }

        if (startsWith(cmd, "look ")) {
            
            String what = cmd.substring(5); // get what the player wants to look at
            lookAt(what);
            return;
        }

        // the next part is for the puzzle and item commands

        if (cmd.equals("take rope")) {
            takeRope();
            return;
        }

        if (cmd.equals("open chest")) {
            openChest();
            return;
        }

        if (cmd.equals("take lantern")) {
            takeLantern();
            return;
        }

        if (cmd.equals("light lantern")) {
            lightLantern();
            return;
        }

        if (cmd.equals("take rune")) {
            takeRune();
            return;
        }

        if (cmd.equals("tie rope")) {
            tieRope();
            return;
        }

        if (cmd.equals("take feather")) {
            takeFeather();
            return;
        }

        if (cmd.equals("use rune on gate")) {
            useRuneOnGate();
            return;
        }

        println("I don't understand that command. Type 'help' to see options.");
    }

    // this is a small help to check if a string starts with another string meaning if a string starts with another word
    private boolean startsWith(String text, String prefix) {
        
        if (text.length() < prefix.length()) {
            return false;
        }
        return text.substring(0, prefix.length()).equals(prefix);
    }

    // this handles movement and checks simple rules
    private void move(String direction) {
        int dx = 0;
        int dy = 0;

        if (direction.equals("north")) {
            dy = -1;
        } else if (direction.equals("south")) {
            dy = 1;
        } else if (direction.equals("west")) {
            dx = -1;
        } else if (direction.equals("east")) {
            dx = 1;
        } else {
            println("Unknown direction.");
            return;
        }

        Position next = new Position(playerPos.x + dx, playerPos.y + dy);

        // make sure the player doesn't enters the  Dark Cave without light like block him from that 
        if (playerPos.x == 5 && playerPos.y == 5 && dx == 0 && dy == 1 && !lanternLit) {
            
            println("It is too dark to enter the cave. You need to light a lantern.");
            return;
        }

        //  makes sure the player doesn't cross the ravine if the rope isn't tied 
        if (playerPos.x == 6 && playerPos.y == 5 && dx == 1 && dy == 0 && !ropeTied) {
            
            println("The ravine is too dangerous to cross without a rope.");
            return;
        }

        if (!roomExistsAt(next)) {
            println("You can't go that way.");
            return;
        }

        playerPos = next;
        Room current = getRoomAt(playerPos);

        println("You go " + direction + " to " + current.getName() + ".");
        map.placeRoom(current.getPosition(), current.getSymbol());
        score.visitRoom();
    }

    // this part is for when the player say look something it decides what to show him
    private void lookAt(String what) {
        
        // this describes the item for the player if he has the item
        if (inventory.hasItem(what) != -1) {
            if (what.equals("rope")) {
                println("A strong rope. Useful for crossing dangerous places.");
                
            } else if (what.equals("lantern")) {
                println("An old lantern. When lit, it lights up dark areas.");
            } else if (what.equals("rune")) {
                println("A small stone with a leaf symbol. It feels important.");
                
            } else if (what.equals("feather")) {
                println("A colourful feather from a forest bird.");
                
            } else {
                println("It's just a " + what + ".");
            }
            return;
        }

        // this checks the room features and what does it have inside
        Room r = getRoomAt(playerPos);
        String name = r.getName();

        if (name.equals("Old Oak") && (what.equals("chest") || what.equals("mossy chest"))) {
            
            if (chestOpened) {
                println("The chest is open and empty now.");
                
            } else {
                println("A mossy chest sits at the roots. It looks like it could be opened.");
                
            }
        } else if (name.equals("Cave Mouth") && (what.equals("cave") || what.equals("entrance"))) {
            
            if (lanternLit) {
                println("With your lantern lit, the cave looks safe enough to enter.");
                
            } else {
                println("The cave is completely dark. You cannot see inside.");
                
            }
        } else if (name.equals("Ravine") && (what.equals("tree") || what.equals("trunk"))) {
            
            println("A sturdy tree stands at the edge. You could tie a rope here.");
            
        } else if (name.equals("Ancient Gate") && (what.equals("gate") || what.equals("recess"))) {
            
            if (gateOpened) {
                println("The gate is open, showing the path out of the forest.");
                
            } else {
                println("A stone gate with a leaf-shaped recess. It needs something to unlock it.");
                
            }
        } else {
            println("You see nothing special about that.");
        }
    }

    // pick up rope at Camp
    private void takeRope() {
        if (!inRoom("Camp")) {
            println("There is no rope here.");
            return;
        }
        if (inventory.hasItem("rope") != -1) {
            println("You already took the rope.");
            return;
        }
        inventory.addItem("rope");
        println("You take the rope from your camp.");
    }

    // open chest at Old Oak
    private void openChest() {
        if (!inRoom("Old Oak")) {
            println("There is no chest here.");
            return;
        }
        if (chestOpened) {
            println("The chest is already open.");
            return;
        }
        chestOpened = true;
        println("You open the chest. Inside you find an old lantern.");
    }

    // take lantern from opened chest
    private void takeLantern() {
        if (!inRoom("Old Oak")) {
            println("There is no lantern here.");
            return;
        }
        if (!chestOpened) {
            println("The lantern is still locked inside the chest.");
            return;
        }
        if (inventory.hasItem("lantern") != -1) {
            println("You already have the lantern.");
            return;
        }
        inventory.addItem("lantern");
        println("You take the lantern.");
    }

    // light the lantern which is puzzle one
    private void lightLantern() {
        if (inventory.hasItem("lantern") == -1) {
            println("You don't have a lantern to light.");
            return;
        }
        if (lanternLit) {
            println("The lantern is already lit.");
            return;
        }
        lanternLit = true;
        score.solvePuzzle();
        println("You light the lantern. Now you can safely explore dark places.");
    }

    // take rune in Dark Cave
    private void takeRune() {
        if (!inRoom("Dark Cave")) {
            println("There is no rune here.");
            return;
        }
        if (inventory.hasItem("rune") != -1) {
            println("You already picked up the rune.");
            return;
        }
        inventory.addItem("rune");
        runeTaken = true;
        println("You pick up the carved rune stone.");
    }

    // tie rope at Ravine which is puzzle two
    private void tieRope() {
        if (!inRoom("Ravine")) {
            println("There is nowhere to tie a rope here.");
            return;
        }
        if (inventory.hasItem("rope") == -1) {
            println("You need a rope to do that.");
            return;
        }
        if (ropeTied) {
            println("The rope is already tied here.");
            return;
        }
        ropeTied = true;
        score.solvePuzzle();
        println("You tie the rope to the tree and across the ravine. You can now cross safely.");
    }

    // optional extra item at Sunny Clearing
    private void takeFeather() {
        if (!inRoom("Sunny Clearing")) {
            println("There is no feather here.");
            return;
        }
        if (inventory.hasItem("feather") != -1) {
            println("You already took the feather.");
            return;
        }
        inventory.addItem("feather");
        println("You pick up the colourful feather.");
    }

    // this is the final part where the player do the last action which is using the rune on the the ancient gate to escape 
    private void useRuneOnGate() {
        if (!inRoom("Ancient Gate")) {
            println("There is no ancient gate here.");
            return;
        }
        if (inventory.hasItem("rune") == -1) {
            println("You need a rune stone to unlock the gate.");
            return;
        }
        if (gateOpened) {
            println("The gate is already open. You are free to leave.");
            return;
        }
        gateOpened = true;
        println("You place the rune into the recess. The gate rumbles open.\nYou have escaped the forest!");
        running = false; // this parts end the game after winning the game
    }

    // checks if the player is in a specific room by its name 
    private boolean inRoom(String roomName) {
        Room r = getRoomAt(playerPos);
        return r != null && r.getName().equals(roomName);
    }

    // tells back the player the description of the room he is currently in and also the changes from puzles 
    private String currentRoomDescription() {
        Room r = getRoomAt(playerPos);
        String name = r.getName();

        if (name.equals("Old Oak")) {
            if (chestOpened) {
                return "You are at the Old Oak. The chest here is open and empty.";
            }
            return "You are at the Old Oak. A closed mossy chest sits at its roots.";
        }

        if (name.equals("Cave Mouth")) {
            if (lanternLit) {
                return "You are at the cave mouth. Your lantern light makes it safe to go inside.";
            }
            return "You are at the cave mouth. It is too dark to see inside.";
        }

        if (name.equals("Dark Cave")) {
            return "You are inside the Dark Cave. Carvings glow faintly on the walls. A rune lies on the floor.";
        }

        if (name.equals("Ravine")) {
            if (ropeTied) {
                return "You are at the ravine. A rope is tied across, letting you cross safely.";
            }
            return "You are at the ravine. It is too wide to cross. A tree stands nearby.";
        }

        if (name.equals("Sunny Clearing")) {
            if (inventory.hasItem("feather") != -1) {
                return "You are in the Sunny Clearing. The grass is soft and calm.";
            }
            return "You are in the Sunny Clearing. A bright feather lies in the grass.";
        }

        if (name.equals("Ancient Gate")) {
            if (gateOpened) {
                return "You stand by the Ancient Gate. It is open, showing the path to safety.";
            }
            return "You stand before the Ancient Gate. The leaf-shaped recess is still empty.";
        }

        // if there is no change in the room it tells the player the normall description for the room 
        return r.getDescription();
    }

    // These two small methods help print text on the screen more easily without typing long each time
    private void println(String text) {
        System.out.println(text);
    }

    private void print(String text) {
        System.out.print(text);
    }
}
