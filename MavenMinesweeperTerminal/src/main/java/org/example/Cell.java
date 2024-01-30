package org.example;

public class Cell {
    private final int x, y;

    private boolean is_bomb, is_flagged, is_marked, is_revealed;
    private int bombs_near;

    public Cell(int x, int y){
        this.x              = x;
        this.y              = y;

        this.is_bomb        = false;
        this.is_flagged     = false;
        this.is_marked      = false;
        this.is_revealed    = false;
        this.bombs_near     = 0;
    }
    public int GetX(){
        return this.x;
    }
    public int GetY(){
        return this.y;
    }
    public boolean GetIsBomb(){
        return this.is_bomb;
    }
    public boolean GetIsRevealed() {
        return this.is_revealed;
    }
    public boolean GetIsFlagged(){
        return this.is_flagged;
    }
    public boolean GetIsMarked(){
        return this.is_marked;
    }
    public int GetBombsNear(){
        return this.bombs_near;
    }

    public void SetIsBomb(boolean is_bomb){
        this.is_bomb = is_bomb;
    }
    public void SetIsRevealed(boolean is_revealed){
        this.is_revealed = is_revealed;
    }
    public void SetIsFlagged(boolean is_flagged){
        this.is_flagged = is_flagged;
    }
    public void SetIsMarked(boolean is_marked){
        this.is_marked = is_marked;
    }
    public void IncrementBombsNear(){
        ++this.bombs_near;
    }




}
