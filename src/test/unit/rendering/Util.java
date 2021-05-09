package unit.rendering;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import org.junit.Assert;

public class Util {
	private Util() {}

	public static void assertImageCorrect(String errorMessage, String fileName) {
		JFrame frame = new JFrame();
		JLabel label = new JLabel(new ImageIcon(fileName));
		JScrollPane scrollPane = new JScrollPane(label);
		frame.add(scrollPane);
		frame.setVisible(true);
		frame.pack();
		int feedback = JOptionPane.showConfirmDialog(frame, "Please confirm that the displayed image is correct.", "",
			JOptionPane.YES_NO_OPTION);
		Assert.assertEquals(errorMessage, feedback, JOptionPane.YES_OPTION);
	}

}
