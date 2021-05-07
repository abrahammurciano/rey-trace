package unit.rendering;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import org.junit.Assert;
import org.junit.Test;
import primitives.Colour;
import rendering.ImageWriter;
import rendering.Resolution;

public class ImageWriterTest {
	final String fileName = "images/GridImage.png";

	@Test
	public void testWriteToFile() {
		int width = 800;
		int height = 500;
		Colour bg = new Colour(100, 100, 4);
		Colour gridlines = new Colour(10, 200, 150);
		ImageWriter iw = new ImageWriter(fileName, new Resolution(width, height));
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
		assertImageCorrect("Grid image does not look correct.", fileName);
	}

	private void assertImageCorrect(String errorMessage, String fileName) {
		JFrame frame = new JFrame();
		JLabel label = new JLabel(new ImageIcon(fileName));
		JScrollPane scrollPane = new JScrollPane(label);
		frame.add(scrollPane);
		frame.setVisible(true);
		frame.pack();
		int feedback = JOptionPane.showConfirmDialog(frame,"Please confirm that the displayed image is correct.", "", JOptionPane.YES_NO_OPTION);
		Assert.assertEquals(errorMessage, feedback, JOptionPane.YES_OPTION);
	}

}
	
