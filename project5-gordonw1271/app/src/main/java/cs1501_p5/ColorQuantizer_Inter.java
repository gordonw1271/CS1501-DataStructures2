/**
 * Color Quantizer interface for CS1501 Project 5
 *
 * @author Dr. Farnan
 * @author Brian T. Nixon
 * @author Marcelo d'Almeida
 */
package cs1501_p5;

interface ColorQuantizer_Inter {
    /**
     * Performs color quantization. If numColors is less than 1, this method throws an
     * IllegalArgumentException.
     *
     * @param numColors number of colors to use for color quantization
     * @return A two dimensional array where each index represents the pixel from the original bitmap
     *         image and contains Pixel representing its color after quantization
     * @throws java.lang.IllegalArgumentException if numColors is less than 1
     */
    public Pixel[][] quantizeTo2DArray(int numColors) throws IllegalArgumentException;

    /**
     * Performs color quantization (but saves to a file!). Should perform quantization like
     * quantizeToArray, but instead of returning a 2D Pixel array, returns nothing and writes
     * the resulting image to a file. If numColors is less than 1, this method throws an
     * IllegalArgumentException
     *
     * @param numColors number of colors to use for color quantization
     * @param fileName File to write resulting image to
     * @throws java.lang.IllegalArgumentException if numColors is less than 1
     */
    public void quantizeToBMP(String fileName, int numColors) throws IllegalArgumentException;
}
