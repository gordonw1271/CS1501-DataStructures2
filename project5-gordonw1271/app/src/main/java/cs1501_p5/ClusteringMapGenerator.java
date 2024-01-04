package cs1501_p5;

import java.util.Map;
import java.util.HashMap;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;

public class ClusteringMapGenerator implements ColorMapGenerator_Inter
{
    DistanceMetric_Inter metric = null;
    
    public ClusteringMapGenerator(DistanceMetric_Inter metric){
        this.metric = metric;
    }

    public Pixel[] generateColorPalette(Pixel[][] pixelArr, int numColors) throws IllegalArgumentException{
        if (numColors < 1){
            throw new IllegalArgumentException();
        }

        int uniqueColors = checkUniqueColors(pixelArr, numColors);

        if(uniqueColors>numColors){
            Pixel[] out = new Pixel[numColors];
            out[0] = pixelArr[0][0];
            int count = 1;
            
            for(int centroid=1;centroid<numColors;centroid++){
                Pixel pix1 = null;
                Pixel pix2 = null;
                double max = 0;
                

                for (int row = 0; row < pixelArr.length; row++) {
                    for (int col = 0; col < pixelArr[row].length; col++) {

                        double distance = Double.MAX_VALUE;
                        for(int i=0;i<count;i++){

                            double d = metric.colorDistance(pixelArr[row][col], out[i]);
                            if(d<distance){
                                distance = d;
                                pix1 = (pixelArr[row][col]);
                            }
                        }

                        if(distance > max){
                            pix2 = pix1;
                            max = distance;
                        }else if(distance == max){
                            if(pixelToNum(pix1)>pixelToNum(pix2)){
                                pix2=pix1;
                            }
                        }
                    }
                }

                count++;
                out[centroid] = pix2;
            }

            return out;
        }else{
            Pixel [] out = new Pixel[uniqueColors];
            boolean[] pixArr = new boolean[16777216];
            int count = 0;
            for (int i = 0; i < pixelArr.length; i++) {
                for (int x = 0; x < pixelArr[i].length; x++) {
                    int ind = pixelToNum(pixelArr[i][x]);

                    if (pixArr[ind] == false) {
                        pixArr[ind] = true;
                        out[count] = pixelArr[i][x];
                        count++;
                    }
                }
            }
            return out;
        }
    }

    // ================================================================================================

    public Map<Pixel, Pixel> generateColorMap(Pixel[][] pixelArr, Pixel[] initialColorPalette){
        int uniqueColors = checkUniqueColors(pixelArr, initialColorPalette.length);
        
        if(uniqueColors>initialColorPalette.length){
            Pixel[] pal = initialColorPalette;
            Pixel[] cPal;

            while(true){
                ArrayList<Pixel>[] arr = new ArrayList[initialColorPalette.length];

                for(int i=0;i<arr.length;i++){
                    arr[i] = new ArrayList<Pixel>();
                }

                for (int row = 0; row < pixelArr.length; row++) {
                    for (int col = 0; col < pixelArr[row].length; col++) {
                        int ind = -1;
                        double dist = Double.MAX_VALUE;
                        for(int i=0;i<pal.length;i++){
                            double d = metric.colorDistance(pixelArr[row][col], pal[i]);
                            if(d<dist){
                                dist = d;
                                ind =i;
                            }
                        }
                        arr[ind].add(pixelArr[row][col]);
                    }
                }

                cPal = centroidMean(arr);

                if(checkPalette(cPal, pal)){
                    break;
                }
                pal = cPal;
            }
            return arrToMap(pixelArr, cPal);
        }else{
            Map<Pixel, Pixel> out = new HashMap<>();
            for (int i = 0; i < initialColorPalette.length; i++) {
                out.put(initialColorPalette[i], initialColorPalette[i]);
            }
            return out;
        }
    }

    // ================================================================================================
    private Map<Pixel, Pixel> arrToMap(Pixel[][] pixelArr, Pixel[] pal){
        Map<Pixel, Pixel> out = new HashMap<>();
        
        for (int row = 0; row < pixelArr.length; row++) {
            for (int col = 0; col < pixelArr[row].length; col++) {
                int ind = 0;
                double dist = Double.MAX_VALUE;

                for(int i=0;i<pal.length;i++){
                    double d = metric.colorDistance(pixelArr[row][col], pal[i]);
                    if(d<dist){
                        dist = d;
                        ind = i;
                    }
                }

                out.put(pixelArr[row][col], pal[ind]);
            }
        }

        return out;

    }
    // ================================================================================================
    private Pixel[] centroidMean(ArrayList<Pixel>[] arr){
        Pixel[] out = new Pixel[arr.length];

        for(int i=0;i<arr.length;i++){
            int red=0;
            int green =0;
            int blue=0;

            for(int x=0;x<arr[i].size();x++){
                red=red+arr[i].get(x).getRed();
                green=green+arr[i].get(x).getGreen();
                blue=blue +arr[i].get(x).getBlue();
            }

            red = red/arr[i].size();
            green = green/arr[i].size();
            blue = blue/arr[i].size();

            out[i] = new Pixel(red,green,blue);
        }
        return out;
    }

    // ================================================================================================
    private boolean checkPalette(Pixel[] p1, Pixel[] p2){
        for(int i=0;i<p1.length;i++){
            if(p1[i].getRed()!=p2[i].getRed() || p1[i].getGreen()!=p2[i].getGreen() || p2[i].getBlue()!=p2[i].getBlue()){
                return false;
            }
        }
        return true;
    }
    // ================================================================================================
    private int checkUniqueColors(Pixel[][] pixelArr, int numColors){
        int uniqueColors = 0;
        while (uniqueColors <= numColors) {
            boolean[] pixArr = new boolean[16777216];

            for (int i = 0; i < pixelArr.length; i++) {
                for (int x = 0; x < pixelArr[i].length; x++) {
                    int ind = pixelToNum(pixelArr[i][x]);

                    if (pixArr[ind] == false) {
                        pixArr[ind] = true;
                        uniqueColors++;
                    }
                }
            }
            break;
        }
        return uniqueColors;
    }
    // ================================================================================================
    private int pixelToNum(Pixel pix) {
        if(pix == null){
            return 0;
        }
        int red = pix.getRed() << 16;
        int green = pix.getGreen() << 8;
        int blue = pix.getBlue();

        return red | green | blue;
    }
    // ================================================================================================
    private Pixel numToPixel(int num) {
        int red = num >> 16 & 255;
        int green = (num >> 8) & 255;
        int blue = num & 255;

        return new Pixel(red, green, blue);
    }
}