public class WallFeature {

    private double width, height, area;

    public WallFeature(double width, double height){
        this.width = width;
        this.height = height;
        this.area = CalculateArea();
    }

    public double CalculateArea(){
        return this.width * this.height;
    }


    public double GetArea(){
        return this.area;
    }
}
