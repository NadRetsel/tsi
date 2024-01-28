import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Scanner reader = new Scanner(System.in);
    private Grid grid;
    private int rows, columns, number_of_bombs, flag_count, mark_count;
    private boolean first_move;

    public Game(){
        this.rows = 8;
        this.columns = 16;
        this.grid = new Grid(this.rows,this.columns);
        this.first_move = true;

        this.number_of_bombs = (int) (this.rows * this.columns * 0.25);
        this.flag_count = 0;
        this.mark_count = 0;

        System.out.println(this.grid.PrintGrid());


        PlayGame();
    }

    // TODO User select grid or custom
    // TODO User select difficulty or custom
    // TODO Check if num of bombs < num of possible cells
    public void SetupGame(){

    }
    public void PlayGame(){

        // TODO User selects cells
        // TODO Custom grid sizes and bombs

        // TODO Flagging



        boolean game_in_progress = true;
        String[] menu_options = {"SELECT", "FLAG/UNFLAG", "MARK/UNMARK"};
        do{

            // Menu select
            String menu_select = "";
            boolean menu_confirm = false;
            while(!menu_confirm) {
                menu_select = menu_options[InputIntegerRange("Would you like to..." +
                                "\n0 - SELECT to reveal" +
                                "\n1 - FLAG/UNFLAG to indicate bomb" +
                                "\n2 - MARK/UNMARK to place a question mark" +
                                "\nPlease select [0-2]",
                        0, 2)];
                // TODO Confirm user input
                String[] options = {"Y", "N"};
                String confirm_input = InputMenu(("You have chosen to " + menu_select + ". Is this correct? [Y/N]"), options);

                if(confirm_input.equals("Y")) menu_confirm = true;
            }

            // Coord cell select
            int input_x = -1;
            int input_y = -1;
            boolean coord_confirm = false;
            while(!coord_confirm) {
                input_x = InputIntegerRange("Enter your x-coordinate: ", 1, this.columns) - 1;
                input_y = InputIntegerRange("Enter your y-coordinate: ", 1, this.rows) - 1;

                String[] options = {"Y", "N"};
                String confirm_input = InputMenu(("You have chosen (" + (input_x+1) + "," + (input_y+1) +"). Is this correct? [Y/N]"), options);

                if(confirm_input.equals("Y")) coord_confirm = true;
            }

            System.out.println(menu_select);
            switch(menu_select){
                case "SELECT" ->  SelectCell(input_x,input_y);
                case "FLAG/UNFLAG" -> FlagCell(input_x, input_y, this.grid.GetCell(input_x, input_y));
                case "MARK/UNMARK" -> MarkCell(input_x, input_y);

            }

            System.out.println(this.grid.PrintGrid());


        } while(game_in_progress);


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

    // Update grid after user selects a cell
    public void SelectCell(int input_x, int input_y){

        // Populate grid with bombs if on first move
        if(this.first_move){
            PopulateGrid(input_x, input_y);
            this.first_move = false;
        }

        Cell cell_selected = this.grid.GetCell(input_x, input_y);

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
        if(cell_selected.GetBombsNear() >= 0) this.grid.RevealAdjacentCells(input_x, input_y);

    }
    public void FlagCell(int input_x, int input_y, Cell cell_selected){

        //Cell cell_selected = this.grid.GetCell(input_x, input_y);

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
    }
    public void MarkCell(int input_x, int input_y){
        Cell cell_selected = this.grid.GetCell(input_x, input_y);

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

        // Cell is not yet marked -> Mark and automtically unflag
        if(!cell_selected.GetIsMarked()){
            System.out.println("Marking cell.");
            cell_selected.SetIsMarked(true);
            cell_selected.SetIsFlagged(false);
            this.flag_count -= 1;
            return;
        }
    }





    // Validate inputs to be a integer
    public int InputInteger(String input_message){
        int int_input = -1;
        boolean valid_input = false;

        // Keep asking until valid input given
        while(!valid_input){
            System.out.println(input_message);
            String input = this.reader.next();

            // Check input is POSITIVE INTEGER
            try {
                int_input = Integer.parseInt(input);
                //if (int_input <= 0) throw new NegativeException();

                valid_input = true;
            }
            //catch(NegativeException e){
            //    System.out.println("Input must be positive. Please try again.");
            //}
            catch(Exception e) {
                System.out.println("Input must be an integer. Please try again.");
            }

        }

        return int_input;
    }

    // Validate inputs to be within integer range
    public int InputIntegerRange(String input_message, int min, int max){
        int int_input = -1;

        do {
            // Keep asking until input within range given
            int_input = InputInteger(input_message); // Ensure integer is inputted
            if(min <= int_input && int_input <= max) break;

            System.out.println("Input out of range. Please try again.");
        } while(true);

        return int_input;
    }

    public String InputMenu(String input_message, String[] options){
        String options_input = "";

        do {
            System.out.println(input_message);
            options_input = reader.next().toUpperCase();
            if(Arrays.asList(options).contains(options_input)) break;

            System.out.println("Input not recognised. Please try again.");
        } while(true);

        return options_input;
    }

}
