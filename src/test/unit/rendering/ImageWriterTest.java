package unit.rendering;

import org.junit.Assert;
import org.junit.Test;
import primitives.Colour;
import rendering.ImageWriter;
import rendering.Resolution;

public class ImageWriterTest {
	static final String FILENAME = "images/GridImage.png";

	@Test
	public void testWriteToFile() {
		int width = 800;
		int height = 500;
		Colour bg = new Colour(100, 100, 4);
		Colour gridlines = new Colour(10, 200, 150);
		ImageWriter iw = new ImageWriter(FILENAME, new Resolution(width, height));
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				if ((j % 50 == 0) || (i % 50 == 0)) {
					iw.setPixel(i, j, gridlines);
				} else {
					iw.setPixel(i, j, bg);
				}
			}
		}
		iw.writeToFile();
		Assert.assertTrue("Grid image does not look correct.", Util.checkImageCorrect(FILENAME));
	}
}
