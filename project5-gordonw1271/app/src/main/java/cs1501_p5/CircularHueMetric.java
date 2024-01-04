package cs1501_p5;

import java.util.Map;

public class CircularHueMetric implements DistanceMetric_Inter
{
    public double colorDistance(Pixel p1, Pixel p2)
    {
        int high,low;

        if(p1.getHue()==p2.getHue()) return 0;
        else if(p1.getHue()>p2.getHue()){
            high = p1.getHue();
            low = p2.getHue();
        }else{
            high = p2.getHue();
            low = p1.getHue();
        }

        int diff = high-low;
        if(diff>180){
            return 360-diff;
        }else{
            return diff;
        }
    }
}