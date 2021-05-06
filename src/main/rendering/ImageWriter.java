package rendering;

import primitives.Colour;

/**
 * This class is responsible for writing pixels to an image file.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class ImageWriter {
	/**
	 * Construct an image writer which will write to the given file.
	 *
	 * @param filename   The name of the file to write to.
	 * @param resolution The resolution of the image.
	 */
	public ImageWriter(String filename, Resolution resolution) {
		// TODO: implement
	}

	/**
	 * Set the pixel at coordinates (row, col) to the given colour.
	 *
	 * @param row    The row index of the pixel to be written.
	 * @param col    The column index of the pixel to be written.
	 * @param colour The colour to be written to the pixel.
	 */
	public void setPixel(int row, int col, Colour colour) {
		// TODO: implement
	}

	/**
	 * Write the image to disk.
	 */
	public void writeToFile() {
		// TODO: implement
	}
}
