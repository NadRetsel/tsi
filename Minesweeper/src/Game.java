import java.util.Random;

public class Game {

    private Grid grid;
    private int number_of_bombs;
    private int rows, columns;

    public Game(){
        this.rows = 8;
        this.columns = 16;
        this.grid = new Grid(this.rows,this.columns);
        System.out.println(this.grid.PrintGrid());

        this.number_of_bombs = 16;

        PlayGame();
    }

    public void PlayGame(){
        // TODO User selects cells
        // TODO Custom grid sizes and bombs
        // TODO Flagging
        PopulateGrid(4,5);
        System.out.println(this.grid.PrintGrid());
        UpdateGrid(4,5);
        System.out.println(this.grid.PrintGrid());

    }



    // Randomly plant bombs and update adjacent 'bombs_near' counters
    public void PopulateGrid(int first_x, int first_y){
        int bombs_planted = 0;
        Random rand = new Random();

        // Repeat until bomb threshold reached
        // TODO Better if list of possible squares used instead of loop until threshold reached
        while(bombs_planted < this.number_of_bombs){
            int random_x = rand.nextInt(this.columns);
            int random_y = rand.nextInt(this.rows);


            if(this.grid.GetCell(random_x,random_y).GetIsBomb()) continue; // Don't place on already existing bomb
            if(Math.abs(random_x-first_x) <= 1 && Math.abs(random_y-first_y) <= 1) continue; // Don't place on first square and adjacent

            this.grid.GetCell(random_x,random_y).SetIsBomb(true);

            // Update adjacent counters
            for(int x = random_x-1; x <= random_x+1; x++){
                if(x < 0 || x >= this.columns) continue;

                for(int y = random_y-1; y <= random_y+1; y++){
                    if(y < 0 || y >= this.rows) continue;

                    this.grid.GetCell(x,y).IncrementBombsNear();
                }
            }
            bombs_planted += 1;
        }
    }

    public void UpdateGrid(int selected_x, int selected_y){
        Cell cell_selected = this.grid.GetCell(selected_x, selected_y);

        // TODO If selected square = flagged -> confirm with user

        // TODO If selected square = bomb -> end game
        if(cell_selected.GetIsBomb()){
            // TODO END GAME
        }

        // Reveal selected and adjacent cells if not bomb or flagged
        if(cell_selected.GetBombsNear() >= 0) this.grid.RevealAdjacentCells(selected_x, selected_y);

    }
}
