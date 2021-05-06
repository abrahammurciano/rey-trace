package rendering;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import primitives.Colour;

/**
 * This class is responsible for writing pixels to an image file.
 *
 */
public class ImageWriter {

	private BufferedImage image;
	private WritableRaster raster;
	// maybe use JFileChooser instead of passing a file name
	private String filename;
	private int width;
	private int[] pixels;

	/**
	 * Construct an image writer which will write to the given file.
	 *
	 * @param filename The name of the file to write to.
	 */
	public ImageWriter(String filename, Resolution resolution) {
		image = new BufferedImage(resolution.x, resolution.y, BufferedImage.TYPE_INT_RGB);
		raster = image.getRaster();
		this.width = resolution.x;
		this.filename = filename;
		this.pixels = new int[resolution.x * resolution.y];
	}

	/**
	 * Set the pixel at coordinates (row, col) to the given colour.
	 *
	 * @param row    The row index of the pixel to be written.
	 * @param col    The column index of the pixel to be written.
	 * @param colour The colour to be written to the pixel.
	 */
	public void setPixel(int row, int col, Colour colour) {
		pixels[row * width + col] = colour.rgb();
	}

	/**
	 * Write the image to disk.
	 */
	public void writeToFile() {
		raster.setDataElements(0, 0, this.width, image.getHeight(), pixels);
		try {
			ImageIO.write(image, "JPG", new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
