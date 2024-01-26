import java.util.LinkedList;
import java.util.Random;

public class Game {

    private Grid grid;
    private int number_of_bombs;
    private int rows, columns;

    public Game(){
        this.rows = 10;
        this.columns = 5;
        this.grid = new Grid(this.rows,this.columns);
        System.out.println(this.grid.PrintGrid());

        this.number_of_bombs = this.rows * this.columns - 9;

        PlayGame();
    }

    public void PlayGame(){
        // TODO User selects cells
        // TODO Custom grid sizes and bombs
        // TODO Check if num of bombs < num of possible cells
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

        // Convert 2D grid into a 1D list
        LinkedList<Cell> possible_cells = new LinkedList<>();
        for(int y = 0; y < this.rows; y++){
            for(int x = 0; x < this.columns; x++){
                possible_cells.add(this.grid.GetCell(x,y));
            }
        }

        // Repeat until bomb threshold reached from list of available cells
        while(bombs_planted < this.number_of_bombs){

            // Select random cell to plant bomb
            int ind_random = rand.nextInt(possible_cells.size());
            Cell cell_random = possible_cells.get(ind_random);

            int x_random = cell_random.GetX();
            int y_random = cell_random.GetY();


            // Don't place on first square and adjacent
            // Remove from possible bomb squares
            if(Math.abs(x_random-first_x) <= 1 && Math.abs(y_random-first_y) <= 1){
                possible_cells.remove(ind_random);
                continue;
            }

            this.grid.GetCell(x_random,y_random).SetIsBomb(true); // Plant bomb on selected cell
            possible_cells.remove(ind_random); // Remove from remaining possible cells

            // Update adjacent counters
            for(int x = x_random-1; x <= x_random+1; x++){
                if(x < 0 || x >= this.columns) continue;

                for(int y = y_random-1; y <= y_random+1; y++){
                    if(y < 0 || y >= this.rows) continue;

                    this.grid.GetCell(x,y).IncrementBombsNear();
                }
            }
            bombs_planted += 1; // Update counter
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
