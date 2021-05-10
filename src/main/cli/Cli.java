package cli;

import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;
import primitives.Point;
import rendering.Renderer;
import rendering.Resolution;
import rendering.camera.Camera;
import rendering.camera.CameraSettings;
import rendering.raytracing.BasicRayTracer;
import rendering.raytracing.RayTracer;
import scene.Scene;
import xml.XmlParserException;
import xml.XmlSceneParser;
import xml.factories.attribute.XmlTripleFactory;

public class Cli {
	public static void main(String[] args) {
		Options options = new Options();

		options.addOption("r", "resolution", true, "Resolution of the output image. Default is 1920x1080.");

		options.addOption("a", "anti-aliasing", true,
			"The level of anti-aliasing to use. 1 means no anti-aliasing. 2 means moderate, 3 means extreme, and anything higher is simply overkill. Default is 2.");

		options.addOption("l", "location", true, "Location of the camera. Default is (0,0,0).");

		options.addOption("d", "distance", true, "Distance of the view plane from the camera. Default is 10.0 units.");

		options.addOption("w", "width", true,
			"Width of the view plane. Default is 19.2. It is recommended that the width be proportional to the height in the same ratio as in the resolution.");

		options.addOption("h", "height", true,
			"Height of the view plane. Default is 10.8. It is recommended that the width be proportional to the height in the same ratio as in the resolution.");

		options.addOption("t", "threads", true, "Number of threads to use. Default is 8.");

		options.addOption("P", "pitch", true,
			"The pitch angle of the camera in degrees. Default is 0.0 (i.e. on the XY plane).");

		options.addOption("Y", "yaw", true,
			"The yaw angle of the camera in degrees. Default is 0.0 (i.e. on the XZ plane).");

		options.addOption("R", "roll", true, "The roll angle of the camera in degrees. Default is 0.0.");

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		Resolution resolution;
		int antiAliasing;
		Point location;
		double distance;
		double width;
		double height;
		int threads;
		double pitch;
		double yaw;
		double roll;
		String fileIn;
		String fileOut;

		try {
			cmd = parser.parse(options, args);

			String resolutionIn = cmd.getOptionValue("resolution");
			resolution = resolutionIn == null ? new Resolution(1920, 1080) : new Resolution(resolutionIn);

			String antiAliasingIn = cmd.getOptionValue("anti-aliasing");
			antiAliasing = antiAliasingIn == null ? 2 : Integer.parseInt(antiAliasingIn);

			String locationIn = cmd.getOptionValue("location");
			location = locationIn == null ? Point.ORIGIN : new XmlTripleFactory<Point>(Point::new).create(locationIn);

			String distanceIn = cmd.getOptionValue("distance");
			distance = distanceIn == null ? 10 : Double.parseDouble(distanceIn);

			String widthIn = cmd.getOptionValue("width");
			width = widthIn == null ? 19.2 : Double.parseDouble(widthIn);

			String heightIn = cmd.getOptionValue("height");
			height = heightIn == null ? 10.8 : Double.parseDouble(heightIn);

			String threadsIn = cmd.getOptionValue("threads");
			threads = threadsIn == null ? 8 : Integer.parseInt(threadsIn);

			String pitchIn = cmd.getOptionValue("pitch");
			pitch = pitchIn == null ? 0 : Double.parseDouble(pitchIn);

			String yawIn = cmd.getOptionValue("yaw");
			yaw = yawIn == null ? 0 : Double.parseDouble(yawIn);

			String rollIn = cmd.getOptionValue("roll");
			roll = rollIn == null ? 0 : Double.parseDouble(rollIn);

			String[] remaining = cmd.getArgs();

			fileIn = remaining[0];
			fileOut = remaining.length > 1 ? remaining[1] : FilenameUtils.removeExtension(fileIn) + ".png";

		} catch (ParseException | ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("reytrace [OPTIONS] <INFILE> [OUTFILE]", options);
			System.exit(1);
			return;
		} catch (NumberFormatException e) {
			System.out.println("Error parsing a number: " + e.getLocalizedMessage());
			System.exit(2);
			return;
		} catch (XmlParserException e) {
			System.out.println("Error parsing XML or location parameter: " + e.getLocalizedMessage());
			System.exit(4);
			return;
		}

		CameraSettings settings =
			new CameraSettings().resolution(resolution).distance(distance).dimensions(width, height).location(location);
		Camera camera = new Camera(settings).rotate(toRadians(pitch), toRadians(yaw), toRadians(roll));
		Scene scene;
		try {
			scene = new XmlSceneParser().parse(fileIn);
		} catch (IOException e) {
			System.out.println("Error: Coutld not open file \"" + fileIn + "\".");
			System.exit(3);
			return;
		}
		RayTracer rayTracer = new BasicRayTracer(scene);
		new Renderer(camera, rayTracer, fileOut).render(threads, antiAliasing);
	}

	private static double toRadians(double degrees) {
		return degrees * Math.PI / 180;
	}
}
