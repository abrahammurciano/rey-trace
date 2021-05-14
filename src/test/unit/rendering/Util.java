package unit.rendering;

import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import scene.Scene;
import rendering.Renderer;
import rendering.raytracing.BasicRayTracer;

public class Util {
	private Util() {}

	public static boolean renderXml(String inFile, String outFile) {
		Scene scene;
		try {
			scene = new xml.XmlSceneParser().parse(inFile);
		} catch (IOException __) {
			System.out.println(inFile + ": file not found or could not be opened.");
			return true;
		}

		new Renderer(scene.camera(), new BasicRayTracer(scene), outFile).render(3, 3);

		return checkImageCorrect(outFile);
	}

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
