import java.util.Vector;

public class Wall {
    private double width, height, area, nonpaint_area;
    private String colour;
    private Vector<WallFeature> nonpaints;
    public Wall(double width, double height, String colour, Vector<WallFeature> nonpaints){
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.nonpaints = nonpaints;

        this.area = CalculateArea();
        this.nonpaint_area = CalculateNonpaintArea();

    }

    public double CalculateArea(){
        double area = this.width * this.height;
        for(WallFeature nonpaint : this.nonpaints){
            area -= nonpaint.CalculateArea();
        }
        return area;
    }

    public double CalculateNonpaintArea(){
        double area = 0;
        for(WallFeature nonpaint : this.nonpaints) area += nonpaint.GetArea();
        return area;
    }


    public String GetProperties(){
        return(
                "Width: " + this.width + " "
                + "Height: " + this.height + " "
                + "Colour: " + this.colour + " "
                + "Non-paintables: " + this.nonpaints + " "
                + "Area: " + this.area
        );
    }


    public double GetArea(){
        return this.area;
    }
    public double GetNonpaintArea(){
        return this.nonpaint_area;
    }
}
