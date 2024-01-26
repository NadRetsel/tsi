import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Scanner reader = new Scanner(System.in);
    private Grid grid;
    private int number_of_bombs;
    private int rows, columns;

    public Game(){
        this.rows = 8;
        this.columns = 16;
        this.grid = new Grid(this.rows,this.columns);
        System.out.println(this.grid.PrintGrid());

        this.number_of_bombs = (int) (this.rows * this.columns * 0.25);

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
        boolean first_move = true;
        String[] menu_options = {"SELECT", "FLAG/UNLFAG", "MARK/UNMARK"};

        do{
            
            String menu_select = menu_options[InputIntegerRange("Would you like to... +" +
                    "\n1 - SELECT to reveal" +
                    "\n2 - FLAG/UNFLAG to indicate bomb" +
                    "\n3 - MARK/UNMARK to place a question mark"+
                    "Please select [1-3]",
                    1, 3)];
            // TODO Confirm user input

            int input_x = InputIntegerRange("Enter your x-coordinate: ", 1, this.columns) - 1;
            int input_y = InputIntegerRange("Enter your y-coordinate: ", 1, this.rows) - 1;

            switch(menu_select){
                case "SELECT" -> {

                    // TODO Confirm inputs with user

                    // Only populate with bombs after the first move
                    if(first_move){
                        PopulateGrid(input_x,input_y);
                        first_move = false;
                    }

                    UpdateGrid(input_x,input_y);
                    System.out.println(this.grid.PrintGrid());
                }
            }


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
    public void UpdateGrid(int selected_x, int selected_y){
        Cell cell_selected = this.grid.GetCell(selected_x, selected_y);

        // TODO If selected square already revealed -> ask again

        // TODO If selected square = flagged -> confirm with user

        // TODO If selected square = bomb -> end game
        if(cell_selected.GetIsBomb()){
            // TODO END GAME
        }

        // Reveal selected and adjacent cells if not bomb or flagged
        if(cell_selected.GetBombsNear() >= 0) this.grid.RevealAdjacentCells(selected_x, selected_y);

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
                if (int_input <= 0) throw new NegativeException();

                valid_input = true;
            }
            catch(NegativeException e){
                System.out.println("Input must be positive. Please try again.");
            }
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

}
