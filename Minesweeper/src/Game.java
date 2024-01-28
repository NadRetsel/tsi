import java.util.LinkedList;
import java.util.Random;

public class Game {
    private InputHandler input_handler = new InputHandler();
    private Grid grid;
    private int rows, columns, number_of_bombs, flag_count, mark_count;
    private boolean first_move, game_in_progress;

    public Game(){
        this.rows = 8;
        this.columns = 16;
        this.grid = new Grid(this.rows,this.columns);
        this.first_move = true;

        this.number_of_bombs = (int) (this.rows * this.columns * 0.25);
        this.flag_count = 0;
        this.mark_count = 0;

        System.out.println(this.grid.GridString());


        PlayGame();
    }

    // TODO User select grid or custom
    // TODO User select difficulty or custom
    // TODO Check if num of bombs < num of possible cells
    public void SetupGame(){

    }
    public void PlayGame(){

        // TODO Custom grid sizes and bombs
        // TODO End game

        this.game_in_progress = true;
        String[] menu_options = {"SELECT", "FLAG/UNFLAG", "MARK/UNMARK"};
        while(game_in_progress){
            int menu_input = SelectAction(menu_options);

            int[] coords = SelectCoords();
            Cell cell_selected = this.grid.GetCell(coords[0], coords[1]);
            int coord_x = coords[0];
            int coord_y = coords[1];

            System.out.println(menu_input);
            switch(menu_input){
                case 0 -> RevealCell(cell_selected); // REVEALING
                case 1 -> FlagCell(cell_selected); // FLAGGING
                case 2 -> MarkCell(cell_selected); // MARKING

            }

            System.out.println(this.grid.GridString());


        }


    }

    public int SelectAction(String[] menu_options){
        int menu_input = -1;
        boolean menu_confirm = false;
        while(!menu_confirm) {
            menu_input = input_handler.InputInteger("Would you like to..." +
                            "\n0 - REVEAL " +
                            "\n1 - FLAG/UNFLAG to indicate bomb" +
                            "\n2 - MARK/UNMARK to place a question mark" +
                            "\nPlease select [0-2]",
                    0, 2);

            // Confirm menu selection
            String[] options = {"Y", "N"};
            String confirm_input = input_handler.InputMenu(("You have chosen to " + menu_options[menu_input] + ". Is this correct? [Y/N]"),
                    options);

            if(confirm_input.equals("Y")) menu_confirm = true;
        }
        return menu_input;
    }
    public int[] SelectCoords(){
        int input_x = -1;
        int input_y = -1;
        boolean coord_confirm = false;
        while(!coord_confirm) {
            input_x = input_handler.InputInteger("Enter your x-coordinate: ", 1, this.columns) - 1;
            input_y = input_handler.InputInteger("Enter your y-coordinate: ", 1, this.rows) - 1;

            String[] options = {"Y", "N"};
            String confirm_input = input_handler.InputMenu(("You have chosen (" + (input_x+1) + "," + (input_y+1) +"). Is this correct? [Y/N]"), options);

            if(confirm_input.equals("Y")) coord_confirm = true;
        }
        return new int[]{input_x, input_y};
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
            int random_ind = rand.nextInt(possible_cells.size());
            Cell random_cell = possible_cells.get(random_ind);

            int random_x = random_cell.GetX();
            int random_y = random_cell.GetY();


            // Don't place on first square and adjacent
            // Remove from possible bomb squares
            if(Math.abs(random_x-first_x) <= 1 && Math.abs(random_y-first_y) <= 1){
                possible_cells.remove(random_ind);
                continue;
            }

            random_cell.SetIsBomb(true); // Plant bomb on selected cell
            possible_cells.remove(random_ind); // Remove from remaining possible cells

            // Update adjacent counters
            for(int x = random_x-1; x <= random_x+1; x++){
                if(x < 0 || x >= this.columns) continue;

                for(int y = random_y-1; y <= random_y+1; y++){
                    if(y < 0 || y >= this.rows) continue;

                    this.grid.GetCell(x,y).IncrementBombsNear();
                }
            }
            bombs_planted += 1; // Update counter
        }
    }


    public void RevealCell(Cell cell_selected){

        // Populate grid with bombs if on first move
        if(this.first_move){
            PopulateGrid(cell_selected.GetX(), cell_selected.GetY());
            this.first_move = false;
        }

        // Cell is already revealed -> Do nothing
        if(cell_selected.GetIsRevealed()){
            System.out.println("Already revealed.");
            return;
        }

        // Cell is already flagged -> Do nothing
        if(cell_selected.GetIsFlagged()){
            System.out.println("Cannot reveal. Cell is flagged.");
            return;
        }

        // Cell is already marked -> Do nothing
        if(cell_selected.GetIsMarked()){
            System.out.println("Cannot reveal. Cell is marked.");
            return;
        }

        // TODO If selected square = bomb -> end game
        if(cell_selected.GetIsBomb()){
            // TODO END GAME
            System.out.println("Bomb exploded. GAME OVER.");
            return;

        }

        // Reveal selected and adjacent cells if not bomb or flagged
        if(cell_selected.GetBombsNear() >= 0) this.grid.RevealAdjacentCells(cell_selected);

    }
    public void FlagCell(Cell cell_selected){

        // Cell is already revealed -> Do nothing
        if(cell_selected.GetIsRevealed()){
            System.out.println("Cannot flag cell. Already revealed.");
            return;
        }

        // Cell is already flagged -> Unflag
        if(cell_selected.GetIsFlagged()){
            System.out.println("Unflagging cell.");
            cell_selected.SetIsFlagged(false);
            this.flag_count -= 1;
            return;
        }

        // Cell is not yet flagged -> Check if maximum flags -> Flag and unmark if needed
        if(!cell_selected.GetIsFlagged()){
            if(this.flag_count >= this.number_of_bombs){
                System.out.println("Cannot flag cell. Maximum number of flags reached. Unflag another flagged cell first.");
                return;
            }

            System.out.println("Flagging cell.");
            cell_selected.SetIsFlagged(true);
            cell_selected.SetIsMarked(false);
            this.flag_count += 1;
            return;
        }

        System.out.println("Shouldn't be here... (FLAGGING)");
    }
    public void MarkCell(Cell cell_selected){

        // Cell is already revealed -> Do nothing
        if(cell_selected.GetIsRevealed()){
            System.out.println("Cannot mark cell. Already revealed.");
            return;
        }

        // Cell is already marked -> Unmark
        if(cell_selected.GetIsMarked()){
            System.out.println("Unmarking cell.");
            cell_selected.SetIsMarked(false);
            return;
        }

        // Cell is not yet marked -> Mark and automatically unflag
        if(!cell_selected.GetIsMarked()){
            System.out.println("Marking cell.");
            cell_selected.SetIsMarked(true);
            cell_selected.SetIsFlagged(false);
            this.flag_count -= 1;
            return;
        }

        System.out.println("Shouldn't be here... (MARKING)");
    }

}
