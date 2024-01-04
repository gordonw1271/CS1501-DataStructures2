package cs1501_p5;

import java.util.Map;
import java.util.HashMap;
import java.lang.IllegalArgumentException;

public class BucketingMapGenerator implements ColorMapGenerator_Inter {
    // ================================================================================================
    
    public Pixel[] generateColorPalette(Pixel[][] pixelArr, int numColors) throws IllegalArgumentException {
        if (numColors < 1){
            throw new IllegalArgumentException();
        }

        int uniqueColors = checkUniqueColors(pixelArr, numColors);
        Pixel[] out;

        float buckets = 16777216 / numColors;
        if (uniqueColors > numColors) {
            out = new Pixel[numColors];
            for (int i = 0; i < numColors; i++) {
                int color = (int)(buckets * i + (buckets / 2.0));
                out[i] = numToPixel(color);
            }
        } else {
            out = new Pixel[uniqueColors];
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
        }

        return out;
    }

    // ================================================================================================
    public Map<Pixel, Pixel> generateColorMap(Pixel[][] pixelArr, Pixel[] initialColorPalette) {
        Map<Pixel, Pixel> out = new HashMap<>();

        int uniqueColors = checkUniqueColors(pixelArr, initialColorPalette.length);

        if (uniqueColors > initialColorPalette.length) {
            int buckets = 16777216 / initialColorPalette.length;

            for (int i = 0; i < pixelArr.length; i++) {
                for (int x = 0; x < pixelArr[i].length; x++) {
                    int ind = pixelToNum(pixelArr[i][x]) / buckets;
                    if(ind == initialColorPalette.length){
                        ind--;
                    }
                    out.put(pixelArr[i][x], initialColorPalette[ind]);
                }
            }
        } else {
            for (int i = 0; i < initialColorPalette.length; i++) {
                out.put(initialColorPalette[i], initialColorPalette[i]);
            }
        }
        return out;
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
        int red = pix.getRed() << 16;
        int green = pix.getGreen() << 8;
        int blue = pix.getBlue();

        return red | green | blue;
    }

    private Pixel numToPixel(int num) {
        int red = num >> 16 & 255;
        int green = (num >> 8) & 255;
        int blue = num & 255;

        return new Pixel(red, green, blue);
    }
}