public class Grid {
     private int rows, columns;
     private Cell[][] grid_of_cells;

     public Grid(int rows, int columns){
         this.rows = rows;
         this.columns = columns;

         this.grid_of_cells = CreateGrid();
     }
     public Cell GetCell(int x, int y){
         return this.grid_of_cells[x][y];
     }


     // Create a 2D columns x rows array of Cells
     public Cell[][] CreateGrid(){
         Cell[][] grid = new Cell[this.columns][this.rows];

         for(int x = 0; x < this.columns; x++){
             Cell[] cells_column = new Cell[this.rows]; // Create empty column with height of number of rows
             for(int y = 0; y < this.rows; y++){
                 grid[x][y] = new Cell(x,y); // Add populated column
             }
         }
         return grid;
     }

     // Build the string ASCII art of the grid, including a rows and columns label
     public String GridString(){
         String grid_string = "";


         for(int y = 0; y < this.rows; y++){

             // Row labels
             String y_label_padding = " ";
             for(int i = 0; i < String.valueOf(this.rows).length() - String.valueOf(y+1).length(); i++)  y_label_padding += " "; // Padding to keep label algined
             grid_string += (y+1) + y_label_padding;

             for (int x = 0; x < this.columns; x++) {
                 Cell c = this.grid_of_cells[x][y];

                 grid_string += c.GetIsRevealed() // If cell revealed -> Show number or bomb / Otherwise -> Flagged, marked, empty
                         ? c.GetIsBomb()
                            ? " * "
                            :  c.GetBombsNear() == 0
                                ? " - "
                                : " " + c.GetBombsNear() + " "
                         : c.GetIsFlagged()
                            ? "[F]"
                            : c.GetIsMarked()
                                ? "[?]"
                                : "[ ]";

                 // Column padding to keep aligned based on largest column label
                 String column_padding = "";
                 for(int i = 0; i < String.valueOf(this.columns).length(); i++)  column_padding += " ";
                 grid_string += column_padding;
             }
             grid_string += "\n";
         }

         // Column labels
         String x_label = " " + " ";
         for(int i = 0; i < String.valueOf(this.rows).length(); i++)  x_label += " ";

         // Padding
         for(int x = 0; x < this.columns; x++){
             x_label += (x+1);
             for(int i = 0; i < 3 + String.valueOf(this.columns).length() - String.valueOf(x+1).length(); i++)  x_label += " ";
         }
         grid_string =  x_label + "\n" + grid_string; // Add column x labels to the string

         return grid_string;
     }



     public void RevealAdjacentCells(Cell current_cell){

         // Reveal current cell
         if(current_cell.GetBombsNear() >= 0) current_cell.SetIsRevealed(true);

         int current_x = current_cell.GetX();
         int current_y = current_cell.GetY();

         //  Recursively reveal adjacent cells
         if(current_cell.GetBombsNear() == 0){
             for(int adjacent_x = current_x-1; adjacent_x <= current_x+1; adjacent_x++){
                 if(adjacent_x < 0 || adjacent_x >= this.columns) continue; // Don't check outside-of-grid

                 for(int adjacent_y = current_y-1; adjacent_y <= current_y+1; adjacent_y++){
                     if(adjacent_y < 0 || adjacent_y >= this.rows || (adjacent_x == current_x && adjacent_y == current_y)) continue; // Don't check outside-of-grid OR same cell

                     Cell adjacent_cell = this.grid_of_cells[adjacent_x][adjacent_y];
                     if(adjacent_cell.GetIsRevealed() || adjacent_cell.GetIsFlagged() || adjacent_cell.GetIsMarked()) continue; // Don't check if already revealed, flagged, or marked
                     RevealAdjacentCells(adjacent_cell);
                 }
             }
         }
     }
}
