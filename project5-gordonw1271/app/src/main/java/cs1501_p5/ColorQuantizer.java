package cs1501_p5;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ColorQuantizer implements ColorQuantizer_Inter
{
    Pixel[][] pixelArray = null;
    ColorMapGenerator_Inter gen = null;

    public ColorQuantizer(Pixel[][] pixelArray, ColorMapGenerator_Inter gen){
        this.pixelArray = pixelArray;
        this.gen = gen;
    }

    public ColorQuantizer(String bmpFilename, ColorMapGenerator_Inter gen){
        this.gen = gen;

        try {
            BufferedImage image = ImageIO.read(new File("build/resources/main/image.bmp"));

            Pixel[][] pixelMatrix = convertBitmapToPixelMatrix(image);
            this.pixelArray = pixelMatrix;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Pixel[][] quantizeTo2DArray(int numColors) throws IllegalArgumentException{
        if(numColors<1){
            throw new IllegalArgumentException();
        }

        Pixel[] pallette = gen.generateColorPalette(this.pixelArray, numColors);
        Map<Pixel, Pixel> map = gen.generateColorMap(pixelArray, pallette);

        Pixel[][] output = new Pixel[this.pixelArray.length][this.pixelArray[0].length];

        for (int i = 0; i < this.pixelArray.length; i++) {
            for (int x = 0; x < this.pixelArray[i].length; x++) {
                output[i][x] = map.get(this.pixelArray[i][x]);
            }
        }

        return output;
    }

    public void quantizeToBMP(String fileName, int numColors){
        Pixel[][] out = quantizeTo2DArray(numColors);

        int width = out[0].length;
        int height = out.length;

        BufferedImage image = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel pixel = out[row][col];
                int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
                image.setRGB(row, col, rgb);
            }
        }

        try {
            File outputFile = new File(fileName);
            ImageIO.write(image, "bmp", outputFile);
        } catch (IOException e) {
            System.err.println("Error writing image to file: " + e.getMessage());
        }
    }

    // ================================================================================================

    private static Pixel[][] convertBitmapToPixelMatrix(BufferedImage image) {
        Pixel[][] pixelMatrix = new Pixel[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                pixelMatrix[x][y] = new Pixel(red, green, blue);
            }
        }
        return pixelMatrix;
    }
}