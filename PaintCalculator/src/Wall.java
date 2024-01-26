package src;

import java.util.Vector;

public class Wall {
    private double width, height, area, nonpaint_area;
    private String colour;
    private Vector<WallFeature> nonpaints;
    public Wall(int shape_ind, double width, double height, String colour, Vector<WallFeature> nonpaints){
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.nonpaints = nonpaints;

        this.area = CalculateArea(shape_ind);
        this.nonpaint_area = CalculateNonpaintArea();

    }

    public double CalculateArea(int shape_ind){
        double area = ShapeArea(shape_ind);

        for(WallFeature nonpaint : this.nonpaints){
            area -= nonpaint.GetArea();
        }
        return area;
    }

    public double CalculateNonpaintArea(){
        double area = 0;
        for(WallFeature nonpaint : this.nonpaints) area += nonpaint.GetArea();
        return area;
    }

    public double ShapeArea(int shape_ind){
        /*
        0 - RECTANGLE
        1 - CIRCLE
        2 - TRIANGLE
        3 - TRAPEZIUM
         */
        double area = 0;
        switch(shape_ind){
            case 0,3 -> area = (this.width * this.height);
            case 1 -> area = (Math.PI * this.width * this.height);
            case 2 -> area = (0.5 * this.width * this.height);
            //case 3 -> area = (this.width * this.height);
        }
        return area;
    }


    public String GetProperties(){
        return(
                "Width: " + this.width + " "
                + "Height: " + this.height + " "
                + "Colour: " + this.colour + " "
                + "Non-paintable area: " + this.nonpaint_area + " "
                + "Paintable: " + this.area
        );
    }


    public double GetArea(){
        return this.area;
    }
    public double GetNonpaintArea(){
        return this.nonpaint_area;
    }
    public String GetColour(){
        return this.colour;
    }
}
