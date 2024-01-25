import java.util.*;

public class Main {
    private static Scanner reader = new Scanner(System.in);

    private static Vector<String> paint_colours = new Vector<>();

    public static void main(String[] args) {

        // Colour of paint

        // Volume of paint tin
        // 1L ≈ 6-6.5m^2


        // Number of walls to paint
        // TODO Verify with user number of walls input is correct
        int walls_total = IntegerInput("Enter number of walls to paint: ");
        Vector<Wall> walls_all = new Vector<>();


        // Ask user for properties of each wall
        for(int wall_index = 0; wall_index < walls_total; ++wall_index){

            // TODO Shape of wall
            int wall_shape_ind = SelectInput("What shape best describes [Wall " + (wall_index+1) + "]? If unsure or shape not available, select [0] for RECTANGLE." +
                    "\n0 - RECTANGLE" +
                    "\n1- CIRCLE" +
                    "\n2 - TRIANGLE" +
                    "\n3 - TRAPEZIUM" +
                    "\nPlease enter a number [0-3]: ",
                    0,3);

            // Width and height of wall
            System.out.println();
            double wall_width = DoubleInput("Enter width of [Wall " + (wall_index+1) + "] in metres: " );
            double wall_height = DoubleInput("Enter height of [Wall " + (wall_index+1) + "] in metres: " );

            // Colour of wall
            String wall_colour = InputWallColour(wall_index);

            // Non-paintables on wall
            Vector<WallFeature> nonpaints_all = InputNonpaints(wall_index);


            Wall wall_new = new Wall(wall_width, wall_height, wall_colour, nonpaints_all);

            // Reject if non-paintable area larger than wall area
            if(wall_new.GetArea() < wall_new.GetNonpaintArea()){
                System.out.println("Non-paintable area greater than wall area. Please try again.");
                --wall_index;
                continue;
            }

            walls_all.add(wall_new);
        }



        double wall_area = 0;
        System.out.println();
        for(int i = 0; i < walls_all.size(); i++) {
            Wall w = walls_all.get(i);
            System.out.println("Wall " + (i+1) + ": " + w.GetProperties());
            wall_area += w.GetArea();
        }

        // TODO Edit walls after initial inputs


        // TODO Convert surface area to paint tins
        System.out.println();
        System.out.println("Total area to paint: " + wall_area + "m^2");

        System.out.println();
        System.out.println("Assuming 10m^2 per 1L of paint...");
        double paint_needed = wall_area / 10;
        System.out.println("Minimum paint required: " + paint_needed + "L");

        // TODO Individual paint tins


        System.out.println();
        System.out.println("Assuming available sizes of paint tins are 10L, 5L, 2.5L, 1L, 0.5L...");
        double[] paint_tins = {10, 5, 2.5, 1, 0.5};
        double[] paint_tins_required = new double[paint_tins.length];
        double remaining_paint = paint_needed;
        String required_tins = "";
        for(int i = 0; i < paint_tins.length; i++){
            if(remaining_paint > paint_tins[i] || (i == paint_tins.length-1 && remaining_paint > 0)){
                remaining_paint -= paint_tins[i];
                paint_tins_required[i] += 1;
                --i;
            }
            required_tins += "\n" + paint_tins_required[i] + " tins required of " + paint_tins[i] + "L";
        }

        System.out.println("You will require:" + required_tins);
        // TODO Calculate best cost
    }

    public static String InputWallColour(int wall_index){
        String wall_colour = "";
        boolean valid_colour = false;
        do{
            System.out.println();
            System.out.println("Previous colours used: " + paint_colours.toString());

            System.out.print("Enter colour of [Wall " + (wall_index + 1) + "]: ");
            reader = new Scanner(System.in);
            wall_colour = reader.next().toUpperCase();

            if(paint_colours.contains(wall_colour)) break; // Skip if old colour input

            // Confirm new colour
            boolean valid_input = false;
            do{
                System.out.print(wall_colour + " is a new colour. Are you sure this is correct? [Y/N]: ");
                reader = new Scanner(System.in);
                switch(reader.next().toUpperCase()){
                    case "Y" -> {
                        paint_colours.add(wall_colour);
                        valid_input = true;
                        valid_colour = true;
                    }
                    case "N" -> {
                        valid_input = true;
                    }
                    default -> {
                        System.out.println();
                        System.out.println("Unknown input. Please try again.");
                    }
                }
            } while(!valid_input);


        } while(!valid_colour);
        return wall_colour;
    }

    public static Vector<WallFeature> InputNonpaints(int wall_index){
        Vector<WallFeature> nonpaints_all = new Vector<>();

        // Number of non-paintable features
        // TODO Verify with user number of non-paintables input is correct
        System.out.println();
        int nonpaints_total = IntegerInput("Enter number windows, doors, and other features you do not want to paint over that are on [Wall " + (wall_index+1) + "]: ");

        // Properties of each non-paintable
        for(int nonpaint_index = 0; nonpaint_index < nonpaints_total; ++nonpaint_index){

            // TODO Shape of each non-paintable
            int nonpaint_shape_ind = SelectInput("What shape best describes [Item " + (nonpaint_index+1) + "]? If unsure or shape not available, select [0] for RECTANGE." +
                            "\n0 - RECTANGLE" +
                            "\n1- CIRCLE" +
                            "\n2 - TRIANGLE" +
                            "\n3 - TRAPEZIUM",
                    0,3);

            // Width and height
            System.out.println();
            double nonpaint_width = DoubleInput("Enter width of [Item " + (nonpaint_index+1) + "] in metres: " );
            double nonpaint_height = DoubleInput("Enter height of [Item " + (nonpaint_index+1) + "] in metres: " );

            WallFeature nonpaint_new = new WallFeature(nonpaint_width, nonpaint_height);
            nonpaints_all.add(nonpaint_new);
        }

        return nonpaints_all;
    }


    // Validate inputs to be positive integer
    public static int IntegerInput(String input_message){
        int int_input = -1;
        boolean valid_input = false;

        // Keep asking until valid input given
        while(!valid_input){
            System.out.println(input_message);
            String input = reader.next();

            // Check input is POSITIVE INTEGER
            try {
                int_input = Integer.parseInt(input);
                if (int_input < 0) throw new NegativeException();

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


    // Validate inputs to be positive double
    public static double DoubleInput(String input_message){
        double int_input = -1;
        boolean valid_input = false;

        // Keep asking until valid input given
        while(!valid_input){
            System.out.println(input_message);
            String input = reader.next();

            // Check input is POSITIVE INTEGER
            try {
                int_input = Double.parseDouble(input);
                if (int_input <= 0) throw new NegativeException();

                valid_input = true;
            }
            catch(NegativeException e){
                System.out.println("Input must be positive. Please try again.");
            }
            catch(Exception e) {
                System.out.println("Input must be a decimal number. Please try again.");
            }

        }

        return int_input;
    }

    // Validate inputs to be within integer selection options
    public static int SelectInput(String input_message, int min, int max){
        int int_input = -1;

        do {
            int_input = IntegerInput(input_message);
            if(min <= int_input && int_input <= max) break;


            System.out.println("Unknown option selected. Please select from the menu.");
        } while(true);

        return int_input;
    }


    public static String NewReaderInput(){
        reader = new Scanner(System.in);
        return reader.nextLine();
    }

}