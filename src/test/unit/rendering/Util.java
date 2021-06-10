package unit.rendering;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Util {
	private Util() {}

	public static boolean checkImageCorrect(String fileName) {
		JFrame frame = new JFrame();
		JLabel label = new JLabel(new ImageIcon(fileName));
		JScrollPane scrollPane = new JScrollPane(label);
		frame.add(scrollPane);
		frame.setVisible(true);
		frame.pack();
		int feedback = JOptionPane.showConfirmDialog(frame, "Please confirm that the displayed image is correct.",
			fileName, JOptionPane.YES_NO_OPTION);
		return JOptionPane.YES_OPTION == feedback;
	}

}
