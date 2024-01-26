package src;

public class WallFeature {

    private double width, height, area;

    public WallFeature(int shape_ind, double width, double height){
        this.width = width;
        this.height = height;
        this.area = CalculateArea(shape_ind);
    }

    public double CalculateArea(int shape_ind){
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


    public double GetArea(){
        return this.area;
    }
}
