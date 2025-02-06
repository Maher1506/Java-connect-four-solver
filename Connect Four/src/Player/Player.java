package Player;

import Grid.Grid;

public abstract class Player {
    
    private String name;
    private char token;
    private Grid grid;

    public Player(String name, char token, Grid grid) {
        this.name = name;
        this.token = token;
        this.grid = grid;
    }

    public abstract void handleTurn();

    public void display() {
        System.out.println("Name: " + name + " | Token: " + token);
    }

    // setters 
    public void setName(String name) {
        this.name = name;
    }
    public void setToken(char token) {
        this.token = token;
    }
    
    // getters
    public String getName() {
        return name;
    }
    public char getToken() {
        return token;
    }
    public Grid getGrid() {
        return grid;
    }
}
