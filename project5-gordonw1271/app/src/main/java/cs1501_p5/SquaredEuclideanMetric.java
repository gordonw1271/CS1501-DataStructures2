package cs1501_p5;

import java.util.Map;

public class SquaredEuclideanMetric implements DistanceMetric_Inter
{
    public double colorDistance(Pixel p1, Pixel p2){
        int red = p1.getRed() - p2.getRed();
        int green = p1.getGreen() - p2.getGreen();
        int blue = p1.getBlue() - p2.getBlue();

        return (red*red)+(green*green)+(blue*blue);
    }
}