public class Cell {

    private boolean is_bomb, is_flagged, is_revealed;
    private int bombs_near, x,y;

    public Cell(int x, int y){
        this.x              = x;
        this.y              = y;

        this.is_bomb        = false;
        this.is_flagged     = false;
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
    public int GetBombsNear(){
        return this.bombs_near;
    }

    public void SetIsBomb(boolean is_bomb){
        this.is_bomb = is_bomb;
    }
    public void SetBombsNear(int bombs_near){
        this.bombs_near = bombs_near;
    }
    public void SetIsRevealed(boolean is_revealed){
        this.is_revealed = is_revealed;
    }
    public void IncrementBombsNear(){
        this.bombs_near += 1;
    }




}
