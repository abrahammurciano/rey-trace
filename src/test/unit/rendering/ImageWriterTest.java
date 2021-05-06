package unit.rendering;

import org.junit.Test;
import primitives.Colour;
import rendering.ImageWriter;
import rendering.Resolution;

public class ImageWriterTest {

	@Test
	public void testWriteToFile() {
		int width = 3;
		int height = 3;
		Colour orange = new Colour(252, 136, 3);
		ImageWriter iw = new ImageWriter("TestImage", new Resolution(width, height));
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				iw.setPixel(i, j, orange);
			}
		}
		iw.writeToFile();
	}

}
	
