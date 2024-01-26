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
             for(int i = 0; i < cells_column.length; i++) cells_column[i] = new Cell(); // Populate each column with empty Cells
             grid[x] = cells_column; // Add populated column
         }
         return grid;
     }

     // Build the string ASCII art of the grid, including a rows and columns label
    // TODO Reformat so the columns are always aligned with the labels
     public String PrintGrid(){
         String grid_string = "";
         for(int y = 0; y < this.rows; y++){
             grid_string += (y+1); // Row labels

             for (Cell[] cells_column : this.grid_of_cells) {
                 Cell c = cells_column[y];
                 // TODO Flagging character
                 // todo Reformat this statement to be more legible
                 grid_string += c.GetIsRevealed() // If cell revealed -> Show. Otherwise, default character
                         ? c.GetIsBomb() // If bomb -> bomb. Otherwise, safe
                            ? " B "
                            : c.GetBombsNear() == 0 // If 0 -> empty. Otherwise, number
                                ? "- -"
                                : " " + c.GetBombsNear() + " "
                         : "[ ]";
                 grid_string += " ";
             }
             grid_string += "\n";
         }

         // Column labels
         String x_label = " ";
         for(int x = 0; x < this.columns; x++) x_label += " " + (x+1) + " " + "\t";
         grid_string =  x_label + "\n" + grid_string;

         return grid_string;
     }



     public void RevealAdjacentCells(int x, int y){

        // Reveal current cell
         if(this.grid_of_cells[x][y].GetBombsNear() >= 0) this.grid_of_cells[x][y].SetIsRevealed(true);

         //  Recursively reveal adjacent cells
         if(this.grid_of_cells[x][y].GetBombsNear() == 0){
             for(int adjacent_x = x-1; adjacent_x <= x+1; adjacent_x++){
                 if(adjacent_x < 0 || adjacent_x >= this.columns) continue; // Don't check outside-of-grid

                 for(int adjacent_y = y-1; adjacent_y <= y+1; adjacent_y++){
                     if(adjacent_y < 0 || adjacent_y >= this.rows || (adjacent_y == x && adjacent_y == y)) continue; // Don't check outside-of-grid OR same cell

                     if(this.grid_of_cells[adjacent_x][adjacent_y].GetIsRevealed()) continue; // Don't check if already revealed
                     RevealAdjacentCells(adjacent_x, adjacent_y);
                 }
             }
         }
     }
}
